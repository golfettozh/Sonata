package com.golfettozh.sonata.controller;

import com.golfettozh.sonata.dto.request.MusicRequestDTO;
import com.golfettozh.sonata.dto.response.MusicResponseDTO;
import com.golfettozh.sonata.model.Music;
import com.golfettozh.sonata.service.MusicService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/musics")
@AllArgsConstructor
public class MusicController {

    private final MusicService musicService;

    @GetMapping("/{id}")
    public ResponseEntity<MusicResponseDTO> getMusic(@PathVariable Long id) {
        Music music = musicService.findById(id);

        MusicResponseDTO response = new MusicResponseDTO(
                music.getId(),
                music.getTitle(),
                music.getArtist(),
                music.getDurationInMinutes()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<MusicResponseDTO>> getAllMusics() {
        List<Music> musics = musicService.findAll();
        List<MusicResponseDTO> responses = musics.stream().map(music -> new MusicResponseDTO(
                music.getId(),
                music.getTitle(),
                music.getArtist(),
                music.getDurationInMinutes()
        )).toList();

        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<MusicResponseDTO> createMusic(@Valid @RequestBody MusicRequestDTO musicDto) {

        Music savedMusic = musicService.save(musicDto);

        MusicResponseDTO response = new MusicResponseDTO(
                savedMusic.getId(),
                savedMusic.getTitle(),
                savedMusic.getArtist(),
                savedMusic.getDurationInMinutes()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MusicResponseDTO> deleteMusic(@Valid @PathVariable Long id) {
        Music deletedMusic = musicService.deleteById(id);

        MusicResponseDTO response = new MusicResponseDTO(
                deletedMusic.getId(),
                deletedMusic.getTitle(),
                deletedMusic.getArtist(),
                deletedMusic.getDurationInMinutes()
        );

        return ResponseEntity.ok(response);
    }
}
