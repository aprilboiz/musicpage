package com.aprilboiz.musicpage.exception.dto;

import org.springframework.http.HttpStatus;

public record ApiErrorResponse(
        int status,
        String error,
        String description) {
    public ApiErrorResponse(int status, String description) {
        this(status, HttpStatus.valueOf(status).getReasonPhrase(), description);
    }
}
