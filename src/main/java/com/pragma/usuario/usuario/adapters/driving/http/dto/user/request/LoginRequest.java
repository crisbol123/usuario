package com.pragma.usuario.usuario.adapters.driving.http.dto.user.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Login request containing user's email and password")
public class LoginRequest {

    @Schema(description = "User's email", example = "john.doe@example.com")
    private String email;

    @Schema(description = "User's password", example = "password123")
    private String password;

    public LoginRequest() {//empty constructor
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
