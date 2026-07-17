package com.golfettozh.sonata.service;

import com.golfettozh.sonata.dto.request.MusicRequestDTO;
import com.golfettozh.sonata.model.music.Music;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class MusicServiceTest {

    private final MusicService musicService;
    private final EntityManager entityManager;

    @Autowired
    public MusicServiceTest(MusicService musicService, EntityManager entityManager) {
        this.musicService = musicService;
        this.entityManager = entityManager;
    }

    @Test
    @DisplayName("Should delete the song from the database if it exists.")
    void shouldDeleteMusicWhenIdExists() {
        MusicRequestDTO musicRequestDTO = new MusicRequestDTO("Song Title", "Artist Name", 3.5);

        Music musicSave = createMusic(musicRequestDTO);
        UUID idExisting = musicSave.getId();

        musicService.deleteById(idExisting);
        entityManager.flush();
        entityManager.clear();

        Music musicDeletada = entityManager.find(Music.class, idExisting);
        assertNull(musicDeletada, "The song should have been deleted from the database");

    }

    @Test
    @DisplayName("They should return all songs present in the database")
    void shouldReturnAllSongsWhenExists() {
        MusicRequestDTO musicRequestDTO = new MusicRequestDTO("Song Title", "Artist Name", 3.5);
        createMusic(musicRequestDTO);

        List<Music> musics = musicService.findAll();

        assertFalse(musics.isEmpty(), "The list of songs should not be empty");
        assertEquals(1, musics.size(), "There should be exactly 1 song in the list");
    }

    private Music createMusic(MusicRequestDTO musicRequestDTO) {
        Music newMusic = new Music();
        newMusic.setTitle(musicRequestDTO.title());
        newMusic.setArtist(musicRequestDTO.artist());
        newMusic.setDurationInMinutes(musicRequestDTO.durationInMinutes());

        this.entityManager.persist(newMusic);
        this.entityManager.flush();
        return newMusic;
    }
}