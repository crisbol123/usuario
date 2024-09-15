package com.pragma.usuario.usuario.adapters.driving.http.controller;


import com.pragma.usuario.usuario.adapters.driving.http.dto.user.request.AuthorizationRequest;
import com.pragma.usuario.usuario.adapters.driving.http.dto.user.request.LoginRequest;
import com.pragma.usuario.usuario.adapters.driving.http.dto.user.response.AuthorizationResponse;
import com.pragma.usuario.usuario.adapters.driving.http.mapper.login.request.LoginRequestMapper;
import com.pragma.usuario.usuario.adapters.driving.http.mapper.login.response.AuthResponse;
import com.pragma.usuario.usuario.adapters.securityconfig.AuthService;
import com.pragma.usuario.usuario.domain.api.IUserServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth-user")
@RequiredArgsConstructor
public class AuthenticateController {

    private final AuthService authService;
    private final IUserServicePort userServicePort;
    private final LoginRequestMapper loginRequestMapper;
    @Operation(summary = "Find user ID by username", description = "Returns the user ID based on the provided username.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User ID found",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    @PostMapping("/validate")
    public AuthorizationResponse validateToken(@RequestBody AuthorizationRequest request) {
        return userServicePort.validateToken(request.getToken());
    }

    @Operation(summary = "User login", description = "Authenticates the user and returns an authentication token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authenticated successfully",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content)
    })
    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

        return ResponseEntity.ok(userServicePort.login(loginRequestMapper.toDomain(request)));
    }
}
