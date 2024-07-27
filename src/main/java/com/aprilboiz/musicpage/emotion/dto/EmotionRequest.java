package com.aprilboiz.musicpage.emotion.dto;

import jakarta.validation.constraints.NotBlank;

public record EmotionRequest(
        @NotBlank(message = "Emotion name is required.")
        String name
) {
}
