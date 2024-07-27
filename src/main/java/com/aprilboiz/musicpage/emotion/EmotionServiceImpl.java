package com.aprilboiz.musicpage.emotion;

import com.aprilboiz.musicpage.emotion.dto.EmotionRequest;
import com.aprilboiz.musicpage.emotion.dto.EmotionResponse;
import com.aprilboiz.musicpage.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmotionServiceImpl implements EmotionService{
    private final EmotionRepository emotionRepository;

    public EmotionServiceImpl(EmotionRepository emotionRepository) {
        this.emotionRepository = emotionRepository;
    }

    @Transactional
    @Override
    public Emotion save(EmotionRequest emotion) {
        Emotion newEmotion = new Emotion(emotion.name());
        return emotionRepository.save(newEmotion);
    }

    @Transactional(readOnly = true)
    @Override
    public List<EmotionResponse> findAll() {
        return emotionRepository.findAllEmotions();
    }

    @Override
    public Emotion findById(Long id) {
        return emotionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Emotion with id '%s' is not found.", id)));
    }

    @Override
    public Emotion findByName(String name) {
         return emotionRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new NotFoundException(String.format("Emotion '%s' is not found.", name)));
    }
}
