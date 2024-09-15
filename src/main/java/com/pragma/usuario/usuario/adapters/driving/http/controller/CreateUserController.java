package com.pragma.usuario.usuario.adapters.driving.http.controller;


import com.pragma.usuario.usuario.adapters.driving.http.dto.user.request.CreateUserRequest;
import com.pragma.usuario.usuario.adapters.driving.http.mapper.user.IUserRequestMapper;
import com.pragma.usuario.usuario.domain.api.IUserServicePort;
import com.pragma.usuario.usuario.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/create-user")
@RequiredArgsConstructor
public class CreateUserController {
    private final IUserRequestMapper userRequestMapper;
    private final IUserServicePort userServicePort;


    @PostMapping("/ware-house-assistant")
    public void createWarehouseAssistant(@RequestBody CreateUserRequest request) {
        User user = userRequestMapper.toDomain(request);
        userServicePort.createWareHouseAssistant(user);
    }
    @PostMapping("/customer")
    public void createCustomer(@RequestBody CreateUserRequest request) {
        User user = userRequestMapper.toDomain(request);
        userServicePort.createCustomer(user);
    }
    @PostMapping("/admin")
    public void createAdmin(@RequestBody CreateUserRequest request) {
        User user = userRequestMapper.toDomain(request);
        userServicePort.createAdmin(user);
    }





}