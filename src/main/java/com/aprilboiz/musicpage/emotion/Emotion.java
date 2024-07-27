package com.aprilboiz.musicpage.emotion;

import com.aprilboiz.musicpage.emotion.dto.EmotionResponse;
import com.aprilboiz.musicpage.song.Song;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "emotion")
public class Emotion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "emotion_id")
    private Long id;

    @Column(name = "emotion_name", nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "emotions", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Song> songs;

    public Emotion(String name) {
        this.name = name;
    }

    public EmotionResponse toDTO(){
        return new EmotionResponse(id, name);
    }
}
