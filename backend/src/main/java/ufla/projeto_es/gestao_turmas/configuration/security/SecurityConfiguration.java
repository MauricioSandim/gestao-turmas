package ufla.projeto_es.gestao_turmas.configuration.security;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final SecurityFilter securityFilter;

    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.fromHierarchy("ROLE_ADMIN > ROLE_PROFESSOR\nROLE_ADMIN > ROLE_ALUNO");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 1. Libera a URL de origem do seu front-end React/Vite
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));

        // 2. Libera os métodos HTTP que o front-end fará
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

        // 3. Permite os cabeçalhos necessários, principalmente o Authorization para o JWT
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));

        // 4. Necessário se for enviar credenciais/tokens de forma embutida
        configuration.setAllowCredentials(true);

        // 5. Aplica essa regra globalmente para todos os endpoints (/**)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationEntryPoint authenticationEntryPoint) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler(authenticationEntryPoint)))
                .authorizeHttpRequests(authorize -> authorize
                        // INFRAESTRUTURA E DOCUMENTAÇÃO (Público)
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/actuator/health").permitAll()
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()

                        // AUTENTICAÇÃO (Público)
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/register").permitAll()

                        // GESTÃO DE USUÁRIOS
                        .requestMatchers(HttpMethod.GET, "/api/v1/usuario/me").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/usuario").hasRole("PROFESSOR")

                        // GESTÃO DE TURMAS
                        .requestMatchers(HttpMethod.GET, "/api/v1/turmas/**").hasRole("PROFESSOR")
                        .requestMatchers(HttpMethod.POST, "/api/v1/turmas/**").hasRole("PROFESSOR")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/turmas/**").hasRole("PROFESSOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/turmas/**").hasRole("PROFESSOR")

                        .requestMatchers(HttpMethod.GET, "/api/v1/aluno/**").hasRole("PROFESSOR")

                        .requestMatchers(HttpMethod.GET, "/api/v1/area-aluno").hasRole("ALUNO")

                        // Regras genéricas de ADMIN para usuários (Delete, Update geral, Listar todos)
                        .requestMatchers("/api/v1/usuario/**").hasRole("ADMIN")

                        .anyRequest().hasRole("ADMIN"))

                .addFilterBefore(this.securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationEntryPoint customAuthenticationEntryPoint() {
        return (request, response, authException) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"status\":401,\"error\":\"Unauthorized\",\"message\":\"Falha na autenticação. Token inválido ou ausente.\",\"path\":\"" + request.getRequestURI() + "\"}");
        };
    }

    private AccessDeniedHandler customAccessDeniedHandler(AuthenticationEntryPoint authenticationEntryPoint) {
        return (request, response, accessDeniedException) -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            // Se não há autenticação ou é anônimo, retorna 401 em vez de 403
            if (authentication == null || !authentication.isAuthenticated() ||
                    "anonymousUser".equals(authentication.getPrincipal())) {
                authenticationEntryPoint.commence(request, response, null);
            } else {
                // Se há autenticação mas sem permissão, retorna 403
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"status\":403,\"error\":\"Forbidden\",\"message\":\"Acesso negado. Você não possui permissão para este recurso.\",\"path\":\"" + request.getRequestURI() + "\"}");
            }
        };
    }
}