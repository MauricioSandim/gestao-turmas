package ufla.projeto_es.gestao_turmas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class GestaoTurmasApplication {

	public static void main(String[] args) {
		System.out.println("HOST=" + System.getenv("SPRING_DATASOURCE_HOST"));
		System.out.println("PORT=" + System.getenv("SPRING_DATASOURCE_PORT"));
		System.out.println("DB=" + System.getenv("SPRING_DATASOURCE_DATABASE"));

		SpringApplication.run(GestaoTurmasApplication.class, args);
	}

}
