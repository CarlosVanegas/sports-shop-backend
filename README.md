# 🏀 SportsShop Backend API

Este backend ha sido desarrollado con **Spring Boot**, **JPA**, **MySQL/MariaDB** y **JWT** para la autenticación. Forma parte del sistema de una tienda de artículos deportivos con arquitectura basada en microservicios.

---

## 🧰 Requisitos

* Java 17
* Maven
* MySQL/MariaDB 8+

---

## ⚙️ Configuración Inicial

1. Clona el repositorio:

```bash
git clone git@github.com:CarlosVanegas/sportshop.git
```

2. Renombra el archivo `application.properties.example` a:

```bash
src/main/resources/application.properties
```

3. Ajusta los valores de conexión:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/sports_shop?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true

server.port=8080

jwt.secret=UnaClaveMuySecretaQueDebesCambiar1234567890
jwt.expiration-ms=3600000
```

4. Asegúrate de tener creada la base de datos `sports_shop` y ejecuta los scripts de `sql/schema.sql` si es necesario.

---

## 📁 Estructura del Proyecto

```
sports-shop-backend/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/com/prueba/sportsshop/backend/
│   │   │   ├── config/          ← Configuración de seguridad y JWT
│   │   │   ├── controller/      ← Controladores REST
│   │   │   ├── dto/             ← DTOs (entradas y salidas JSON)
│   │   │   ├── entity/          ← Entidades JPA
│   │   │   ├── exception/       ← Manejo de errores personalizados
│   │   │   ├── repository/      ← Interfaces JPA
│   │   │   ├── security/        ← Filtros y helpers JWT
│   │   │   ├── service/         ← Lógica de negocio
│   │   │   └── util/            ← Utilidades varias
│   │   └── resources/
│   │       └── application.properties
└── sql/
    └── schema.sql              ← Script para creación de tablas
```

---

## 🧪 Ejecución del Proyecto

```bash
./mvnw spring-boot:run
```

Accede a: [http://localhost:8080](http://localhost:8080)

---

## 🛠 Esquema de Base de Datos

Incluye las siguientes tablas:

* `users`, `product_categories`, `products`
* `carts`, `cart_items`
* `checkout_sessions`, `payment_transactions`
* `orders`, `order_details`

El script se encuentra en: `sql/schema.sql`

---

## 📬 Endpoints Principales (Resumen)

### 1. Autenticación

* `POST /api/auth/register`: Registro de usuario
* `POST /api/auth/login`: Login con retorno de JWT
* `POST /api/auth/recover`: Recuperación de contraseña

### 2. Perfil de Usuario

* `GET /api/users/me`: Obtener perfil
* `PUT /api/users/me`: Actualizar información
* `PUT /api/users/me/password`: Cambiar contraseña

### 3. Categorías

* `GET /api/categories`: Listar categorías
* `POST /api/categories`: Crear categoría

### 4. Productos

* `GET /api/products`: Listar productos públicos
* `GET /api/products/{id}`: Detalle de producto
* `POST /api/products`: Crear producto (admin)
* `PUT /api/products/{id}`: Actualizar producto
* `DELETE /api/products/{id}`: Eliminar producto

### 5. Carrito

* `GET /api/cart`: Ver carrito
* `POST /api/cart/items`: Agregar ítem
* `DELETE /api/cart/items/{id}`: Quitar ítem

### 6. Checkout & Órdenes

* `POST /api/checkout`: Iniciar checkout
* `POST /api/checkout/{id}/pay`: Procesar pago y confirmar orden
* `GET /api/orders`: Historial de órdenes
* `GET /api/orders/{id}`: Detalle de orden

### 7. Transacciones

* `GET /api/transactions/{id}`: Consultar estado
* `POST /api/transactions`: Registrar transacción

---

## 🔐 Seguridad

* Autenticación basada en JWT
* Los endpoints sensibles requieren encabezado:

```
Authorization: Bearer <token>
```

---

## 📌 Notas Finales

* Toda la lógica crítica (checkout, pedidos) está anotada con `@Transactional`
* El backend está preparado para conectarse a un frontend en React/Next.js vía REST

---

> Para más detalles o mantenimiento del backend, revisa la documentación interna de cada paquete en `src/main/java/com/prueba/sportsshop/backend/`
