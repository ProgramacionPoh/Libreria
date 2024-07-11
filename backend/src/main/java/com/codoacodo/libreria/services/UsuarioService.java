package com.codoacodo.libreria.services;
import com.codoacodo.libreria.dtos.UsuarioDTO;
import com.codoacodo.libreria.repositories.UsuarioRepository;
import com.codoacodo.libreria.models.UsuarioModel;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioModel guardarUsuario(UsuarioModel usuario) {
        return usuarioRepository.save(usuario);
    }

    public Optional<UsuarioModel> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<UsuarioDTO> buscarUsuarioDTO(Long id) {
        return usuarioRepository.findById(id)
                .map(this::convertirADTO);
    }

    public Set<UsuarioDTO> obtenerUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toSet());
    }

    private UsuarioDTO convertirADTO(UsuarioModel usuario) {
        return new UsuarioDTO(usuario.getId_usuario(), usuario.getEmail());
    }
    public Optional<UsuarioModel> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}