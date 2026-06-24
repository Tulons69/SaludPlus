# SaludPlus — Sistema de Gestión de Microservicios

## Descripción del Proyecto

SaludPlus es un sistema de gestión de salud basado en arquitectura de microservicios desarrollado con Spring Boot. El sistema permite administrar pacientes y doctores de forma independiente, con comunicación entre servicios mediante OpenFeign, documentación automática con Swagger/OpenAPI, pruebas unitarias con JUnit y Mockito, y enrutamiento centralizado mediante API Gateway.

---

## Integrantes

| Nombre | Empresa |
|---|---|
| Diego González | Raíces Informáticas |

---

## Microservicios Implementados

| Microservicio | Puerto | Base de Datos | Descripción |
|---|---|---|---|
| paciente-service | 8080 | db_pacientes | Gestión de pacientes, HATEOAS, carga masiva, backup |
| doctor-service | 8081 | db_doctores | Gestión de doctores, HATEOAS, backup automático |
| api-gateway | 8085 | — | Enrutamiento centralizado entre microservicios |

---

## Rutas Principales del Gateway

| Método | Ruta Gateway | Redirige a |
|---|---|---|
| GET/POST | `localhost:8085/gateway/pacientes` | paciente-service :8080 |
| GET/POST | `localhost:8085/gateway/doctores` | doctor-service :8081 |
| GET | `localhost:8085/gateway/v2/pacientes` | paciente-service HATEOAS :8080 |
| GET | `localhost:8085/gateway/v2/doctores` | doctor-service HATEOAS :8081 |
| GET | `localhost:8085/gateway/tester/paciente/test-completo` | Tester paciente-service |
| GET | `localhost:8085/gateway/tester/doctor/test-completo` | Tester doctor-service |

---

## Documentación Swagger

| Microservicio | URL Swagger |
|---|---|
| paciente-service | http://localhost:8080/doc/swagger-ui/index.html |
| doctor-service | http://localhost:8081/doc/swagger-ui/index.html |

---

## Tecnologías Utilizadas

- Java 21
- Spring Boot 3.3.5
- Spring Cloud 2023.0.3 (OpenFeign + Gateway)
- Spring Data JPA / Hibernate
- Flyway (migraciones SQL versionadas)
- MySQL 8.x (Laragon)
- Swagger / SpringDoc OpenAPI 2.6.0
- Spring HATEOAS
- JUnit 5 + Mockito (pruebas unitarias)
- Lombok

---

## Instrucciones de Ejecución Local

### Requisitos previos
- Java 21 instalado
- Laragon corriendo con MySQL activo
- VS Code con extensiones: Spring Boot Dashboard, Extension Pack for Java

### 1. Crear las bases de datos en Laragon

Abre la terminal de Laragon y ejecuta:

```sql
CREATE DATABASE db_pacientes;
CREATE DATABASE db_doctores;
```

### 2. Arrancar los microservicios en orden

Abre cada carpeta en VS Code y usa el Spring Boot Dashboard para arrancar:

```
1. SaludPlus-Final   → puerto 8080
2. doctor-service    → puerto 8081
3. api-gateway       → puerto 8085
```

### 3. Verificar que todo funciona

```
GET http://localhost:8085/gateway/pacientes
GET http://localhost:8085/gateway/doctores
```

### 4. Correr las pruebas unitarias

En la terminal de cada microservicio:

```bash
mvn test
```

Resultado esperado:
```
Tests run: 7, Failures: 0, Errors: 0
BUILD SUCCESS
```

---

## Estructura del Proyecto

```
SaludPlus_V2/
├── SaludPlus-Final/          # paciente-service (puerto 8080)
│   ├── src/main/java/
│   │   └── SaludPlus/SaludPlus/
│   │       ├── assembler/    # HATEOAS assembler
│   │       ├── client/       # OpenFeign DoctorClient
│   │       ├── config/       # SwaggerConfig
│   │       ├── controller/   # PacienteController, PacienteControllerV2, CargaController
│   │       ├── dto/          # DoctorDTO, PacienteDTO
│   │       ├── model/        # Paciente
│   │       ├── repository/   # PacienteRepository
│   │       ├── service/      # PacienteService, BackupService, BackupScheduler
│   │       └── tester/       # TesterController
│   └── src/test/java/
│       └── SaludPlus/SaludPlus/
│           └── service/      # PacienteServiceTest (JUnit + Mockito)
│
├── doctor-service/           # doctor-service (puerto 8081)
│   ├── src/main/java/
│   │   └── SaludPlus/DoctorService/
│   │       ├── assembler/    # HATEOAS assembler
│   │       ├── config/       # SwaggerConfig
│   │       ├── controller/   # DoctorController, DoctorControllerV2
│   │       ├── model/        # Doctor
│   │       ├── repository/   # DoctorRepository
│   │       ├── service/      # DoctorService, BackupService, BackupScheduler
│   │       └── tester/       # TesterController
│   └── src/test/java/
│       └── SaludPlus/DoctorService/
│           └── service/      # DoctorServiceTest (JUnit + Mockito)
│
└── api-gateway/              # API Gateway (puerto 8085)
    └── src/main/resources/
        └── application.yml   # Rutas del Gateway
```

---

## Endpoints Completos

### paciente-service (sin Gateway)

```
POST   http://localhost:8080/api/v1/pacientes
GET    http://localhost:8080/api/v1/pacientes
GET    http://localhost:8080/api/v1/pacientes/{rut}
DELETE http://localhost:8080/api/v1/pacientes/{id}
GET    http://localhost:8080/api/v2/pacientes              (HATEOAS)
GET    http://localhost:8080/api/v2/pacientes/total
GET    http://localhost:8080/api/v2/pacientes/prevision/{prevision}
POST   http://localhost:8080/api/pacientes/masivo          (carga masiva)
GET    http://localhost:8080/api/tester/test-completo
POST   http://localhost:8080/admin/db/backup
POST   http://localhost:8080/admin/db/repair
GET    http://localhost:8080/admin/db/info
```

### doctor-service (sin Gateway)

```
POST   http://localhost:8081/api/v1/doctores
GET    http://localhost:8081/api/v1/doctores
GET    http://localhost:8081/api/v1/doctores/{id}
DELETE http://localhost:8081/api/v1/doctores/{id}
GET    http://localhost:8081/api/v2/doctores              (HATEOAS)
GET    http://localhost:8081/api/v2/doctores/total
GET    http://localhost:8081/api/v2/doctores/especialidad/{especialidad}
GET    http://localhost:8081/api/tester/test-completo
POST   http://localhost:8081/admin/db/backup
POST   http://localhost:8081/admin/db/repair
```
