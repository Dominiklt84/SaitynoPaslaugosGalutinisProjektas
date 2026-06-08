package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Pagrindinė Spring Boot programos paleidimo klasė.
 */
@SpringBootApplication
@EnableCaching
public class KinoFilmaiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KinoFilmaiApplication.class, args);
	}

}
