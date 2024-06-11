package com.benitezryan.literalura_challenge.main;

import com.benitezryan.literalura_challenge.model.*;
import com.benitezryan.literalura_challenge.repository.IAutorRepository;
import com.benitezryan.literalura_challenge.repository.ILibroRepository;
import com.benitezryan.literalura_challenge.service.ConsumoAPI;
import com.benitezryan.literalura_challenge.service.ConvierteDatos;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private final Scanner scanner = new Scanner(System.in);
    private final ConsumoAPI consumoApi = new ConsumoAPI();
    private final ConvierteDatos conversor = new ConvierteDatos();
    //https://gutendex.com/books/?search=great
    private final String URL_BOOKS = "https://gutendex.com/books/?search=";
    private List<Libro> datosLibros = new ArrayList<>();
    private List<Autor> datosAutores = new ArrayList<>();

    private List<Autor> autoresRegistradosEnLaBD = new ArrayList<>();
    private List<Libro> librosRegistradosEnLaBD = new ArrayList<>();

    // Creando el repositorio que estaremos utilizando
    private final IAutorRepository autorRepository;
    private final ILibroRepository libroRepository;

    public Main(IAutorRepository autorRepository, ILibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    // Muestra el menú que verá el usuario
    public void mostrarMenu() {
        try {
            var opcion = -1;
            while (opcion != 0) {
                var menu = """
                    
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idiomas
                    6 - Estadisticas de descargas
                    7 - Top 10 libros más descargados
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
                    case 6:
                        generarEstadisticas();
                        break;
                    case 7:
                        top10LibrosMasDescargados();
                        break;
                    case 0:
                        System.out.println("Cerrando la aplicación.");
                        break;
                    default:
                        System.out.println("Opción invalida, pruebe de nuevo.");
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error: Opción no válida\n" + e);
        }
    }

    // Métodos principales

    // Busca un libro por nombre o autor en la API y lo registra en la BD
    private void buscarLibroPorTitulo() throws DataIntegrityViolationException{
        Optional<Libro> libro = Optional.ofNullable(getDatosLibros());
        if(libro.isPresent()) {
            insertarLibroYAutor(libro.get());
            System.out.println(libro.get());
        } else {
            System.out.println("No se encontró ninguna coincidencia.\n" +
                    "Asegurese de escribir el título o nombre del autor correctamente.");
        }
    }

    // Trae los libros registrados en la base de datos y los muestra
    private void obtenerLibrosRegistrados() {
        datosLibros = libroRepository.findAll();

        datosLibros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(System.out::println);
    }

    // Obtener los autores registrados en la base de datos y mostrarlos en la consola
    private void obtenerAutoresRegistrados() {
        autoresRegistradosEnLaBD = autorRepository.findAll();

        autoresRegistradosEnLaBD.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(System.out::println);
        System.out.println("----------------------------------------------------------------\n");
    }

    // Listar los autores vivos en el año especificado por el usuario
    private void autoresVivosEnXAnio() {
        System.out.print("Escriba el año que quiere comprobar: ");
        var anio = scanner.nextInt();
        scanner.nextLine();

        System.out.println("----------------------------------------------------------------\n" +
                "Los autores vivos en el año son:" + anio);
        autorRepository.autorVivoEnXAnio(anio).stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(a -> System.out.println(a.getNombre()));
        System.out.println("----------------------------------------------------------------\n");
    }

    // Lista los libros por el primer idioma en la lista de idiomas
    private void listarLibrosPorIdiomas() {
        try {
            List<String> idiomas = new ArrayList<>();
            idiomas.add("es");
            idiomas.add("en");
            idiomas.add("fr");
            idiomas.add("pt");
            System.out.println("""
                Elija un idioma para listar los libros:
                es - Español
                en - Inglés
                fr - Francés
                pt - Portugués
                """);
            String opcion = scanner.nextLine();

            if (idiomas.contains(opcion.toLowerCase())) {
                datosLibros = libroRepository.findAll();
                datosLibros.stream()
                        .filter(l -> l.getIdioma().equalsIgnoreCase(opcion))
                        .sorted(Comparator.comparing(Libro::getTitulo))
                        .forEach(l -> System.out.println("----------------------------------------------------------------\n"
                                + "Libro: " + l.getTitulo() + "\nIdioma: " + l.getIdioma() +
                                "\n----------------------------------------------------------------\n"));
            } else {
                System.out.println("Por favor, seleccione uno de los idiomas disponibles");
                listarLibrosPorIdiomas();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Estadisticas del número de descargas totales y correspondientes a cada libro
    private void generarEstadisticas() {
        librosRegistradosEnLaBD = libroRepository.findAll();

        DoubleSummaryStatistics est = librosRegistradosEnLaBD.stream()
                .filter(l -> l.getDescargas() > 0.0)
                .collect(Collectors.summarizingDouble(Libro::getDescargas));

        System.out.println("Número de libros registrados: " + est.getCount());
        System.out.println("Suma de descargas totales registradas: " + est.getSum());
        System.out.println("Máximo número de descargas de un libro: " + est.getMax());
        System.out.println("Mínimo número de descargas de un libro: " + est.getMin());
        System.out.println("Promedio de descargas: " + est.getAverage());
    }

    // Muestra cúales son los 10 libros con el mayor número de descargas imprimiendolos de mayor a menos
    private void top10LibrosMasDescargados() {
        List<Libro> libros = libroRepository.findTop10ByOrderByDescargasDesc();

        System.out.println("Los 10 libros más descargados del momento son: ");
        libros.forEach(l -> System.out.println("Libro: " + l.getTitulo() + " - " +
                "Descargas: " + l.getDescargas()));
    }

    // Métodos auxiliares
    private Libro getDatosLibros() {
        try {
            // Obtiene la respuesta de la API
            System.out.println("Escribe el nombre del libro que deseas buscar");
            var nombreLibro = scanner.nextLine();
//            System.out.println(URL_BOOKS + nombreLibro.replace(" ", "%20"));
            var json = consumoApi.obtenerDatos(URL_BOOKS + nombreLibro.replace(" ", "%20"));

            // Se extrae solo la información de la sección "results" del json
            FiltroInfoResult filtroInfoResult = conversor.obtenerDatos(json, FiltroInfoResult.class);

            // Se procesan los resultados de la busqueda y en caso de ser nulos retorna null
            if (filtroInfoResult.resultado().isEmpty()) {
                return null;
            } else {
                // Se obtiene una lista con todos los resultados obtenidos
                List<Libro> listaObtenida = procesarInfoLibros(filtroInfoResult);
                if (listaObtenida.size() > 1) {
                    // Retorna el Libro elegido por el usuario
                    return seleccionarDeLaListaDeLibrosObtenida(listaObtenida);
                } else {
                    return listaObtenida.get(0);
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Error: " + e);
        }
        return null;
    }

    // Retorna una lista del tipo Libro con todos los resultados obtenidos
    private List<Libro> procesarInfoLibros(FiltroInfoResult result) {
        String jsonAutor, jsonLibro;
        Optional<JsonNode> jsonNodeAutor;
        int resultadosObtenidos = result.resultado().size();
        List<Libro> listaDeLibros = new ArrayList<>();
        DatosAutor datosAutorDefecto = new DatosAutor("Desconocido", 0, 0);

        for (int i = 0; i < resultadosObtenidos; i++) {
            jsonLibro = (result.resultado().get(i)).toString();
            jsonNodeAutor = Optional.ofNullable(result.resultado().get(i).get("authors").get(0));

            DatosLibro datosLibro = conversor.obtenerDatos(jsonLibro, DatosLibro.class);
            if (jsonNodeAutor.isPresent()) {
                jsonAutor = jsonNodeAutor.get().toString();

                listaDeLibros.add(new Libro(datosLibro, conversor.obtenerDatos(jsonAutor, DatosAutor.class)));
            } else {
                listaDeLibros.add(new Libro(datosLibro, datosAutorDefecto));
            }
        }
        return listaDeLibros;
    }

    // Pregunta al usuario cuál de todos los libros obtenidos es el que está buscando
    private Libro seleccionarDeLaListaDeLibrosObtenida(List<Libro> lista) {
        try {
            System.out.println("Lista de resultados de la busqueda: ");
            for (int i = 0; i < lista.size(); i++) {
                System.out.println((i + 1) + " - " + lista.get(i).getTitulo());
            }
            System.out.println("\nSeleccione el indice del libro buscado.");
            var opcion = scanner.nextInt();
            scanner.nextLine();

            if (opcion >= 1 && opcion <= lista.size()) {
                return lista.get(opcion - 1);
            } else {
                System.out.println("Error: No hay ningun elemento en ese indice");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    // Inserta el libro dentro de la base de datos verificando primero que no exista de antemano
    private void insertarLibroYAutor(Libro libro) {
        Optional<Autor> autorExiste = Optional.ofNullable(autorRepository.findByName(libro.getAutor().getNombre()));
        Optional<Libro> libroExiste = Optional.ofNullable(libroRepository.findByName(libro.getTitulo()));

        try {
            if (!autorExiste.isPresent() && !libroExiste.isPresent()) {
                autorRepository.save(libro.getAutor());
            } else if (autorExiste.isPresent() && !libroExiste.isPresent()) {
                libro.setAutor(autorExiste.get());
            } else if (libroExiste.isPresent() && !autorExiste.isPresent()) {
                autorRepository.save(libro.getAutor());
            } else {
                System.out.println("Este libro ya se encuentra registrado.");
                System.out.println(libro);
                return;
            }
            libroRepository.save(libro);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}
