package com.codoacodo.libreria.dtos;

public class UsuarioDTO {

    private Long id_usuario;
    private String email;

    public UsuarioDTO(Long id_usuario, String email) {
        this.id_usuario = id_usuario;
        this.email = email;
    }

    // Getters y setters

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}