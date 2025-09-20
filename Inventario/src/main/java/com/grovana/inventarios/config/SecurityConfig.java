package com.grovana.inventarios.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager users() {
        UserDetails user = User.withUsername("user").password("{noop}user123").roles("USER").build();
        UserDetails admin = User.withUsername("admin").password("{noop}admin123").roles("ADMIN").build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(auth -> auth
                // Public homepage and static assets
                .requestMatchers(HttpMethod.GET, "/", "/index.html", "/static/**").permitAll()
                // Public H2 console (frame options disabled below)
                .requestMatchers("/h2-console/**").permitAll()
                // Public Swagger UI and OpenAPI docs
                .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                // Public registration stub
                .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                // Role-based for categorias/proveedores (nuevo prefijo y rutas legacy para compatibilidad)
                .requestMatchers(HttpMethod.GET, "/api/v1/categorias/**", "/api/v1/proveedores/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/categorias/**", "/proveedores/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/categorias", "/api/v1/proveedores").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/categorias/**", "/api/v1/proveedores/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/categorias/**", "/api/v1/proveedores/**").hasRole("ADMIN")
                // Any other request must be authenticated
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());
        // Allow H2 console frames
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        // Orígenes típicos de front en dev; ajustar según necesidad
        config.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:4200", "http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept", "Origin", "X-Requested-With"));
        config.setAllowCredentials(true);
        // Exponer headers si el front lo requiere
        config.setExposedHeaders(List.of("Location"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
