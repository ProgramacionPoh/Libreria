package com.codoacodo.libreria.controllers;

import com.codoacodo.libreria.models.LibroModel;
import com.codoacodo.libreria.models.UsuarioModel;
import com.codoacodo.libreria.services.LibroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/libros")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    // Endpoint para obtener todos los libros
    @GetMapping("/buscar")
    public ResponseEntity<Iterable<LibroModel>> obtenerTodosLosLibros() {
        Iterable<LibroModel> libros = libroService.obtenerTodosLosLibros();
        return ResponseEntity.ok(libros);
    }

    // Endpoint para obtener libro por titulo
    public ResponseEntity<Set<LibroModel>> buscarLibrosPorTermino(@RequestParam String termino) {
        Set<LibroModel> libros = libroService.buscarLibrosPorTermino(termino);
        return ResponseEntity.ok(libros);
    }

    // Endpoint para crear un libro nuevo
    @PostMapping
    public ResponseEntity<LibroModel> crearLibro(@RequestBody LibroModel libro) {
        LibroModel libroGuardado = libroService.guardarLibro(libro);
        return ResponseEntity.ok(libroGuardado);
    }

    // Endpoint para obtener un libro por ID
    @GetMapping("/{id}")
    public ResponseEntity<LibroModel> obtenerLibroPorId(@PathVariable Long id) {
        Optional<LibroModel> libro = libroService.buscarPorId(id);
        return libro.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para agregar un libro a los favoritos de un usuario
    @PostMapping("/{libroId}/favorito/{usuarioId}")
    public ResponseEntity<UsuarioModel> agregarFavorito(@PathVariable Long usuarioId, @PathVariable Long libroId) {
        Optional<UsuarioModel> usuario = libroService.agregarFavorito(usuarioId, libroId);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    // Endpoint para obtener los libros favoritos de un usuario
    @GetMapping("/favoritos/{usuarioId}")
    public ResponseEntity<Set<LibroModel>> obtenerFavoritos(@PathVariable Long usuarioId) {
        Optional<Set<LibroModel>> favoritos = libroService.obtenerFavoritos(usuarioId);
        return favoritos.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para eliminar un libro por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLibro(@PathVariable Long id) {
        boolean eliminado = libroService.eliminarLibro(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}