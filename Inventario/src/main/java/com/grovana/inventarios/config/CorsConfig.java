package com.grovana.inventarios.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Habilita CORS globalmente para toda la aplicación
        registry.addMapping("/**")
            // Permite peticiones desde el origen de tu frontend de desarrollo (Vite)
            .allowedOrigins("http://localhost:5173")
            // Permite los métodos HTTP que usarás (GET, POST, PUT, DELETE, OPTIONS)
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            // Permite el uso de cualquier header
            .allowedHeaders("*")
            // Permite credenciales (si usarás cookies o autenticación básica)
            .allowCredentials(true);
    }
}
