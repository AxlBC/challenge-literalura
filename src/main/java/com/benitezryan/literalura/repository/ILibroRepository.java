package com.benitezryan.literalura.repository;

import com.benitezryan.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ILibroRepository extends JpaRepository<Libro, Long> {

    @Query("SELECT a FROM Libro a WHERE a.titulo = :titulo")
    Libro findByName(String titulo);
}
