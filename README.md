# Proyecto E-commerce

Este es un proyecto de E-commerce desarrollado con Angular en el frontend y Spring Boot en el backend. La aplicación permite a los usuarios realizar compras, administrar productos, gestionar direcciones de envío y métodos de pago, entre otras funcionalidades.

## Tabla de Contenidos

- [Características](#características)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Requisitos Previos](#requisitos-previos)
- [Configuración del Backend (Spring Boot)](#configuración-del-backend-spring-boot)
- [Configuración del Frontend (Angular)](#configuración-del-frontend-angular)
- [Ejecución del Proyecto](#ejecución-del-proyecto)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Contribuciones](#contribuciones)
- [Licencia](#licencia)

## Características

- **Gestión de Productos**: CRUD de productos y categorías.
- **Carrito de Compras**: Añadir y gestionar productos en el carrito.
- **Checkout**: Procesar compras y seleccionar métodos de pago.
- **Autenticación y Autorización**: Registro y login de usuarios.
- **Gestión de Direcciones**: Añadir y seleccionar direcciones de envío.
- **Multilenguaje**: Soporte para traducciones de estado de pedidos.

## Tecnologías Utilizadas

### Backend
- **Spring Boot**
- **Spring Data JPA**
- **Spring Security**
- **MySQL** o **PostgreSQL** (Base de datos)
- **Eureka Server** (para microservicios)
- **API Gateway**

### Frontend
- **Angular**
- **TypeScript**
- **Bootstrap** (Opcional)
- **RxJS**

## Requisitos Previos

### Backend
- Java 11 o superior
- Maven 3.x
- MySQL o PostgreSQL

### Frontend
- Node.js v14 o superior
- Angular CLI

## Configuración del Backend (Spring Boot)

1. **Clona el repositorio**:

    ```bash
    git clone [https://github.com/tu-usuario/proyecto-ecommerce.git](https://github.com/methaporce/ms-products-web.git)
    cd proyecto-ecommerce/backend
    ```

2. **Configura la base de datos**:
    - Configura las credenciales de tu base de datos en `src/main/resources/application.properties` o `application.yml`.

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
    spring.datasource.username=usuario
    spring.datasource.password=contraseña
    spring.jpa.hibernate.ddl-auto=update
    ```

3. **Construye y ejecuta la aplicación**:

    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

## Configuración del Frontend (Angular)

1. **Instala las dependencias**:

    ```bash
    cd frontend
    npm install
    ```

2. **Configura las variables de entorno**:
    - Modifica `src/environments/environment.ts` para apuntar a la URL del backend.

    ```typescript
    export const environment = {
      production: false,
      apiUrl: 'http://localhost:8080/api',
      userIdTest: 1
    };
    ```

3. **Inicia el servidor de desarrollo**:

    ```bash
    ng serve
    ```

4. **Accede a la aplicación**:
    - Abre un navegador y ve a `http://localhost:4200`.

## Ejecución del Proyecto

- Inicia el backend (Spring Boot) y el frontend (Angular) como se describe arriba.
- Navega a `http://localhost:4200` para acceder a la aplicación.

## Estructura del Proyecto

### Backend (Spring Boot)

```bash
src
├── main
│   ├── java
│   │   └── com/metaphorce
│   │       ├── controller
│   │       ├── service
│   │       ├── repository
│   │       ├── model
│   │       └── config
│   └── resources
│       └── application.properties
└── test

src
├── app
│   ├── components
│   ├── services
│   ├── models
│   ├── pages
│   ├── app.component.ts
│   └── app.module.ts
└── assets
