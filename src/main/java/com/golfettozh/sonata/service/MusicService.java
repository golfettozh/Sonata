package com.golfettozh.sonata.service;

import com.golfettozh.sonata.dto.request.MusicRequestDTO;
import com.golfettozh.sonata.model.Music;
import com.golfettozh.sonata.repository.MusicRepository;
import com.golfettozh.sonata.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class MusicService {
    private final MusicRepository musicRepository;

    public Music save(MusicRequestDTO dto) {
        Objects.requireNonNull(dto, "Dados da música são obrigatórios");

        Music music = new Music();
        music.setArtist(dto.getArtist());
        music.setTitle(dto.getTitle());
        music.setDurationInMinutes(dto.getDurationInMinutes());

        return musicRepository.save(music);
    }

    public Music findById(Long id) {
        return musicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Música não cadastrada ou não encontrada"));
    }

    public List<Music> findAll() {
        return musicRepository.findAll();
    }

    public Music deleteById(Long id) {
        Music music = findById(id);

        musicRepository.delete(music);

        return music;
    }
}


