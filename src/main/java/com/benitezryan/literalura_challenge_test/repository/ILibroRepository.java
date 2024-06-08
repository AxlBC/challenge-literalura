package com.benitezryan.literalura_challenge_test.repository;

import com.benitezryan.literalura_challenge_test.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ILibroRepository extends JpaRepository<Libro, Long> {

//    List<Libro> listarLibroPorIdioma();
}
