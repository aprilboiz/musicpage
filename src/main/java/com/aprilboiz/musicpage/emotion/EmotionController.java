package com.aprilboiz.musicpage.emotion;

import com.aprilboiz.musicpage.emotion.dto.EmotionRequest;
import com.aprilboiz.musicpage.emotion.dto.EmotionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emotion")
public class EmotionController {
    private final EmotionServiceImpl emotionService;

    public EmotionController(EmotionServiceImpl emotionService) {
        this.emotionService = emotionService;
    }

    @Operation(summary = "Add a new emotion", tags = {"Emotion"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Emotion added successfully", content = @Content(schema = @Schema(implementation = EmotionResponse.class)))
    })
    @PostMapping("/add")
    public ResponseEntity<EmotionResponse> addEmotion(@Valid @RequestBody EmotionRequest request){
        Emotion newEmotion = emotionService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEmotion.toDTO());
    }

    @Operation(summary = "Get all emotions", tags = {"Emotion"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Emotions found", content = @Content(schema = @Schema(implementation = EmotionResponse.class)))
    })
    @GetMapping("/all")
    public ResponseEntity<List<EmotionResponse>> getAllEmotion() {
        return ResponseEntity.ok(emotionService.findAll());
    }
}
