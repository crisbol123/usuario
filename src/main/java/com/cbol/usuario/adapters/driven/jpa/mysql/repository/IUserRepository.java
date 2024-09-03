package com.cbol.usuario.adapters.driven.jpa.mysql.repository;


import com.cbol.usuario.adapters.driven.jpa.mysql.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {


    Optional<UserEntity> findByEmail(String username);

}
