# ♻️ EcoCycle - Plataforma de Productos Reciclables

**EcoCycle** es una aplicación web desarrollada con **Java + Spring Boot** que permite a los usuarios registrar, comprar, vender y calificar productos reciclables, fomentando la economía circular y el consumo responsable.

---

## 🚀 Características

- 🧾 Registro e inicio de sesión de usuarios
- 📦 Publicación, edición y eliminación de productos
- 🛍 Sistema de transacciones (compra, confirmación y rechazo)
- 🌟 Calificación entre usuarios (reputación)
- 👤 Perfiles con ecoPuntos, estado y tipo de usuario (particular o empresa)
- 📷 Subida y visualización de imágenes de productos
- 🔍 Búsqueda de productos por nombre
- 🔐 Acceso protegido mediante sesiones

---

## 🛠 Tecnologías utilizadas

| Tecnología         | Descripción                             |
|--------------------|------------------------------------------|
| Java 17            | Lenguaje principal                       |
| Spring Boot        | Framework backend                        |
| Thymeleaf          | Motor de plantillas HTML                 |
| PostgreSQL         | Base de datos relacional                 |
| Maven              | Gestión de dependencias y construcción  |
| Bootstrap & Tailwind CSS | Diseño frontend moderno y responsive |

---

## ⚙️ Requisitos

- Java 17+
- PostgreSQL
- Maven
- Un IDE como IntelliJ IDEA, Eclipse o NetBeans

---

## 🧪 Cómo ejecutar el proyecto localmente

### 1. Clonar el repositorio

```bash
git clone https://github.com/tu_usuario/eco-cycle.git
cd eco-cycle
```

### 2. Crear la base de datos PostgreSQL

```sql
CREATE DATABASE db_ecocycle;
```

### 3. Configurar tu archivo `application.properties`

Copia el archivo de ejemplo y edítalo con tus credenciales:

```bash
cp src/main/resources/application-example.properties src/main/resources/application.properties
```

### 4. Ejecutar la aplicación

Desde tu IDE o con Maven:

```bash
mvn spring-boot:run
```

Accede a la app en `http://localhost:8080/`

---

## 📁 Estructura del proyecto

```
eco-cycle/
├── controller/           # Controladores Spring MVC
├── model/                # Entidades JPA
├── repository/           # Interfaces JPA para la BD
├── service/              # Lógica de negocio
├── resources/            # HTMLs, application.properties, CSS
├── EcocycleApplication.java
├── README.md
└── pom.xml
```

---

## 💾 Archivo de configuración de ejemplo

📄 `src/main/resources/application-example.properties`:

```properties
spring.application.name=ecocycle
spring.datasource.url=jdbc:postgresql://localhost:5432/db_ecocycle
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## 🤝 Créditos

Proyecto desarrollado por estudiantes de Ingeniería en Sistemas para la materia de Desarrollo de Marcos de Desarrollo en la **Universidad Central del Ecuador**.

**Autores:**
- Juan Pablo Morillo

---

## 📜 Licencia

Este proyecto está bajo la licencia MIT. Puedes usarlo, modificarlo y distribuirlo con fines educativos.
