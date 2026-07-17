package com.golfettozh.sonata.controller;

import com.golfettozh.sonata.dto.request.MusicRequestDTO;
import com.golfettozh.sonata.dto.response.MusicResponseDTO;
import com.golfettozh.sonata.model.music.Music;
import com.golfettozh.sonata.service.MusicService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/musics")
@AllArgsConstructor
public class MusicController {

    private final MusicService musicService;

    @GetMapping("/{id}")
    public ResponseEntity<MusicResponseDTO> getMusic(@PathVariable UUID id) {
        Music music = musicService.findById(id);
        return ResponseEntity.ok(new MusicResponseDTO(music));
    }

    @GetMapping
    public ResponseEntity<List<MusicResponseDTO>> getAllMusics() {
        List<Music> musics = musicService.findAll();
        List<MusicResponseDTO> responses = musics.stream()
                .map(MusicResponseDTO::new)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<MusicResponseDTO> createMusic(@Valid @RequestBody MusicRequestDTO musicDto) {
        Music savedMusic = musicService.save(musicDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MusicResponseDTO(savedMusic));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMusic(@PathVariable UUID id) {
        musicService.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
