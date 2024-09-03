package com.cbol.usuario.adapters.driven.jpa.mysql.mapper;
import com.cbol.usuario.adapters.driven.jpa.mysql.entity.UserEntity;
import com.cbol.usuario.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {IRoleEntityMapper.class})
public interface IUserEntityMapper {


//quiero mapear del idRole de User a role de UserEntity
    @Mapping(target = "role", source = "roleId", qualifiedByName = "toEntity")
    @Mapping(target = "password", ignore = true)
    UserEntity toEntity(User user);


@Mapping(target = "roleId", source = "role", qualifiedByName = "toModel")
    User toDomain(UserEntity userEntity);
}
