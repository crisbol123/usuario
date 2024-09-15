package com.pragma.usuario.usuario.domain.model;

public class Role {
    private Long id;
    private String name;

    // Constructor
    public Role(Long id, String name) {
        // Validaciones de lógica de negocio si son necesarias
        this.id = id;
        this.name = name;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
