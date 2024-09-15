package com.pragma.usuario.usuario.adapters.driving.http.mapper.user;



import com.pragma.usuario.usuario.adapters.driving.http.dto.user.request.CreateUserRequest;
import com.pragma.usuario.usuario.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IUserRequestMapper {
@Mapping(target = "roleName", ignore = true)
User toDomain(CreateUserRequest request);

}