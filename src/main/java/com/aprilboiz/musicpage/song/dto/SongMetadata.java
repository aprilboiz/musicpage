package com.aprilboiz.musicpage.song.dto;

public record SongMetadata(
        String id,
        String title,
        String author,
        String playback_url,
        Long duration
) {
}
