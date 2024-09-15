package com.pragma.usuario.usuario.adapters.driving.http.mapper.login.request;


import com.pragma.usuario.usuario.adapters.driving.http.dto.user.request.LoginRequest;
import com.pragma.usuario.usuario.domain.model.Login;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginRequestMapper {
    Login toDomain(LoginRequest request);
}
