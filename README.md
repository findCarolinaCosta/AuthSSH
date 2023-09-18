# AuthSSH - API Spring REST

[![MIT LICENSE](https://camo.githubusercontent.com/c6239caa38945e7693646486b0337744e4bd84d52807a7a1756d596a0e13676a/68747470733a2f2f696d672e736869656c64732e696f2f6769746875622f6c6963656e73652f65617379626173652f65617379626173652d7265616374)](https://github.com/findCarolinaCosta/AuthSSH/blob/main/LICENSE)

## Descripsiption

A AuthSSH is a Spring REST API that allows user authentication through SSH keys.

## Funcionalities

### Resources

Resources available in the API:

`/login` - User authentication for access to API resources.

`/logout` - End user session.

`/register` - Register user data for SSH authentication.

`/authenticate` - Authenticate user through SSH key pre-registered in the system.

`/error` - Custom error page.

`/swagger-ui/index.html` - API documentation.

### API Documentation

API documentation is available via Swagger at `/swagger-ui/index.html`. You can access it
in your local browser after running the application. To authenticate, use the user
credentials defined in `application.properties` if the installation and execution are manual
or in `application-docker.properties` if using Docker.

In the properties:

``` text
spring.security.user.name=
spring.security.user.password=
```

### Authentication and Authorization

Authentication in the API is performed through basic authentication (Basic Auth). You need to
login to the `/login` route using the appropriate credentials. After login, authentication is
maintained in Spring Session. To exit, use the `/logout` route.

### Database

The PostgreSQL is used as the runtime database. Tables and columns are created automatically if
they don't exist.

OBS: it's necessary that the database AuthSSH already exists in PostgreSQL.

### Tests

Integration tests make real calls to the API and use mocks to abstract the dependency with the
database. They are implemented in `AuthSSH/test/java/com/AuthSSH/ssh`. Run the tests with the
command:

``` shell
mvn test
```

Obs: Apache Maven v3.9.4+ is required and it's also necessary to run the `mvn install` command.

## Project Requirements

- Java 17
- Spring 3.1.3

``` text
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.1.3)
```

- Spring Libraries:
    - Spring Boot Starter Data JPA v3.1.3: Integration with JPA (Java Persistence API).
    - Spring Boot Starter Security v6.1.3: Security features for Spring Boot applications.
    - Spring Boot Starter Web v3.1.3: Web application development.
    - Spring Boot Starter Validation v3.1.3: Data validation.
    - Spring Boot Starter Data Redis v3.1.3: Integration with Redis.
    - Spring Session Data Redis v3.1.2: Session management using Redis.
    - Spring Boot DevTools v3.1.3: Facilitates development with features such as hot-reload.
    - Spring Boot Starter Test v3.1.3: Dependencies for tests.
    - Spring Security Test v6.1.3: Library for security tests.
- Database:
    - PostgreSQL v42.6.0: Runtime database (scope `runtime`).
- Database for Tests:
    - H2 Database v2.2.222: Test database (scope `test`).
- Swagger Documentation:
    - Springdoc OpenAPI Starter WebMVC UI: API documentation generation with Swagger (specified
      version: `2.2.0`).
- Lombok:
    - Project Lombok: Annotations library to reduce Java code verbosity (specified version:
      `1.18.28`, scope `provided`).

## Application Execution

To run the Spring REST application locally, follow the installation steps and run the specified
commands. The API will be available for local use.

## Installation and Configuration of the Application - Manually

Follow the steps below to install and configure the application:

1. Make sure you have the following requirements installed:
    - Apache Maven v3.9.4+
    - Java 17
    - PostgreSQL
    - Redis
2. Clone the repository:

``` shell
  git clone https://github.com/findCarolinaCosta/AuthSSH.git
   cd AuthSSH
```

3. Run the following command to build and start the application:

``` shell
mvn install 
mvn spring-boot:run
```

4. Configure the database by editing the `application.properties` file in
   `AuthSSH/src/main/resources/application.properties` with your PostgreSQL user and password
   credentials.
5. Configure Spring Session to work properly by defining the Redis properties in the
   `application.properties` file as needed.

For detailed information on configuring environment variables and other settings, see the
`application-example.properties` file in `AuthSSH/src/main/resources/`.

## Installation and Configuration of the Application - With Docker

1. Make sure you have the following requirements installed:
    - Apache Maven v3.9.4+
    - Java 17
    - Docker v24.0.6+
    - Docker Compose

2. Clone the repository:

```shell
   git clone https://github.com/findCarolinaCosta/AuthSSH.git
   cd AuthSSH
```

3. Run the following command to build and start the application:

``` shell 
mvn clean package
docker compose -f docker-compose.yml up --build -d
```

4. Personalize the `docker-compose.yml` file as needed.

5. Personalize the `application-docker.properties` file
   in `AuthSSH/src/main/resources/application-docker.properties` as needed.

### Estrutura do Projeto

The project structure follows the following scheme:

``` markdown
src
 ├── main
 │   ├── java
 │   │   ├── com
 │   │       └── AuthSSH
 │   │           └── ssh
 │   │               ├── config                  // Configurations of SSH and security
 │   │               ├── controllers             // Controllers REST
 │   │               ├── dtos                    // Objects for data transfer (DTOs)
 │   │               ├── models                  // Data models
 │   │               ├── repositories            // Data repositories
 │   │               ├── services                // Service layer
 │   │               └── util                    // Utility classes
 │   └── resources
 │        ├── application.properties             // Configuration of environment variables
 │        ├── application-docker.properties      // Configuration of environment variables for Docker
 │        └── application-example.properties     // Example of configuration of environment variables
 └── test
      ├── java
      │   └── com
      │       └── AuthSSH
      │           └── ssh
      │               └── integration            // Integration tests
      └── resources
           └── application-test.properties       // Configuration of environment variables for tests
```

## License

This API is distributed under the MIT License.

## Contact

For more information, contact me [Carolina Pereira](www.linkedin.com/in/carolinapereiradev).
