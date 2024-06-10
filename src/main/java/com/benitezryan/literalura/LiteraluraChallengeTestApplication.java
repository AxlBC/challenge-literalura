package com.benitezryan.literalura;

import com.benitezryan.literalura.main.Main;
import com.benitezryan.literalura.repository.IAutorRepository;
import com.benitezryan.literalura.repository.ILibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraChallengeTestApplication implements CommandLineRunner {
	// Se realiza una inyección de dependencia
	@Autowired
	private IAutorRepository autorRepository;
	@Autowired
	private ILibroRepository libroRepository;
	public static void main(String[] args) {
		SpringApplication.run(LiteraluraChallengeTestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(autorRepository, libroRepository);
		main.mostrarMenu();
	}
}

// Southern Pacific Company. Passenger Department
// (Sin autor) Sir Gawain and the Lady of Lys
// (Más de 1 autor) The Whole Family: a Novel by Twelve Authors

/*
 * Pulimiento:
 * 1 - Agregar un enum para los idiomas
 * 2 - Generar detalles por defecto en caso de recibir ciertos parametros como null (hecho)
 * 3 - Modificar las relaciones para que acepte varios autores
 * 4 - Agregar extras
 */