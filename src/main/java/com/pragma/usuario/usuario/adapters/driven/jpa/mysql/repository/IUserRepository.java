package com.pragma.usuario.usuario.adapters.driven.jpa.mysql.repository;


import com.pragma.usuario.usuario.adapters.driven.jpa.mysql.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u.id FROM UserEntity u WHERE u.email = :email")
    Optional<Long> findIdByEmail(String email);
@Query("SELECT u.id FROM UserEntity u WHERE u.identityDocument = :document")
    Optional<Long> findIdByIdentityDocument(String document);

    Optional<UserEntity> findByEmail(String email);

}
