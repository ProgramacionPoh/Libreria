package com.codoacodo.libreria.services;

import com.codoacodo.libreria.repositories.LibroRepository;
import com.codoacodo.libreria.repositories.UsuarioRepository;
import com.codoacodo.libreria.models.LibroModel;
import com.codoacodo.libreria.models.UsuarioModel;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LibroService {

    private final LibroRepository libroRepository;
    private final UsuarioRepository usuarioRepository;

    public LibroService(LibroRepository libroRepository, UsuarioRepository usuarioRepository) {
        this.libroRepository = libroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public LibroModel guardarLibro(LibroModel libro) {
        return libroRepository.save(libro);
    }

public Optional<LibroModel> buscarPorId(Long id) {
        return libroRepository.findById(id).map(this::eliminarContraseñas);
    }

    public Set<LibroModel> obtenerTodosLosLibros() {
        return libroRepository.findAll().stream()
                .map(this::eliminarContraseñas)
                .collect(Collectors.toSet());
    }
    public Set<LibroModel> buscarLibrosPorTermino(String termino) {
        return libroRepository.findAll().stream()
                .filter(libro -> libro.getTitulo().toLowerCase().contains(termino.toLowerCase()))
                .map(this::eliminarContraseñas)
                .collect(Collectors.toSet());
    }

    private LibroModel eliminarContraseñas(LibroModel libro) {
        libro.getUsuarios().forEach(usuario -> usuario.setContraseña(null));
        return libro;
    }
    public Optional<UsuarioModel> agregarFavorito(Long usuarioId, Long libroId) {
        Optional<UsuarioModel> usuario = usuarioRepository.findById(usuarioId);
        Optional<LibroModel> libro = libroRepository.findById(libroId);

        if (usuario.isPresent() && libro.isPresent()) {
            UsuarioModel usuarioModel = usuario.get();
            usuarioModel.getFavoritos().add(libro.get());
            usuarioRepository.save(usuarioModel);
            return Optional.of(usuarioModel);
        }
        return Optional.empty();
    }

    public Optional<Set<LibroModel>> obtenerFavoritos(Long usuarioId) {
        Optional<UsuarioModel> usuario = usuarioRepository.findById(usuarioId);
        return usuario.map(UsuarioModel::getFavoritos);
    }

    public boolean eliminarLibro(Long id) {
        Optional<LibroModel> libro = libroRepository.findById(id);
        if (libro.isPresent()) {
            libroRepository.delete(libro.get());
            return true;
        }
        return false;
    }
}