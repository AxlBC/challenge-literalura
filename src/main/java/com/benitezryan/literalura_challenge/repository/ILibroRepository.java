package com.benitezryan.literalura_challenge.repository;

import com.benitezryan.literalura_challenge.model.libro.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ILibroRepository extends JpaRepository<Libro, Long> {

    @Query("SELECT a FROM Libro a WHERE a.titulo = :titulo")
    Libro findByName(String titulo);

    List<Libro> findTop10ByOrderByDescargasDesc();
}
