# AuthSSH - API Spring REST

[![MIT LICENSE](https://camo.githubusercontent.com/c6239caa38945e7693646486b0337744e4bd84d52807a7a1756d596a0e13676a/68747470733a2f2f696d672e736869656c64732e696f2f6769746875622f6c6963656e73652f65617379626173652f65617379626173652d7265616374)](https://github.com/findCarolinaCosta/AuthSSH/blob/main/LICENSE)

## Descrição

A AuthSSH é uma API Spring REST que permite a autenticação de usuários por meio de chaves SSH.

## Funcionalidades

### Recursos

Recursos disponíveis na API:

`/login` - Autenticação de usuário para acesso a recursos da API.

`/logout` - Encerrar sessão de usuário.

`/register` - Registrar dados de usuário para autenticação SSH.

`/authenticate` - Autenticar usuário por meio de chave SSH pré-registrada no sistema.

`/error` - Página de erro personalizada.

`/swagger-ui/index.html` - Documentação da API.

### Documentação da API

A documentação da API está disponível via Swagger em `/swagger-ui/index.html`. Você pode acessá-la
no seu navegador local após a execução da aplicação. Para autenticar, use as credenciais de usuário
e senha do spring security definidas em `application.properties` caso a execeção e instalação for
manual ou em `application-docker.properties`.

Nas propriedades:

``` text
spring.security.user.name=
spring.security.user.password=
```

### Autenticação e Autorização

A autenticação na API é realizada por meio de autenticação básica (Basic Auth). Você precisa fazer
login na rota `/login` usando as credenciais apropriadas. Após o login, a autenticação é mantida no
Spring Session. Para sair, utilize a rota `/logout`.

### Banco de Dados

O PostgreSQL é usado como banco de dados em tempo de execução. As tabelas e colunas são criadas
automaticamente se não existirem, mas é necessário que o banco de dados já exista no PostgreSQL.

### Testes

Os testes de integração fazem chamadas reais à API e usam mocks para abstrair a dependência com o
banco de dados. Eles estão implementados em `AuthSSH/test/java/com/AuthSSH/ssh`. Execute os testes
com o comando:

``` shell
mvn test
```

Obs: é necessario Apache Maven v3.9.4+ e também necessário executar o comando `mvn install` antes de
executar os testes.

## Requisitos do projeto

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

- Bibliotecas do Spring:
    - Spring Boot Starter Data JPA v3.1.3: Integração com JPA (Java Persistence API).
    - Spring Boot Starter Security v6.1.3: Recursos de segurança para aplicativos Spring Boot.
    - Spring Boot Starter Web v3.1.3: Desenvolvimento de aplicativos web.
    - Spring Boot Starter Validation v3.1.3: Validação de dados.
    - Spring Boot Starter Data Redis v3.1.3: Integração com o Redis.
    - Spring Session Data Redis v3.1.2: Gerenciamento de sessão usando o Redis.
    - Spring Boot DevTools v3.1.3: Facilita o desenvolvimento com recursos como hot-reload.
    - Spring Boot Starter Test v3.1.3: Dependências para testes.
    - Spring Security Test v6.1.3: Biblioteca para testes de segurança.
- Banco de Dados:
    - PostgreSQL v42.6.0: Banco de dados em tempo de execução (escopo `runtime`).
- Banco de Dados para Testes:
    - H2 Database v2.2.222: Banco de dados de teste (escopo `test`).
- Testes:
    - JUnit Jupiter: Para escrever testes JUnit 5 (escopo `test`).
- Documentação com Swagger:
    - Springdoc OpenAPI Starter WebMVC UI: Geração de documentação da API com Swagger (versão
      especificada: `2.2.0`).
- Lombok:
    - Project Lombok: Biblioteca de anotações para reduzir a verbosidade do código Java (versão
      especificada: `1.18.28`, escopo `provided`).

## Execução da Aplicação

Para iniciar a aplicação Spring REST localmente, siga os passos de instalação e execute os comandos
especificados. A API estará disponível para uso localmente.

## Instalação e Configuração da Aplicação - Manualmente

Siga os passos abaixo para instalar e configurar a aplicação:

1. Certifique-se de ter os seguintes requisitos instalados:
    - Apache Maven v3.9.4+
    - Java 17
    - PostgreSQL
    - Redis

2. Clone o repositório:
   ```shell
   git clone https://github.com/findCarolinaCosta/AuthSSH.git
   cd AuthSSH

```

3. Execute o seguinte comando para construir e iniciar a aplicação:
``` shell
mvn install 
mvn spring-boot:run
```

4. Configure o banco de dados editando o arquivo `application.properties`
   em `AuthSSH/src/main/resources/application.properties` com suas credenciais de usuário e senha do
   PostgreSQL.

5. Configure o Spring Session para funcionar corretamente definindo as propriedades do Redis no
   arquivo `application.properties` conforme necessário.

Para obter informações detalhadas sobre a configuração de variáveis de ambiente e outras
configurações, consulte o arquivo `application-example.properties` em `AuthSSH/src/main/resources/`.

## Instalação e Configuração da Aplicação - Com Docker

1. Certifique-se de ter os seguintes requisitos instalados:
    - Apache Maven v3.9.4+
    - Java 17
    - Docker v24.0.6+
    - Docker Compose

2. Clone o repositório:

```shell
   git clone https://github.com/findCarolinaCosta/AuthSSH.git
   cd AuthSSH
```

3. Execute o seguinte comando para construir e iniciar a aplicação:

``` shell 
mvn clean package
docker compose -f docker-compose.yml up --build -d
```

4. Personalize o arquivo `docker-compose.yml` conforme necessário.

5. Personalize o arquivo `application-docker.properties`
   em `AuthSSH/src/main/resources/application-docker.properties` conforme necessário.

## Estrutura do Projeto

A estrutura do projeto segue o seguinte esquema:

``` markdown
src
 ├── main
 │   ├── java
 │   │   ├── com
 │   │       └── AuthSSH
 │   │           └── ssh
 │   │               ├── config                  // Configuração do SSH e segurança
 │   │               ├── controllers             // Controladores REST
 │   │               ├── dtos                    // Objetos de transferência de dados(DTOs)
 │   │               ├── models                  // Modelos de dados
 │   │               ├── repositories            // Repositórios de dados
 │   │               ├── services                // Camada de serviço
 │   │               └── util                    // Utilitários e classes auxiliares
 │   └── resources
 │        ├── application.properties             // Configuração da aplicação
 │        └── application-example.properties     // Exemplo de configuração de variáveis 
 └── test
      ├── java
      │   └── com
      │       └── AuthSSH
      │           └── ssh
      │               └── integration            // Testes de integração
      └── resources
           └── application-test.properties       // Configuração de variáveis para testes
```

## Licença

Esta API é distribuída sob a Licença MIT.

## Contato

Para obter mais informações ou suporte, entre em contato
com [Carolina Pereira](www.linkedin.com/in/carolinapereiradev).
