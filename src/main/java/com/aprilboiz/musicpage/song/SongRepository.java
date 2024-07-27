package com.aprilboiz.musicpage.song;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, Long> {
    Optional<List<Song>> findSongsByTitle(String title);
    Optional<List<Song>> findSongsByArtist(String artist);
    Optional<Song> findSongByYoutubeID(String youtubeID);
    @Query(
            value = "SELECT DISTINCT s.id, s.title, s.artist, s.youtube_id FROM songs s JOIN song_emotion e ON s.id = e.song_id WHERE e.emotion_id IN :emotionIDs",
            nativeQuery = true
    )
    List<Song> findAllSongsContainsEmotions(List<Long> emotionIDs);
}
