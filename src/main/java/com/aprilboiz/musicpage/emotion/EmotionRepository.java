package com.aprilboiz.musicpage.emotion;

import com.aprilboiz.musicpage.emotion.dto.EmotionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmotionRepository extends JpaRepository<Emotion, Long> {
    Optional<Emotion> findByNameIgnoreCase(String name);

    @Query("SELECT new com.aprilboiz.musicpage.emotion.dto.EmotionResponse(e.id, e.name) FROM Emotion e")
    List<EmotionResponse> findAllEmotions();
}
