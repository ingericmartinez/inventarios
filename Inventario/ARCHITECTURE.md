# Arquitectura y Preparación para Producción

Este documento resume el estado actual de la arquitectura, recomendaciones para salir a producción y opciones de patrones aplicables (Hexagonal/Ports & Adapters, Sagas para consistencia distribuida, CQRS) con un plan incremental y mínimo impacto en el código existente.

## Estado actual
- Capas presentes: Controller (Web/REST) → Service (dominio/aplicación) → Repository (persistencia JPA). Separación clara de DTOs de entrada/salida y entidades.
- Seguridad: Spring Security con RBAC por roles (USER/ADMIN) y Basic Auth in-memory (para dev).
- Persistencia: H2 en memoria con schema.sql y data.sql (dev). Entidades básicas: Categoria, Proveedor, Producto. Restricciones de eliminación con validación de negocio.
- Observabilidad: Actuator (health, info) habilitado.
- Documentación: OpenAPI/Swagger UI.
- Tests: Unitarios de servicios y pruebas de integración de seguridad; WebMvc standalone para controladores.

Conclusión: La API es apta para un entorno dev/demo. Para producción, se recomiendan ajustes de hardening y, si se desea, una evolución hacia un estilo Hexagonal moderado.

## ¿Hexagonal (Ports & Adapters) ahora o después?
La estructura ya es “en capas” y se puede evolucionar sin un gran refactor. Propuesta incremental:

1) Definir puertos (interfaces) en el dominio/aplicación:
- Ej.: CategoriaRepositoryPort, ProveedorRepositoryPort con los métodos que usa la capa de servicio.
- Los adapters actuales serían implementaciones basadas en Spring Data JPA (JpaCategoriaRepositoryAdapter) delegando a los repositorios existentes.

2) Invertir las dependencias en los servicios:
- Los servicios dependen de puertos, no de Spring Data directamente. Facilita pruebas, intercambiar persistencia o añadir caching.

3) Mantener los controladores como adaptadores de entrada (REST) y mapear DTOs→casos de uso.

Esto puede hacerse de forma progresiva por agregado (categorías primero), manteniendo la compatibilidad pública de la API.

## ¿Sagas?
Sagas aplican a transacciones de larga duración y consistencia eventual entre múltiples microservicios o recursos. Este proyecto es un monolito sencillo con una única BD; por ahora no se justifica la complejidad de Sagas. Recomendación:
- No implementar Sagas en este monolito.
- Si en el futuro se separan bounded contexts (inventario, compras, ventas) en microservicios y aparecen flujos distribuidos (p. ej. orden de compra → reserva de inventario → facturación), entonces evaluar Sagas (coreografía con eventos o orquestación con un componente central).

## Recomendaciones para “production-ready” (mínimos necesarios)
- Seguridad:
  - Cambiar Basic Auth por JWT/OAuth2 o, al menos, usuarios externos (BD/IdP). Desactivar H2 console y Swagger UI o protegerlos por perfil.
  - Forzar HTTPS detrás de reverse proxy (X-Forwarded-Proto) y headers de seguridad (HSTS, CSP básica, X-Content-Type-Options, etc.).
- Persistencia real:
  - Configurar RDBMS (MariaDB/PostgreSQL) en application-prod.properties. Usar migraciones con Flyway o Liquibase en lugar de schema.sql/data.sql.
- Observabilidad:
  - Actuator con endpoints necesarios (health/info/metrics) y micrómetros (Prometheus) si aplica.
  - Logs estructurados (JSON) y correlación (traceId/spanId con Spring Cloud Sleuth/observability).
- Resiliencia:
  - Timeouts/pools en datasource; validación de conexiones. Manejo de errores consistente ya presente.
- Build/Deploy:
  - Añadir Dockerfile y profile prod (variables de entorno). Pipeline CI para build/test/scan.

## Cambios mínimos aplicados en este commit
- Documento ARCHITECTURE.md con guía de evolución a Hexagonal y checklist de hardening para prod.
- Sin refactor de código (cambios cero en runtime) para no arriesgar funcionalidad ya probada.

## Plan de evolución sugerido (incremental)
1. Introducir puertos para Categoría (CategoriaRepositoryPort) y su adapter JPA. Mantener firma de servicios. (1–2 PRs)
2. Repetir para Proveedor/Producto. (1–2 PRs)
3. Añadir MapStruct para mapeos DTO↔Entidad y reducir boilerplate. (1 PR)
4. Sustituir Basic Auth por JWT (spring-boot-starter-oauth2-resource-server) y desactivar Swagger/H2 en prod por perfiles. (1–2 PRs)
5. Migraciones con Flyway y datasource prod real. (1 PR)
6. Observabilidad: metrics y logs estructurados. (1 PR)

Con este enfoque, tu API puede salir a producción con bajo riesgo y una senda clara para alcanzar una arquitectura hexagonal si el proyecto escala.
