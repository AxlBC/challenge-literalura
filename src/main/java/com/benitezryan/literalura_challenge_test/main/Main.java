package com.benitezryan.literalura_challenge_test.main;

import com.benitezryan.literalura_challenge_test.model.*;
import com.benitezryan.literalura_challenge_test.repository.IAutorRepository;
import com.benitezryan.literalura_challenge_test.repository.ILibroRepository;
import com.benitezryan.literalura_challenge_test.service.ConsumoAPI;
import com.benitezryan.literalura_challenge_test.service.ConvierteDatos;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;

public class Main {
    private final Scanner scanner = new Scanner(System.in);
    private final ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    //https://gutendex.com/books/?search=great
    private final String URL_BOOKS = "https://gutendex.com/books/?search=";
    private List<Libro> datosLibros = new ArrayList<>();
    private List<Autor> datosAutores = new ArrayList<>();

    private List<Autor> autoresRegistradosEnLaBD = new ArrayList<>();

    // Creando el repositorio que estaremos utilizando
    private IAutorRepository autorRepository;
    private ILibroRepository libroRepository;

    public Main(IAutorRepository autorRepository, ILibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

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
                    
                    0 - Salir""";
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

    // Métodos principales

    // Busca un libro por nombre o autor en la API y lo registra en la BD
    private void buscarLibroPorTitulo() throws DataIntegrityViolationException{
        Optional<Libro> libro = Optional.ofNullable(getDatosLibros());
        if(libro.isPresent()) {
            insertarLibroYAutor(libro.get());
//
//            autorRepository.save(aux.getAutor());
//            libroRepository.save(aux);
//
//            System.out.println("aux: " + aux);
        } else {
            System.out.println("No se encontró ninguna coincidencia.\n" +
                    "Asegurese de escribir el título o nombre del autor correctamente.");
        }
    }

    // Trae lis libros registrados en la base de datos y los muestra
    private void obtenerLibrosRegistrados() {
        datosLibros = libroRepository.findAll();

        datosLibros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(System.out::println);

//        datosLibros.forEach(System.out::println);
//        for(Libro libro: datosLibros) {
//            System.out.println(libro);
//        }
    }

    private void obtenerAutoresRegistrados() {
        autoresRegistradosEnLaBD = autorRepository.findAll();

        autoresRegistradosEnLaBD.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(System.out::println);
        System.out.println("----------------------------------------------------------------\n");
    }

    private void autoresVivosEnXAnio() {
        System.out.print("Escriba el año que quiere comprobar: ");
        var anio = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Autores vivos en el año " + anio);
        autorRepository.autorVivoEnXAnio(anio).stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(a -> System.out.println(a.getNombre()));
    }

    private void listarLibrosPorIdiomas() {
        datosLibros = libroRepository.findAll();
        datosLibros.stream()
                .sorted(Comparator.comparing(l -> l.getIdiomas().get(0)))
                .forEach(l -> System.out.println(l.getIdiomas().get(0) + " - " + l.getTitulo()));
        System.out.println("----------------------------------------------------------------\n");
    }


    // Métodos auxiliares
    private Libro getDatosLibros() {
        /*
         * Este método retorna un objeto del tipo libro, y funciona de la siguiente forma:
         * 1 - Primero realiza la consulta con la API y obtiene una String en formato json con la información
         * 2 - Luego filtra la string json obtenida para guardar los datos de la sección "result" en un objeto del tipo
         *     FiltroInfoResult
         * 3 - Dependiendo de lo que se busque se puede obtener 0, 1 o más resultados archivados en un ArrayNode por lo
         *     que el método actuará de formas distintas dependiendo el caso
         * 3.1 - Si la consulta retorna una String json sin datos de libros se retorna null
         * 3.2 - Si la consulta retorna una String json con datos de exactamente 1 libro, se descompone el objeto
         *       FiltroInforResult en 2 String json que contienen los datos del Libro y los su Autor correspondientes,
         *       se convierten esos datos en dos objetos DatosLibro y DatosAutor y se juntan en un solo objeto Libro el
         *       cual se retorna.
         * 3.3 - Si la consulta retorna un json con la información de 2 o más libros, estos se agregan a una lista de
         *       objetos del tipo Libro y se pregunta al usuario si el libro buscado se encuentra en dicha lista y que
         *       ingrese el indice que tiene el libro deseado en la lista mostrada
         */
        try {
            // Obtiene la respuesta de la API
            System.out.println("Escribe el nombre del libro que deseas buscar");
            var nombreLibro = scanner.nextLine();
//            System.out.println(URL_BOOKS + nombreLibro.replace(" ", "%20"));
            var json = consumoApi.obtenerDatos(URL_BOOKS + nombreLibro.replace(" ", "%20"));

            // Se extrae solo la información de la sección "results" del json
            FiltroInfoResult filtroInfoResult = conversor.obtenerDatos(json, FiltroInfoResult.class);

            // Se procesa la información dentro del ArrayNode de filtroInfoResult
            if (!(filtroInfoResult.resultado()).isEmpty()) {

                if (filtroInfoResult.resultado().size() > 1) {
                    Optional<JsonNode> jsonNodeAutor = Optional.ofNullable(filtroInfoResult.resultado().get(0).get("authors").get(0));
                    Optional<JsonNode> jsonNodeLibro = Optional.ofNullable(filtroInfoResult.resultado().get(0));
                }

                if ((filtroInfoResult.resultado()).size() == 1) {
                    Optional<JsonNode> jsonNodeAutor = Optional.ofNullable(filtroInfoResult.resultado().get(0).get("authors").get(0));
                    Optional<JsonNode> jsonNodeLibro = Optional.ofNullable(filtroInfoResult.resultado().get(0));

                    if (jsonNodeAutor.isPresent() && jsonNodeLibro.isPresent()) {
                        var jsonAutor = (jsonNodeAutor.get()).toString();
                        var jsonLibro = (jsonNodeLibro.get()).toString();

                        DatosAutor datosAutor = conversor.obtenerDatos(jsonAutor, DatosAutor.class);
                        DatosLibro datosLibro = conversor.obtenerDatos(jsonLibro, DatosLibro.class);
                        return new Libro(datosLibro, datosAutor);
                    } else return null;
                } else {
                    List<Libro> listaDeLibrosObtenidos = new ArrayList<>();
//                    System.out.println(filtroInfoResult.resultado().size());
//                    System.out.println(filtroInfoResult.resultado());

                    for (int i = 0; i < filtroInfoResult.resultado().size(); i++) {
                        Optional<JsonNode> jsonNodeAutor = Optional.ofNullable(filtroInfoResult.resultado().get(i).get("authors").get(0));
                        Optional<JsonNode> jsonNodeLibro = Optional.ofNullable(filtroInfoResult.resultado().get(i));

                        if (jsonNodeAutor.isPresent() && jsonNodeLibro.isPresent()) {
                            var jsonAutor = (jsonNodeAutor.get()).toString();
                            var jsonLibro = (jsonNodeLibro.get()).toString();

                            DatosAutor datosAutor = conversor.obtenerDatos(jsonAutor, DatosAutor.class);
                            DatosLibro datosLibro = conversor.obtenerDatos(jsonLibro, DatosLibro.class);
                            listaDeLibrosObtenidos.add(new Libro(datosLibro, datosAutor));
//                        System.out.println(listaDeLibrosObtenidos.get(i).toString());
                        }
                    }
                    return seleccionarDeLaListaDeLibrosObtenida(listaDeLibrosObtenidos);
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Error: " + e);
        }
        return null;
    }

    private Libro seleccionarDeLaListaDeLibrosObtenida(List<Libro> lista) {
//        System.out.println("libros hallados: " + lista.size());
//        System.out.println("Lista de libros hallados: ");
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + " - " + lista.get(i).getTitulo());
        }
        System.out.println("\nSeleccione el indice del libro buscado.");
        var opcion = scanner.nextInt();
        scanner.nextLine();
        return lista.get(opcion - 1);
    }


    private void insertarLibroYAutor(Libro libro) {
        Optional<Autor> autorExiste = Optional.ofNullable(autorRepository.findByName(libro.getAutor().getNombre()));

        if (autorExiste.isPresent()) {
            libro.setAutor(autorExiste.get());
        } else {
            autorRepository.save(libro.getAutor());
        }
        libroRepository.save(libro);
    }
}
