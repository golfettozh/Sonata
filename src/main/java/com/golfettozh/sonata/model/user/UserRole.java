package com.golfettozh.sonata.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {
    ROLE_USER("user"),
    ROLE_ADMIN("admin"),
    ROLE_ARTIST("artist");

    private String role;
}
