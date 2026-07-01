package com.golfettozh.sonata.service;

import com.golfettozh.sonata.dto.request.MusicRequestDTO;
import com.golfettozh.sonata.model.Music;
import com.golfettozh.sonata.repository.MusicRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MusicService {
    private final MusicRepository musicRepository;

    public Music save(MusicRequestDTO dto) {
        Music music = new Music();
        music.setArtist(dto.getArtist());
        music.setTitle(dto.getTitle());
        music.setDurationInMinutes(dto.getDurationInMinutes());

        return musicRepository.save(music);
    }

    public Music findById(Long id) {
        return musicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Música com o ID: " + id + ", não foi encontrada."));
    }
}


