package com.benitezryan.literalura.main;

import com.benitezryan.literalura.service.ConsumoAPI;
import com.benitezryan.literalura.service.ConvierteDatos;

import java.util.Scanner;

public class Main {
    private Scanner scanner = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    //https://gutendex.com/books/?search=great
    private final String URL_BOOKS = "https://gutendex.com/books/?search=";

    // Muestra el menú que verá el usuario
    public void mostrarMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idiomas
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    obtenerLibrosRegistrados();
                    break;
                case 3:
                    obtenerAutoresRegistrados();
                    break;
                case 4:
                    autoresVivosEnXAnio();
                    break;
                case 5:
                    listarLibrosPorIdiomas();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación.");
                    break;
                default:
                    System.out.println("Opción invalida, pruebe denuevo.");
                    break;
                case -5:
                    System.out.println("Teste del proyecto");
                    getDatosLibros();
                    break;
            }
        }
    }

    // Métodos a utilizar
//    private DatosLibros getDatosLibros() {
    private void getDatosLibros() {
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var nombreLibro = scanner.nextLine();
        var json = consumoApi.obtenerDatos(URL_BOOKS + nombreLibro.replace(" ", "%20"));
        System.out.println(json);
//        DatosLibros datos = conversor.obtenerDatos(json, DatosLibros.class);
//        return datos;
    }

    private void buscarLibroPorTitulo() {
    }

    private void obtenerLibrosRegistrados() {
    }

    private void obtenerAutoresRegistrados() {
    }

    private void autoresVivosEnXAnio() {
    }

    private void listarLibrosPorIdiomas() {
    }
}
