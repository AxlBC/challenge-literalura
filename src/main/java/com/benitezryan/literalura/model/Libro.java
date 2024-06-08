package com.benitezryan.literalura.model;

import java.util.List;

public class Libro {
    private String titulo;
    private String autor;
    private List<String> temas;
    private List<String> estanterias;
    private boolean copyright;
    private Integer descargas;

    // Constructor vacio para el correcto funcionamiento de la base de datos
    public Libro() {}

    // Constructor para asignar las valores a cada atributo
    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.autor = datosLibro.autor();
        this.temas = datosLibro.temas();
        this.estanterias = datosLibro.estanterias();
        this.copyright = datosLibro.copyright();
        this.descargas = datosLibro.descargas();
    }

    // Getters y Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public List<String> getTemas() {
        return temas;
    }

    public void setTemas(List<String> temas) {
        this.temas = temas;
    }

    public List<String> getEstanterias() {
        return estanterias;
    }

    public void setEstanterias(List<String> estanterias) {
        this.estanterias = estanterias;
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
        return  "titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", temas=" + temas +
                ", estanterias=" + estanterias +
                ", copyright=" + copyright +
                ", descargas=" + descargas
                ;
    }
}

