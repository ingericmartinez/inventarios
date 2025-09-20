# Inventario API (Spring Boot 3)

API RESTful de gestión de inventarios (enfoque inicial en Categorías y Proveedores), siguiendo la especificación OpenAPI adjunta (src/main/resources/static/inventarios.yaml).

## Requisitos
- Java 21+
- Gradle

## Ejecutar
```
./gradlew bootRun
```
La app utiliza una base de datos H2 en memoria y se inicializa con `schema.sql` y `data.sql`.

- Página de inicio: http://localhost:8080/ (pública)
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs
- OpenAPI YAML: http://localhost:8080/v3/api-docs.yaml
- H2 Console: http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:inventario`)
- Actuator: http://localhost:8080/actuator (health, info)

## Seguridad
Autenticación: Basic Auth (usuarios en memoria)
- user / user123 (ROLE_USER)
- admin / admin123 (ROLE_ADMIN)

Reglas de acceso:
- Público: POST /usuarios (simulación de registro)
- USER o ADMIN: GET /categorias, GET /categorias/{id}, GET /proveedores, GET /proveedores/{id}
- Solo ADMIN: POST/PUT/DELETE en /categorias y /proveedores

## Endpoints principales
- Categorías: `GET /categorias`, `POST /categorias`, `GET /categorias/{id}`, `PUT /categorias/{id}`, `DELETE /categorias/{id}`
- Proveedores: `GET /proveedores`, `POST /proveedores`, `GET /proveedores/{id}`, `PUT /proveedores/{id}`, `DELETE /proveedores/{id}`

Paginación:
- Estándar Spring Data: `page` (0-based), `size` y `sort` (p. ej. `?page=0&size=20`).
- Compatibilidad: también acepta `limit` como alias de `size`.
- Límite de tamaño: máximo 100 por solicitud.

## Manejo de errores
Se utiliza `@ControllerAdvice` para devolver errores con estructura `ApiError` y códigos: 400 (validación), 404 (no encontrado), 409 (conflicto: p.ej., eliminar categoría/proveedor con productos asociados).

## Tests
Ejecutar:
```
./gradlew test
```
Incluye pruebas unitarias para la lógica de conflicto en servicios, pruebas de integración de seguridad, y WebMvc tests para controladores.

## Perfiles (dev/prod)
- Actualmente no hay entorno prod. Se añadieron `application-dev.properties` y `application-prod.properties` como base.
- Por ahora, dev y prod comparten la misma configuración (H2, Swagger, H2 console, Actuator limitado a health/info).
- Cuando exista prod real, ajustar DataSource, deshabilitar H2 console y restringir endpoints de Actuator.

## Arquitectura
- Ver guía de arquitectura y recomendaciones para producción en ARCHITECTURE.md
