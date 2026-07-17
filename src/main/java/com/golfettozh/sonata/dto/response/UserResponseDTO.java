package com.golfettozh.sonata.dto.response;

import com.golfettozh.sonata.model.user.User;
import com.golfettozh.sonata.model.user.UserRole;

import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String email,
        UserRole role
) {
    public UserResponseDTO(User user) {
        this(
                user.getId(),
                user.getEmail(),
                user.getRole()
        );
    }
}
