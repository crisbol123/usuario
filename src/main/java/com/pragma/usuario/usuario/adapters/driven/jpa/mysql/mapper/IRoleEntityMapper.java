package com.pragma.usuario.usuario.adapters.driven.jpa.mysql.mapper;


import com.pragma.usuario.usuario.adapters.driven.jpa.mysql.entity.RoleEntity;
import com.pragma.usuario.usuario.adapters.driven.jpa.mysql.repository.IRoleRepository;
import org.mapstruct.Context;
import org.mapstruct.Named;
@org.mapstruct.Mapper(componentModel = "spring")
public interface IRoleEntityMapper {


    @Named("toEntity")
    default RoleEntity toEntity(String roleName, @Context IRoleRepository iRoleRepository) {

        return  iRoleRepository.findByName(roleName);
    }
    @Named("toModel")
    default String toModel(RoleEntity roleEntity) {
        return roleEntity.getName();
    }
}
