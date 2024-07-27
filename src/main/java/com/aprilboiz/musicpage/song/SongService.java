package com.aprilboiz.musicpage.song;

import com.aprilboiz.musicpage.song.dto.SongMetadata;
import com.aprilboiz.musicpage.song.dto.SongUpload;

import java.util.List;

public interface SongService {
    Song save(SongUpload song);
    List<Song> findByEmotions(String emotion);
    SongMetadata extractSongData(String url);
}
