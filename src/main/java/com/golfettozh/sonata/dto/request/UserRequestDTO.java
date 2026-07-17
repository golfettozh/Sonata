package com.golfettozh.sonata.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.golfettozh.sonata.model.user.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserRequestDTO(

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "Insira um formato de e-mail válido")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
        String password,

        @NotNull(message = "O papel do usuário não pode ser nulo")
        UserRole role
) {}
