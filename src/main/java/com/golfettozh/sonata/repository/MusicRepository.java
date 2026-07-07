package com.golfettozh.sonata.repository;

import com.golfettozh.sonata.model.Music;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, Long> {}
