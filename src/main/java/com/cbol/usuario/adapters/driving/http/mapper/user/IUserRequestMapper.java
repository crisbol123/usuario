package com.cbol.usuario.adapters.driving.http.mapper.user;


import com.cbol.usuario.adapters.driving.http.dto.user.request.CreateUserRequest;
import com.cbol.usuario.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IUserRequestMapper {
@Mapping(target = "roleId", ignore = true)
    User toDomain(CreateUserRequest request);

}