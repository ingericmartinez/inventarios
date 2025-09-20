# Documentación Swagger / OpenAPI para Inventario API

Este proyecto expone documentación OpenAPI interactiva utilizando Springdoc (Swagger UI).

## Cómo acceder

- Swagger UI: http://localhost:8080/swagger-ui.html
  - Alternativamente: http://localhost:8080/swagger-ui/index.html
- Especificación OpenAPI JSON: http://localhost:8080/v3/api-docs
- Especificación OpenAPI YAML: http://localhost:8080/v3/api-docs.yaml

Nota: La API también incluye un archivo OpenAPI de referencia en `src/main/resources/static/inventarios.yaml`, pero Swagger UI muestra la especificación generada en tiempo de ejecución a partir de los controladores reales.

## Seguridad y acceso

- La UI y los endpoints de documentación están públicos (no requieren autenticación) según la configuración de Spring Security.
- Los endpoints de la API requieren Basic Auth:
  - USER: `user / user123` (sólo lectura)
  - ADMIN: `admin / admin123` (lectura y escritura)

## Endpoints principales documentados

- Categorías
  - GET /categorias
  - GET /categorias/{id}
  - POST /categorias (ADMIN)
  - PUT /categorias/{id} (ADMIN)
  - DELETE /categorias/{id} (ADMIN)
- Proveedores
  - GET /proveedores
  - GET /proveedores/{id}
  - POST /proveedores (ADMIN)
  - PUT /proveedores/{id} (ADMIN)
  - DELETE /proveedores/{id} (ADMIN)

Los modelos de request/response se infieren de los DTOs:
- CategoriaDto, CategoriaInputDto
- ProveedorDto, ProveedorInputDto

Las validaciones Bean Validation (anotaciones Jakarta @NotBlank, @Email, @Size, etc.) serán reflejadas en los esquemas de OpenAPI.

## Personalización opcional

Si quieres ajustar metadatos (título, descripción, versión) o el servidor base, puedes añadir una clase de configuración como ejemplo:

```java
// src/main/java/com/grovana/inventarios/config/OpenApiConfig.java
package com.grovana.inventarios.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI inventarioOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Inventario API")
                .description("API REST para gestión de inventarios (Categorías y Proveedores)")
                .version("v1"));
    }
}
```

Esto no es obligatorio; Springdoc funciona sin esta clase, pero añade metadatos a la UI.

## Problemas comunes

- 404 en `/swagger-ui.html`: asegúrate de que la dependencia `springdoc-openapi-starter-webmvc-ui` esté en el build.gradle y que la app esté corriendo.
- 401/403 al llamar endpoints desde la UI: recuerda que los endpoints requieren Basic Auth. Usa el botón `Authorize` en Swagger UI e ingresa `user/user123` (GET) o `admin/admin123` (POST/PUT/DELETE).
- No ves los endpoints: verifica que los controladores están bajo el paquete base del proyecto y que la app inició sin errores.

## Referencias
- Springdoc: https://springdoc.org/
- Swagger UI: https://swagger.io/tools/swagger-ui/
