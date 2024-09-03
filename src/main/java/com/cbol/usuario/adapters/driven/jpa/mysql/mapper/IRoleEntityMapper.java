package com.cbol.usuario.adapters.driven.jpa.mysql.mapper;

import com.cbol.usuario.adapters.driven.jpa.mysql.entity.RoleEntity;
import org.mapstruct.Named;
@org.mapstruct.Mapper(componentModel = "spring")
public interface IRoleEntityMapper {

    @Named("toEntity")
    default RoleEntity toEntity(Long id) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(id);
        return roleEntity;
    }
    @Named("toModel")
    default Long toModel(RoleEntity roleEntity) {
        return roleEntity.getId();
    }
}
