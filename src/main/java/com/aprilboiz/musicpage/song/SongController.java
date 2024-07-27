package com.aprilboiz.musicpage.song;

import com.aprilboiz.musicpage.song.dto.SongMetadata;
import com.aprilboiz.musicpage.song.dto.SongResponse;
import com.aprilboiz.musicpage.song.dto.SongUpload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/song")
public class SongController {
    private final SongService songService;

    public SongController(RestTemplate restTemplate, SongService songService) {
        this.songService = songService;
    }

    @Operation(summary = "Upload a new song", tags = {"Song"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Song uploaded successfully", content = @Content(schema = @Schema(implementation = SongResponse.class))),
            @ApiResponse(responseCode = "404", description = "Emotion is not found/Invalid emotion", content = @Content),
            @ApiResponse(responseCode = "409", description = "Song already exists", content = @Content)
    })
    @PostMapping("/upload")
    public ResponseEntity<SongResponse> uploadSong(@Valid @RequestBody SongUpload upload){
        Song savedSong = songService.save(upload);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSong.toDTO());
    }

    /**
     * Emotions are separated by comma.
     * <p>
     * For example, if the emotions are happy, sad, and angry, the path would be /api/song?emo=happy,sad,angry
     */
    @Operation(summary = "Get music by emotions", tags = {"Song"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Music found", content = @Content(schema = @Schema(implementation = SongResponse.class))),
            @ApiResponse(responseCode = "404", description = "Music not found", content = @Content)
    })
    @GetMapping()
    public ResponseEntity<List<SongResponse>> getMusicByEmotion(@RequestParam String emo) {
        return ResponseEntity.ok(
                songService.findByEmotions(emo)
                        .stream()
                        .map(Song::toDTO)
                        .toList()
        );
    }

    @Operation(summary = "Extract song data", tags = {"Song"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Song data extracted successfully", content = @Content(schema = @Schema(implementation = SongMetadata.class))),
            @ApiResponse(responseCode = "404", description = "Failed to extract song data", content = @Content)
    })
    @GetMapping("/extract")
    public ResponseEntity<SongMetadata> extractSongData(@RequestParam String url){
        SongMetadata metadata = songService.extractSongData(url);
        return ResponseEntity.ok(metadata);
    }
}
