package com.aprilboiz.musicpage.song.dto;

public record SongMetadata(
        String title,
        String artist,
        String playback_url,
        Long duration
) {
}
