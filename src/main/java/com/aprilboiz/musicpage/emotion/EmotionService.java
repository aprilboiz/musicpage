package com.aprilboiz.musicpage.emotion;

import com.aprilboiz.musicpage.emotion.dto.EmotionRequest;
import com.aprilboiz.musicpage.emotion.dto.EmotionResponse;

import java.util.List;

public interface EmotionService {
    Emotion save(EmotionRequest emotion);
    List<EmotionResponse> findAll();
    Emotion findById(Long id);
    Emotion findByName(String name);
}
