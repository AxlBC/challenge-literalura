package com.benitezryan.literalura_challenge.model.libro;

import com.benitezryan.literalura_challenge.model.autor.Autor;
import com.benitezryan.literalura_challenge.model.autor.DatosAutor;
import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String titulo;
    private String idioma;
    private boolean copyright;
    private Integer descargas;

    @ManyToOne
    private Autor autor;

    // Constructor vacio para el correcto funcionamiento de la base de datos
    public Libro() {}

    // Constructor para asignar los valores a cada atributo
    public Libro(DatosLibro datosLibro, DatosAutor datosAutor) {
        this.titulo = datosLibro.titulo();
        this.autor = new Autor(datosAutor);
//        this.idiomas = datosLibro.idiomas()
        this.idioma = datosLibro.idiomas().get(0);
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

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
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
                Idioma: %s
                Copyright: %b
                Descargas: %d""", this.titulo, this.autor.getNombre(), this.idioma, this.copyright, this.descargas);
    }
}
