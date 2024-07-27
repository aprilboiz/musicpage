package com.aprilboiz.musicpage.auth.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record RegisterRequest(
        @NotEmpty(message = "Username is required")
        String username,

        @NotEmpty(message = "Password is required")
        @Min(value = 8, message = "Password must be at least 8 characters long")
        String password,

        @NotEmpty(message = "Role is required")
        @Size(min = 1, message = "At least one role is required")
        Set<String> roles

) {}
