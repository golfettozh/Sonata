package com.golfettozh.sonata.dto.request;

import com.golfettozh.sonata.model.user.UserRole;

public record RegisterRequestDTO(String email, String password, UserRole role) {
}
