package com.codoacodo.libreria.repositories;

import com.codoacodo.libreria.models.LibroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<LibroModel, Long> {
}