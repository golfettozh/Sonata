package com.golfettozh.sonata.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MusicRequestDTO {

    @NotBlank(message = "O título não pode estar em branco")
    @Size(min = 3, max = 50, message = "O título deve ter entre 3 e 50 caracteres")
    private String title;

    @NotBlank(message = "O nome do artista é obrigatório")
    @Size(min = 3, max = 50, message = "O nome do artista deve ter entre 3 e 50 caracteres")
    private String artist;

    @NotNull(message = "A duração da música é obrigatória")
    @Positive(message = "A duração deve ser maior que zero")
    private Double durationInMinutes;
}