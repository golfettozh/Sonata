package com.golfettozh.sonata.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class MusicResponseDTO {
    private Long id;
    private String title;
    private String artist;
    private Double durationInMinutes;
}
