package com.benitezryan.literalura_challenge_test.model;

import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String titulo;
//    private List<String> temas;
    private List<String> idiomas;
//    private List<String> estanterias;
    private boolean copyright;
    private Integer descargas;

    @ManyToOne
    private Autor autor;

    // Constructor vacio para el correcto funcionamiento de la base de datos
    public Libro() {}

    // Constructor para asignar las valores a cada atributo
    public Libro(DatosLibro datosLibro, DatosAutor datosAutor) {
        this.titulo = datosLibro.titulo();
        this.autor = new Autor(datosAutor);
//        this.temas = datosLibro.temas();
        this.idiomas = datosLibro.idiomas();
//        this.estanterias = datosLibro.estanterias();
        this.copyright = datosLibro.copyright();
        this.descargas = datosLibro.descargas();
    }

    // Getters y Setters
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public boolean isCopyright() {
        return copyright;
    }

    public void setCopyright(boolean copyright) {
        this.copyright = copyright;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    // Métodos
    @Override
    public String toString() {
        return String.format("""
                -------------------------------------------
                Título: %s
                Autor: %s
                Idiomas: %s
                Copyright: %b
                Descargas: %d
                """, this.titulo, this.autor.getNombre(), this.idiomas, this.copyright, this.descargas);

//        return String.format("""
//                -------------------------------------------
//                Título: %s
//                Autor: %s
//                Temas: %s
//                Idiomas: %s
//                Copyright: %b
//                Descargas: %d
//                """, this.titulo, this.autor.getNombre(), this.temas, this.idiomas, this.copyright, this.descargas);
    }
}
