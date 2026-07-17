package com.golfettozh.sonata.repository;

import com.golfettozh.sonata.model.music.Music;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MusicRepository extends JpaRepository<Music, UUID> {}
