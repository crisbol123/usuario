package com.pragma.usuario.usuario.adapters.driven.jpa.mysql.mapper;

import com.pragma.usuario.usuario.adapters.driven.jpa.mysql.entity.UserEntity;
import com.pragma.usuario.usuario.adapters.driven.jpa.mysql.repository.IRoleRepository;
import com.pragma.usuario.usuario.domain.model.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {IRoleEntityMapper.class})
public interface IUserEntityMapper {


    @Mapping(target = "role", source = "roleName", qualifiedByName = "toEntity")
    @Mapping(target = "password", ignore = true)
    UserEntity toEntity(User user, @Context IRoleRepository roleRepository);


@Mapping(target = "roleName", source = "role", qualifiedByName = "toModel")
    User toDomain(UserEntity userEntity);
}
