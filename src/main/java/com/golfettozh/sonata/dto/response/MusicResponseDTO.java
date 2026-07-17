package com.golfettozh.sonata.dto.response;

import com.golfettozh.sonata.model.music.Music;
import java.util.UUID;

public record MusicResponseDTO (
        UUID id,
        String title,
        String artist,
        Double durationInMinutes
) {
    public MusicResponseDTO(Music music) {
        this(
                music.getId(),
                music.getTitle(),
                music.getArtist(),
                music.getDurationInMinutes()
        );
    }
}
