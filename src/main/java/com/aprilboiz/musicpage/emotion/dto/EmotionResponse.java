package com.aprilboiz.musicpage.emotion.dto;

import com.aprilboiz.musicpage.emotion.Emotion;

public record EmotionResponse(
        Long id,
        String name
) {
    public static EmotionResponse fromEmotion(Emotion e) {
        return new EmotionResponse(e.getId(), e.getName());
    }
}
