package com.aprilboiz.musicpage.auth.dto;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(
        @NotEmpty(message = "Username is required")
        String username,

        @NotEmpty(message = "Password is required")
//        @Min(value = 1, message = "Password must be at least 8 characters long")
        String password) {
}
