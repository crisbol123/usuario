package com.pragma.usuario.usuario.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "roles")
@Getter
@Setter
public class RoleEntity {
    @Id
    @Column(name = "name", nullable = false, unique = true)
    private String name;


}