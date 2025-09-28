# Banking Tech Backend - Prueba Técnica

## 📌 Explicación general

Este proyecto corresponde a la **prueba técnica de Banking Tech**, en la cual se implementa un **servicio backend en Spring Boot** para:

- Autorización de usuarios mediante JWT.
- Creación, edición y eliminación de **monedas** y **criptomonedas**.
- Persistencia en base de datos PostgreSQL (contenedor Docker).
- Seguridad con Spring Security, BCrypt y control de sesiones.

El proyecto fue desarrollado en **Java 14 con Spring Boot**, utilizando **Spring Data JPA** y **Spring Security**.

---

## 🐳 1. Requerimiento de contenedor de Docker

Para correr la base de datos es necesario tener **Docker** instalado en el equipo.

### 🔹 Instalación de Docker

- **Windows**: instalar Docker Desktop desde [Docker Desktop Windows](https://www.docker.com/products/docker-desktop/).
- **Linux**: seguir la guía oficial de instalación [Docker Linux](https://docs.docker.com/engine/install/).
- **macOS (iOS)**: instalar Docker Desktop desde [Docker Desktop Mac](https://www.docker.com/products/docker-desktop/).

### 🔹 Imágenes utilizadas

- **PostgreSQL 16**: base de datos principal donde se guardan usuarios, monedas y criptomonedas.
- **pgAdmin 4.8**: cliente gráfico web para administrar PostgreSQL.


### 🔹 Cómo levantar los contenedores

Una vez clonado el proyecto y con Docker instalado, en la carpeta raíz ejecuta:

```bash
docker compose up -d
```

Esto levantará los servicios de **PostgreSQL 16** y **pgAdmin 4.8** en segundo plano.


### 🔹 Credenciales de conexión a la base de datos

```
Host: localhost
Port: 5432
Database: monedasdb
Username: monedas
Password: monedas
```

Estas credenciales pueden usarse en herramientas como **TablePlus**, **pgAdmin** o cualquier cliente SQL.

pgAdmin estará disponible en:

```
http://localhost:5050
```

Con credenciales por defecto configuradas en el `docker-compose.yml`:

```
Email: admin@example.com
Password: admin
```

---

## ⚙️ 2. Requerimiento para correr el backend

- Proyecto desarrollado en **Spring Boot 3** con **Java 14**.
- Dependencias principales:
  - Spring Web
  - Spring Security
  - Spring Data JPA
  - PostgreSQL Driver
  - JWT (io.jsonwebtoken)
  - Lombok

### 🔹 IDE / Editores recomendados

- IntelliJ IDEA
- Eclipse
- VS Code con extensión Java

### 🔹 Comandos básicos

Compilar y empaquetar:

```bash
mvn clean package
```

Correr el proyecto:

```bash
mvn spring-boot:run
```

El backend se levantará en:

```
http://localhost:8080
```

---

## 🔑 3. Endpoints de seguridad

### 🔹 Login

```
POST /auth/login
```

Ejemplo de body:

```json
{
  "email": "admin@gmail.com",
  "password": "clave123"
}
```

- Retorna un **JWT** válido para consumir el resto de endpoints.
- Cada login se registra en la tabla `user_session` con fecha de login y metadatos.

### 🔹 Registro de usuarios

```
POST /auth/register
```

Ejemplo de body:

```json
{
  "name": "admin",
  "email": "admin@gmail.com",
  "role": "ADMINISTRATOR",
  "password": "clave123"
}
```

Los roles disponibles son:

- `ADMINISTRATOR`
- `REGULAR_USER`

📌 **Nota:** En esta versión, no se restringen endpoints por rol, solo se valida que el usuario tenga un JWT válido.

### 🔹 Importante

Para ejecutar cualquier otro endpoint (monedas o criptomonedas) es necesario:

1. Registrar un usuario.
2. Hacer login para obtener el token.
3. Incluir el token en cada request:

```
Authorization: Bearer <token>
```

---

## 💰 4. Endpoints de monedas

### 🔹 Listar monedas

```
GET /moneda
```

Ejemplo de respuesta:

```json
[
  {
    "id": 1,
    "codigo": "USD",
    "nombre": "Dolar Estadounidense",
    "criptomonedas": []
  }
]
```

### 🔹 Crear moneda

```
POST /moneda
```

Body de ejemplo:

```json
{
  "codigo": "USD",
  "nombre": "Dolar Estadounidense"
}
```

Respuesta exitosa:

```json
{
  "id": 1,
  "codigo": "USD",
  "nombre": "Dolar Estadounidense",
  "criptomonedas": null
}
```

- Si el código ya existe → retorna `409 Conflict`.

📌 **Nota**: el atributo `codigo` es clave para asociar criptomonedas a una moneda.

### 🔹 Eliminar moneda

```
DELETE /moneda/{id}
```

Ejemplo:

```
DELETE /moneda/2
```

⚠️ **Importante**: al eliminar una moneda también se eliminarán todas las criptomonedas asociadas a ella, debido al diseño en cascada de la base de datos.

---

## 🪙 5. Endpoints de criptomonedas

### 🔹 Crear criptomoneda

```
POST /criptomoneda
```

Body de ejemplo:

```json
{
  "simbolo": "USDT",
  "monedaCodigo": "USD",
  "nombre": "USDT"
}
```

- `monedaCodigo` debe ser el código de una moneda existente.
- Si el código es inválido → retorna `400 Bad Request`.

Respuesta exitosa (`201 Created`):

```json
{
  "id": 1,
  "simbolo": "USDT",
  "nombre": "USDT"
}
```

### 🔹 Relación moneda – criptomonedas

Después de crear una criptomoneda asociada a USD, el endpoint de monedas reflejará la relación:

```
GET /moneda
```

Respuesta:

```json
[
  {
    "id": 1,
    "codigo": "USD",
    "nombre": "Dolar Estadounidense",
    "criptomonedas": [
      {
        "id": 1,
        "simbolo": "USDT",
        "nombre": "USDT"
      }
    ]
  }
]
```


### 🔹 Listar criptomonedas (opcional con filtro por moneda)

```
GET /criptomoneda
GET /criptomoneda?moneda=USD
```

- Si se llama sin parámetros → retorna todas las criptomonedas con su relación de moneda.
- Si se pasa el parámetro `moneda=USD` (o cualquier código válido) → retorna **solo las criptomonedas asociadas a esa moneda**.

Ejemplo de respuesta:

```json
[
  {
    "id": 1,
    "simbolo": "USDT",
    "nombre": "USDT",
    "monedaCodigo": "USD",
    "monedaNombre": "Dolar Estadounidense"
  }
]
```


---

## 📝 Notas adicionales

- El proyecto incluye configuración de **Spring Security** con filtros JWT personalizados.
- Contraseñas de usuarios se almacenan en la base de datos usando **BCrypt**.
- El diseño de entidades usa JPA con relaciones:
  - `Moneda` (OneToMany) → `Criptomoneda` (ManyToOne).
- Se recomienda usar DTOs (`MonedaRequest`, `CriptomonedaRequest`) para la creación/edición y evitar exponer entidades directamente.

---

## 🚀 Resumen

Este backend permite:
- Registrar y autenticar usuarios con JWT.
- Crear, listar y eliminar monedas.
- Crear y listar criptomonedas asociadas a monedas.
- Proteger todos los endpoints con validación de token.

Cumple con los requerimientos de la prueba técnica de **Banking Tech** para gestión de usuarios y activos digitales.


---

## 🗄️ 6. Estructura de tablas en la base de datos

La base de datos PostgreSQL contiene las siguientes tablas principales, derivadas de las entidades JPA:

### 🔹 Tabla `monedas`

```sql
CREATE TABLE monedas (
    id BIGSERIAL PRIMARY KEY,
    codigo VARCHAR(10) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL
);
```

### 🔹 Tabla `criptomonedas`

```sql
CREATE TABLE criptomonedas (
    id BIGSERIAL PRIMARY KEY,
    simbolo VARCHAR(20) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    moneda_id BIGINT NOT NULL,
    CONSTRAINT fk_crypto_moneda FOREIGN KEY (moneda_id)
        REFERENCES monedas(id)
        ON DELETE CASCADE
);
```

### 🔹 Tabla `user`

> ⚠️ La tabla se llama `"user"` con comillas debido a que *user* es palabra reservada en PostgreSQL.

```sql
CREATE TABLE "user" (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    registration_date TIMESTAMP,
    last_login TIMESTAMP
);
```

### 🔹 Tabla `user_sessions`

```sql
CREATE TABLE user_sessions (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    registration_date TIMESTAMP,
    login_time TIMESTAMP
);
```
