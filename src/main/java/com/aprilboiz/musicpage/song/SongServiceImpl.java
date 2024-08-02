package com.aprilboiz.musicpage.song;

import com.aprilboiz.musicpage.emotion.Emotion;
import com.aprilboiz.musicpage.emotion.EmotionService;
import com.aprilboiz.musicpage.emotion.dto.EmotionResponse;
import com.aprilboiz.musicpage.exception.DuplicateException;
import com.aprilboiz.musicpage.exception.NotFoundException;
import com.aprilboiz.musicpage.song.dto.SongMetadata;
import com.aprilboiz.musicpage.song.dto.SongUpload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:extractService.properties")
public class SongServiceImpl implements SongService{
    private final SongRepository songRepository;
    private final EmotionService emotionService;
    private final RestTemplate restTemplate;
    @Value("${extractService.url}")
    private String extractServiceUrl;

    public SongServiceImpl(SongRepository songRepository, EmotionService emotionService, RestTemplate restTemplate) {
        this.songRepository = songRepository;
        this.emotionService = emotionService;
        this.restTemplate = restTemplate;
    }

    @Transactional
    @Override
    public Song save(SongUpload uploadSong) {
        SongMetadata metadata = this.extractSongData(uploadSong.yt_id());
        if (metadata == null){
            throw new NotFoundException("Your song is not found.");
        }
        Optional<Song> existingSong = songRepository.findSongByYoutubeID(metadata.id());
        if (existingSong.isPresent()){
            throw new DuplicateException("Song already exists.");
        }

//        check for emotions are already exists in the database or not
        List<String> emotionNames = emotionService.findAll()
                .stream()
                .map(EmotionResponse::name)
                .map(String::toLowerCase)
                .toList();
        if (uploadSong.emotions()
                .stream()
                .map(String::toLowerCase)
                .anyMatch(emo -> !emotionNames.contains(emo))
        ){
            throw new NotFoundException("Invalid emotion.");
        }

//       check for title and artist
        if (uploadSong.title() == null || uploadSong.author() == null){
            SongMetadata songMetadata = extractSongData(uploadSong.yt_id());
            if (songMetadata == null){
                throw new NotFoundException("Failed to extract song data.");
            }
            uploadSong = new SongUpload(
                    songMetadata.title(),
                    songMetadata.author(),
                    songMetadata.id(),
                    uploadSong.emotions()
            );
        }

        try {
            Song newSong = new Song(
                    uploadSong.title(),
                    uploadSong.author(),
                    uploadSong.yt_id(),
                    uploadSong.emotions()
                            .stream()
                            .map(emotionService::findByName)
                            .collect(Collectors.toSet())
            );
            return songRepository.save(newSong);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save song. Reason: " + e.getMessage());
        }
    }

    @Override
    public List<Song> findByEmotions(String emotions) {
        Set<String> existingEmotions = emotionService.findAll()
                .stream()
                .map(EmotionResponse::name)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        Set<String> filteredEmotions = Arrays.stream(emotions.split(","))
                .map(String::toLowerCase)
                .filter(existingEmotions::contains)
                .collect(Collectors.toSet());

        if (filteredEmotions.isEmpty()){
            throw new NotFoundException(String.format("No songs found for emotion '%s'.", emotions));
        }

        List<Song> response = songRepository.findAllSongsContainsEmotions(
                filteredEmotions
                        .stream()
                        .map(emotionService::findByName)
                        .toList()
                        .stream()
                        .map(Emotion::getId)
                        .toList()
        );
        if (!response.isEmpty()){
            return response;
        } else {
            throw new NotFoundException(String.format("No songs found for emotion '%s'.", emotions));
        }
    }

    @Override
    public SongMetadata extractSongData(String songUrl) {
        try {
            return restTemplate.getForObject(extractServiceUrl + songUrl, SongMetadata.class);
        } catch (Exception e) {
            return null;
//            throw new NotFoundException("Failed to extract song data. Reason: " + e.getMessage());
        }
    }
}
