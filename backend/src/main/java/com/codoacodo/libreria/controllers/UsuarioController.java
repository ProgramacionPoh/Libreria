package com.codoacodo.libreria.controllers;

import com.codoacodo.libreria.dtos.UsuarioDTO;
import com.codoacodo.libreria.models.UsuarioModel;
import com.codoacodo.libreria.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/usuarios")
@Validated
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Endpoint para crear un nuevo usuario
    @PostMapping
    public ResponseEntity<?> crearUsuario(@Validated @RequestBody UsuarioModel usuario) {
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("El email es obligatorio");
        }

        Optional<UsuarioModel> usuarioExistente = usuarioService.buscarPorEmail(usuario.getEmail());
        if (usuarioExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El email ya está registrado");
        }

        UsuarioModel usuarioGuardado = usuarioService.guardarUsuario(usuario);
        return ResponseEntity.ok(usuarioGuardado);
    }

    // Endpoint para obtener un usuario por ID (con contraseña, para login)
    @GetMapping("/login/{id}")
    public ResponseEntity<UsuarioModel> obtenerUsuarioPorId(@PathVariable Long id) {
        Optional<UsuarioModel> usuario = usuarioService.buscarPorId(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para obtener un usuario por ID (sin contraseña, para otros usos)
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioDTOPorId(@PathVariable Long id) {
        Optional<UsuarioDTO> usuarioDTO = usuarioService.buscarUsuarioDTO(id);
        return usuarioDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para obtener todos los usuarios (sin contraseñas)
    @GetMapping
    public ResponseEntity<Set<UsuarioDTO>> obtenerUsuarios() {
        Set<UsuarioDTO> usuarios = usuarioService.obtenerUsuarios();
        return ResponseEntity.ok(usuarios);
    }
}