package com.aprilboiz.musicpage.auth.dto;

public record LoginResponse(String token, String token_type) {

    public LoginResponse(String token){
        this(token, "Bearer");
    }

}
