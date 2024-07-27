package com.aprilboiz.musicpage.song.dto;

import java.util.Set;


public record SongResponse(
        Long id,
        String title,
        String artist,
        String yt_id,
        Set<String> emotions
) {
}
