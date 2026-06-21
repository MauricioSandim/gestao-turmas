package ufla.projeto_es.gestao_turmas.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.flyway.autoconfigure.FlywayConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class FlywayConfig {

    @Bean
    public FlywayConfigurationCustomizer flywayCustomizer(
            @Value("${api.initializer.default-admin-password}") String adminPassword) {
        return configuration -> {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            String adminPasswordHash = passwordEncoder.encode(adminPassword);

            Map<String, String> placeholders = new HashMap<>(configuration.getPlaceholders());
            placeholders.put("adminPasswordHash", adminPasswordHash);

            configuration.placeholders(placeholders);
        };
    }
}
