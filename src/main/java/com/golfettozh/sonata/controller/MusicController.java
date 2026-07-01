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

@RestController
@RequestMapping("/musics")
@AllArgsConstructor
public class MusicController {

    private final MusicService musicService;

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

    @GetMapping("/{id}")
    public ResponseEntity<MusicResponseDTO> getUser(@PathVariable Long id) {
        Music music = musicService.findById(id);

        if (music == null) {
            return ResponseEntity.notFound().build();
        }

        MusicResponseDTO response = new MusicResponseDTO(music.getId(), music.getTitle(), music.getArtist(), music.getDurationInMinutes());
        return ResponseEntity.ok(response);
    }


}