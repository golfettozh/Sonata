package com.golfettozh.sonata.model.music;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "musics")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;
    private String artist;
    private Double durationInMinutes;
}
