package com.benitezryan.literalura_challenge.repository;

import com.benitezryan.literalura_challenge.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IAutorRepository extends JpaRepository<Autor, Long> {

    @Query("SELECT a FROM Autor a WHERE a.anioNacimiento <= :anio AND (a.anioFallecimiento IS NULL OR " +
            "a.anioFallecimiento >= :anio)")
    List<Autor> autorVivoEnXAnio(int anio);

    @Query("SELECT a FROM Autor a WHERE a.nombre = :nombre")
    Autor findByName(String nombre);
}
