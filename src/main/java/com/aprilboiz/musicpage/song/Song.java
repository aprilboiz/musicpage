package com.aprilboiz.musicpage.song;

import com.aprilboiz.musicpage.emotion.Emotion;
import com.aprilboiz.musicpage.song.dto.SongResponse;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Entity
@Table(name = "songs")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;
    private String author;

    @Column(name = "youtube_id", nullable = false)
    private String youtubeID;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "song_emotion",
            joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "emotion_id")
    )
    Set<Emotion> emotions = new HashSet<>();

    public Song(String title, String author, String youtubeID, Set<Emotion> emotions) {
        this.title = title;
        this.author = author;
        this.youtubeID = youtubeID;
        this.emotions = emotions;
    }

    public SongResponse toDTO(){
        return new SongResponse(
                id,
                title,
                author,
                youtubeID,
                emotions.stream().map(Emotion::getName).collect(Collectors.toSet()));
    }
}
