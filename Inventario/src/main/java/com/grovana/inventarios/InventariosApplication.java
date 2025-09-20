package com.grovana.inventarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class InventariosApplication {
    public static void main(String[] args) {
        SpringApplication.run(InventariosApplication.class, args);
    }
}
