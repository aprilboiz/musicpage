package com.aprilboiz.musicpage.song.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record SongUpload(
        String title,
        String author,

        @NotNull(message = "Youtube ID is required")
        String yt_id,

        @NotEmpty(message = "Emotions are required")
        @Size(min = 1, message = "At least one emotion is required")
        Set<String> emotions
) {
}
