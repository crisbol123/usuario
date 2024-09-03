package com.cbol.usuario.adapters.driving.http.controller;


import com.cbol.usuario.adapters.driving.http.dto.user.request.CreateUserRequest;
import com.cbol.usuario.adapters.driving.http.dto.user.request.LoginRequest;
import com.cbol.usuario.adapters.driving.http.dto.user.response.AuthResponse;
import com.cbol.usuario.adapters.driving.http.mapper.user.IUserRequestMapper;
import com.cbol.usuario.adapters.securityconfig.AuthService;
import com.cbol.usuario.domain.api.IUserServicePort;
import com.cbol.usuario.domain.model.User;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final AuthService authService;
    private final IUserRequestMapper userRequestMapper;
    private final IUserServicePort userServicePort;

    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request)
    {
        return ResponseEntity.ok(authService.login(request));
    }
    @PostMapping("/create-assistant")
    public void createWarehouseAssistant(@RequestBody CreateUserRequest request) {
        User user = userRequestMapper.toDomain(request);
        user.setRoleId(1L);
        userServicePort.createUser(user);
    }}