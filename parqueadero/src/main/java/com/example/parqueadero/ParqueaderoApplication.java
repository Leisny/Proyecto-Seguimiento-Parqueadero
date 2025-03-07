package com.example.parqueadero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // Indico que esta es la clase principal de mi aplicación Spring Boot
public class ParqueaderoApplication {

	/**
	 * Este es el punto de entrada de mi aplicación.
	 * Uso `SpringApplication.run()` para iniciar el contexto de Spring Boot y cargar todos los componentes.
	 * @param args Argumentos que puedo pasar al ejecutar la aplicación.
	 */
	public static void main(String[] args) {
		SpringApplication.run(ParqueaderoApplication.class, args);
	}
}
