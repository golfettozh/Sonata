package com.golfettozh.sonata.dto.request;

public record AuthenticationRequestDTO(
        String email,
        String password
) {}
