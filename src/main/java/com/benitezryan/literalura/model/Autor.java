package com.benitezryan.literalura.model;

import jakarta.persistence.*;

import java.util.List;

/*
 * 1 - Convertimos esta clase en una entidad para nuestra base de datos
 * 2 - Indicamos el nombre que tendrá esta entidad en nuestra base de datos
 */
@Entity
@Table(name = "autores")
public class Autor {
    /*
     * 3 - Todos nuestros autores tendrán un identificador único (Id)
     * 4 - El Id será generado automáticamente
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    /*
     * 5 - Indicamos que el nombre será único en nuestra base de datos
     */
    @Column(unique = true)
    private String nombre;
    private int anioNacimiento;
    private int anioFallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    // Constructores
    // Constructor vacio para el correcto funcionamiento de la base de datos
    public Autor() {
    }

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.anioNacimiento = datosAutor.anioNacimiento();
        this.anioFallecimiento = datosAutor.anioFallecimiento();
    }

    public Autor(String nombre, int anioNacimiento, int anioFallecimiento) {
        if (nombre.isEmpty()) {
            this.nombre = "Unknown";
        } else { this.nombre = nombre; }
        this.anioNacimiento = anioNacimiento;
        this.anioFallecimiento = anioFallecimiento;
    }

    public Autor(Libro libro) {
        this.nombre = libro.getAutor().nombre;
        this.anioNacimiento = libro.getAutor().anioNacimiento;
        this.anioFallecimiento = libro.getAutor().getAnioFallecimiento();
        this.libros.add(libro);
    }

    // Getters y Setters
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAnioNacimiento() {
        return anioNacimiento;
    }

    public void setAnioNacimiento(int anioNacimiento) {
        this.anioNacimiento = anioNacimiento;
    }

    public int getAnioFallecimiento() {
        return anioFallecimiento;
    }

    public void setAnioFallecimiento(int anioFallecimiento) {
        this.anioFallecimiento = anioFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    // Métodos
    @Override
    public String toString() {
        return String.format("""
                ----------------------------------------------------------------
                Información del autor:
                Nombre: %s
                Año de nacimiento: %d
                Año de fallecimiento: %d
                Libros: %s""",
                this.nombre, this.anioNacimiento, this.anioFallecimiento, this.libros.stream()
                        .map(l -> "'" + l.getTitulo() + "'")
                        .toList());
    }
}
