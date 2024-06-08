package com.benitezryan.literalura_challenge_test.repository;

import com.benitezryan.literalura_challenge_test.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IAutorRepository extends JpaRepository<Autor, Long> {

    //SELECT Nombre
    //FROM Autor
    //WHERE "Año de nacimiento" <= año_deseado
    //  AND ("Año de fallecimiento" IS NULL OR "Año de fallecimiento" >= año_deseado);
//    @Query("SELECT v FROM Autor a WHERE  <= %:anio%")
//    List<Autor> autorVivoEnXAnio(int anio);

    @Query("SELECT a FROM Autor a WHERE a.anioNacimiento <= :anio AND (a.anioFallecimiento IS NULL OR " +
            "a.anioFallecimiento >= :anio)")
    List<Autor> autorVivoEnXAnio(int anio);

    @Query("SELECT a FROM Autor a WHERE a.nombre = :nombre")
    Autor findByName(String nombre);
}
