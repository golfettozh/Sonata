# Sonata

Uma API RESTful construída com Spring Boot para gerenciar usuários e coleções de músicas. Esta aplicação fornece endpoints abrangentes para criar, recuperar, atualizar e deletar registros de usuários e músicas.

## Índice

- [Visão Geral](#visão-geral)
- [Tecnologias](#tecnologias)
- [Requisitos](#requisitos)
- [Instalação](#instalação)
- [Configuração](#configuração)
- [Executando a Aplicação](#executando-a-aplicação)
- [Rotas da API](#rotas-da-api)
- [Exemplos de Requisições](#exemplos-de-requisições)
- [Banco de Dados](#banco-de-dados)
- [Estrutura do Projeto](#estrutura-do-projeto)

## Visão Geral

Sonata é uma API de gerenciamento de músicas que permite você:
- Gerenciar contas de usuários com credenciais
- Gerenciar um catálogo de músicas com detalhes das faixas
- Realizar operações CRUD em ambas as entidades
- Construído com Spring Boot 4.1.0 e Java 21

## Tecnologias

- **Java 21** - Linguagem de programação
- **Spring Boot 4.1.0** - Framework web
- **Spring Security** - Autenticação e autorização
- **Spring Data JPA** - ORM e abstração de banco de dados
- **PostgreSQL** - Banco de dados principal
- **H2 Database** - Banco de dados em memória para testes
- **Lombok** - Geração de código padrão
- **Maven** - Ferramenta de build

## Requisitos

- **Java 21** ou superior
- **PostgreSQL 12** ou superior
- **Maven 3.6** ou superior
- **Git**

## Instalação

### 1. Clonar o Repositório

```bash
git clone https://github.com/golfettozh/Sonata.git
cd Sonata
```

### 2. Instalar PostgreSQL

Certifique-se de que o PostgreSQL está instalado e executando no seu sistema.

#### No Windows:
```bash
# Usando Chocolatey
choco install postgresql
```

#### No macOS:
```bash
# Usando Homebrew
brew install postgresql@15
brew services start postgresql@15
```

#### No Linux:
```bash
# Ubuntu/Debian
sudo apt-get install postgresql postgresql-contrib
```

### 3. Criar o Banco de Dados

```bash
psql -U postgres
```

Depois no terminal do PostgreSQL:

```sql
CREATE DATABASE sonata;
```

## Configuração

### Propriedades da Aplicação

A aplicação usa diferentes perfis de configuração:

#### Configuração Principal (`application.properties`)

```properties
spring.application.name=Sonata
server.port=8081

# Perfil ativo (test ou dev)
spring.profiles.active=dev

# Configuração de JPA/Hibernate
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update

# Tratamento de Erros
spring.web.error.include-stacktrace=never
spring.web.error.include-message=never
spring.web.error.include-binding-errors=never
```

#### Configuração de Desenvolvimento (`application-dev.properties`)

```properties
spring.datasource.username=${USER_DB:postgres}
spring.datasource.password=${PASSWORD_DB:postgres}
spring.datasource.url=${URL_DB:jdbc:postgresql://localhost:5432/sonata}
spring.datasource.driver-class-name=org.postgresql.Driver
```

### Variáveis de Ambiente

Você pode sobrescrever a configuração padrão do banco de dados usando variáveis de ambiente:

- `USER_DB` - Nome de usuário do banco (padrão: `postgres`)
- `PASSWORD_DB` - Senha do banco (padrão: `postgres`)
- `URL_DB` - URL do banco (padrão: `jdbc:postgresql://localhost:5432/sonata`)

#### Definindo Variáveis de Ambiente

**No Windows (PowerShell):**
```powershell
$env:USER_DB = "seu_usuario"
$env:PASSWORD_DB = "sua_senha"
$env:URL_DB = "jdbc:postgresql://localhost:5432/sonata"
```

**No macOS/Linux (Bash):**
```bash
export USER_DB=seu_usuario
export PASSWORD_DB=sua_senha
export URL_DB=jdbc:postgresql://localhost:5432/sonata
```

### Configuração do Servidor

- **Porta:** 8081 (configurável via `server.port`)
- **Caminho de Contexto:** `/` (raiz)
- **Perfis:** `dev` (padrão), `test` (para testes)

## Executando a Aplicação

### Usando Maven

```bash
# Construir a aplicação
mvn clean install

# Executar a aplicação
mvn spring-boot:run

# Executar com um perfil específico
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

### Usando Java Direto

```bash
# Depois de construir com Maven
java -jar target/Sonata-0.0.1-SNAPSHOT.jar
```

### Executando Testes

```bash
# Executar todos os testes
mvn test

# Executar classe de teste específica
mvn test -Dtest=UserServiceTest
```

A aplicação será iniciada em `http://localhost:8081`

## Rotas da API

### Endpoints de Usuários

#### 1. Obter Todos os Usuários

**Endpoint:** `GET /users`

**Descrição:** Recupera uma lista de todos os usuários no sistema.

**Resposta:**
```json
[
  {
    "id": 1,
    "username": "john_doe",
    "email": "john@example.com"
  },
  {
    "id": 2,
    "username": "jane_smith",
    "email": "jane@example.com"
  }
]
```

**Código de Status:** `200 OK`

---

#### 2. Obter Usuário por ID

**Endpoint:** `GET /users/{id}`

**Descrição:** Recupera um usuário específico pelo seu ID.

**Parâmetros de Caminho:**
- `id` (Long, obrigatório) - O ID do usuário

**Resposta:**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com"
}
```

**Códigos de Status:**
- `200 OK` - Usuário encontrado
- `404 NOT FOUND` - Usuário não encontrado

---

#### 3. Criar Usuário

**Endpoint:** `POST /users`

**Descrição:** Cria uma nova conta de usuário.

**Corpo da Requisição:**
```json
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "senhaSegura123"
}
```

**Regras de Validação:**
- `username` - Obrigatório, não pode estar vazio
- `email` - Obrigatório, deve ser um email válido, deve ser único
- `password` - Obrigatório, não pode estar vazio

**Resposta:**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com"
}
```

**Códigos de Status:**
- `201 CREATED` - Usuário criado com sucesso
- `400 BAD REQUEST` - Erro de validação ou email já existe
- `409 CONFLICT` - Email já registrado

---

#### 4. Deletar Usuário

**Endpoint:** `DELETE /users/{id}`

**Descrição:** Deleta um usuário pelo ID.

**Parâmetros de Caminho:**
- `id` (Long, obrigatório) - O ID do usuário a deletar

**Resposta:**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com"
}
```

**Códigos de Status:**
- `200 OK` - Usuário deletado com sucesso
- `404 NOT FOUND` - Usuário não encontrado

---

### Endpoints de Músicas

#### 1. Obter Todas as Músicas

**Endpoint:** `GET /musics`

**Descrição:** Recupera uma lista de todos os registros de músicas no catálogo.

**Resposta:**
```json
[
  {
    "id": 1,
    "title": "Bohemian Rhapsody",
    "artist": "Queen",
    "durationInMinutes": 5.55
  },
  {
    "id": 2,
    "title": "Imagine",
    "artist": "John Lennon",
    "durationInMinutes": 3.03
  }
]
```

**Código de Status:** `200 OK`

---

#### 2. Obter Música por ID

**Endpoint:** `GET /musics/{id}`

**Descrição:** Recupera um registro de música específico pelo seu ID.

**Parâmetros de Caminho:**
- `id` (Long, obrigatório) - O ID da música

**Resposta:**
```json
{
  "id": 1,
  "title": "Bohemian Rhapsody",
  "artist": "Queen",
  "durationInMinutes": 5.55
}
```

**Códigos de Status:**
- `200 OK` - Música encontrada
- `404 NOT FOUND` - Música não encontrada

---

#### 3. Criar Música

**Endpoint:** `POST /musics`

**Descrição:** Adiciona um novo registro de música ao catálogo.

**Corpo da Requisição:**
```json
{
  "title": "Bohemian Rhapsody",
  "artist": "Queen",
  "durationInMinutes": 5.55
}
```

**Regras de Validação:**
- `title` - Obrigatório, não pode estar vazio
- `artist` - Obrigatório, não pode estar vazio
- `durationInMinutes` - Obrigatório, deve ser um número positivo

**Resposta:**
```json
{
  "id": 1,
  "title": "Bohemian Rhapsody",
  "artist": "Queen",
  "durationInMinutes": 5.55
}
```

**Códigos de Status:**
- `201 CREATED` - Música criada com sucesso
- `400 BAD REQUEST` - Erro de validação

---

#### 4. Deletar Música

**Endpoint:** `DELETE /musics/{id}`

**Descrição:** Deleta um registro de música pelo ID.

**Parâmetros de Caminho:**
- `id` (Long, obrigatório) - O ID da música a deletar

**Resposta:**
```json
{
  "id": 1,
  "title": "Bohemian Rhapsody",
  "artist": "Queen",
  "durationInMinutes": 5.55
}
```

**Códigos de Status:**
- `200 OK` - Música deletada com sucesso
- `404 NOT FOUND` - Música não encontrada

---

## Exemplos de Requisições

### Usando cURL

#### Criar um Usuário

```bash
curl -X POST http://localhost:8081/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "email": "john@example.com",
    "password": "senhaSegura123"
  }'
```

#### Obter Todos os Usuários

```bash
curl -X GET http://localhost:8081/users \
  -H "Accept: application/json"
```

#### Obter Usuário por ID

```bash
curl -X GET http://localhost:8081/users/1 \
  -H "Accept: application/json"
```

#### Deletar Usuário

```bash
curl -X DELETE http://localhost:8081/users/1
```

#### Criar um Registro de Música

```bash
curl -X POST http://localhost:8081/musics \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Bohemian Rhapsody",
    "artist": "Queen",
    "durationInMinutes": 5.55
  }'
```

#### Obter Todas as Músicas

```bash
curl -X GET http://localhost:8081/musics \
  -H "Accept: application/json"
```

#### Obter Música por ID

```bash
curl -X GET http://localhost:8081/musics/1 \
  -H "Accept: application/json"
```

#### Deletar Música

```bash
curl -X DELETE http://localhost:8081/musics/1
```

### Usando Postman

1. **Criar Coleção:** `Sonata API`
2. **Criar Requisições:**
   - Nome: `Criar Usuário`
     - Método: `POST`
     - URL: `http://localhost:8081/users`
     - Corpo (JSON):
       ```json
       {
         "username": "john_doe",
         "email": "john@example.com",
         "password": "senhaSegura123"
       }
       ```

   - Nome: `Obter Todos os Usuários`
     - Método: `GET`
     - URL: `http://localhost:8081/users`

   - Nome: `Criar Música`
     - Método: `POST`
     - URL: `http://localhost:8081/musics`
     - Corpo (JSON):
       ```json
       {
         "title": "Bohemian Rhapsody",
         "artist": "Queen",
         "durationInMinutes": 5.55
       }
       ```

   - Nome: `Obter Todas as Músicas`
     - Método: `GET`
     - URL: `http://localhost:8081/musics`

## Banco de Dados

### Modelos de Banco de Dados

#### Tabela Usuários

| Coluna | Tipo | Restrições | Descrição |
|--------|------|-----------|-------------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Identificador único do usuário |
| username | VARCHAR | NOT NULL | Nome de exibição do usuário |
| email | VARCHAR | NOT NULL, UNIQUE | Endereço de email do usuário |
| password | VARCHAR | NOT NULL | Senha do usuário (criptografada) |

#### Tabela Músicas

| Coluna | Tipo | Restrições | Descrição |
|--------|------|-----------|-------------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Identificador único da música |
| title | VARCHAR | NOT NULL | Título da faixa |
| artist | VARCHAR | NOT NULL | Nome do artista |
| durationInMinutes | DOUBLE | NOT NULL | Duração da faixa em minutos |

### Características do Banco de Dados

- **Auto-DDL:** Hibernate está configurado com `ddl-auto=update`, que cria/atualiza automaticamente as tabelas
- **Connection Pooling:** Spring Boot configura o pool de conexões automaticamente
- **Gerenciamento de Transações:** Todas as operações de banco são gerenciadas por transação pelo Spring

### Acessando Console H2 (Desenvolvimento)

Quando executado em modo de desenvolvimento, você pode acessar o console H2 em:
```
http://localhost:8081/h2-console
```

**Credenciais Padrão:**
- URL: `jdbc:h2:mem:testdb`
- Usuário: `sa`
- Senha: (vazio)

## Estrutura do Projeto

```
Sonata/
├── src/
│   ├── main/
│   │   ├── java/com/golfettozh/sonata/
│   │   │   ├── controller/
│   │   │   │   ├── UserController.java
│   │   │   │   └── MusicController.java
│   │   │   ├── service/
│   │   │   │   ├── UserService.java
│   │   │   │   └── MusicService.java
│   │   │   ├── model/
│   │   │   │   ├── User.java
│   │   │   │   └── Music.java
│   │   │   ├── repository/
│   │   │   │   ├── UserRepository.java
│   │   │   │   └── MusicRepository.java
│   │   │   ├── dto/
│   │   │   │   ├── request/
│   │   │   │   │   ├── UserRequestDTO.java
│   │   │   │   │   └── MusicRequestDTO.java
│   │   │   │   └── response/
│   │   │   │       ├── UserResponseDTO.java
│   │   │   │       └── MusicResponseDTO.java
│   │   │   ├── exception/
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   └── ResourceNotFoundException.java
│   │   │   └── SonataApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── application-dev.properties
│   └── test/
│       ├── java/com/golfettozh/sonata/
│       │   ├── service/
│       │   │   ├── UserServiceTest.java
│       │   │   └── MusicServiceTest.java
│       │   └── SonataApplicationTests.java
│       └── resources/
│           └── application-test.properties
├── pom.xml
└── README.pt-BR.md
```

## Descrição dos Componentes

### Controladores
- **UserController:** Trata todas as requisições HTTP relacionadas ao gerenciamento de usuários
- **MusicController:** Trata todas as requisições HTTP relacionadas ao gerenciamento do catálogo de músicas

### Serviços
- **UserService:** Lógica de negócio para operações de usuário
- **MusicService:** Lógica de negócio para operações de música

### Repositórios
- **UserRepository:** Camada de acesso a dados da entidade User (estende JpaRepository)
- **MusicRepository:** Camada de acesso a dados da entidade Music (estende JpaRepository)

### DTOs (Objetos de Transferência de Dados)
- **DTOs de Requisição:** Usados para requisições de API recebidas
- **DTOs de Resposta:** Usados para respostas de API saintes

### Tratamento de Exceções
- **GlobalExceptionHandler:** Tratamento centralizado de exceções para toda a aplicação
- **ResourceNotFoundException:** Lançada quando um recurso solicitado não é encontrado

## Tratamento de Erros

A aplicação implementa tratamento global de exceções. Respostas de erro comuns:

### 400 Bad Request
```json
{
  "timestamp": "2024-01-15T10:30:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Parâmetros de requisição inválidos"
}
```

### 404 Not Found
```json
{
  "timestamp": "2024-01-15T10:30:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "Recurso não encontrado"
}
```

### 500 Internal Server Error
```json
{
  "timestamp": "2024-01-15T10:30:00Z",
  "status": 500,
  "error": "Internal Server Error",
  "message": "Ocorreu um erro inesperado"
}
```

## Desenvolvimento

### Construindo o Projeto

```bash
mvn clean install
```

### Executando Testes

```bash
# Executar todos os testes
mvn test

# Executar classe de teste específica
mvn test -Dtest=UserServiceTest

# Executar testes com cobertura
mvn test jacoco:report
```

### Executando com Ferramentas de Desenvolvimento

```bash
# Executar com Spring Boot DevTools para auto-reload
mvn spring-boot:run
```

Alterações em arquivos Java serão automaticamente recompiladas e o aplicativo será reiniciado.

## Resolução de Problemas

### Problemas de Conexão com PostgreSQL

Se você encontrar erros de conexão:

1. Verifique se PostgreSQL está executando:
   ```bash
   # Windows (PowerShell)
   Get-Service | grep postgres
   
   # macOS/Linux
   sudo systemctl status postgresql
   ```

2. Verifique se o banco de dados existe:
   ```bash
   psql -U postgres -l | grep sonata
   ```

3. Verifique as variáveis de ambiente:
   ```bash
   echo $env:USER_DB
   echo $env:PASSWORD_DB
   ```

### Porta Já em Uso

Se a porta 8081 já está em uso:

1. Encontre o processo usando a porta:
   ```bash
   # Windows
   netstat -ano | findstr :8081
   
   # macOS/Linux
   lsof -i :8081
   ```

2. Altere a porta em `application.properties`:
   ```properties
   server.port=8082
   ```

## Contribuindo

1. Faça um Fork do repositório
2. Crie uma branch de funcionalidade (`git checkout -b feature/MinhaFuncionalidade`)
3. Commit suas mudanças (`git commit -m 'Adicionar MinhaFuncionalidade'`)
4. Push para a branch (`git push origin feature/MinhaFuncionalidade`)
5. Abra um Pull Request

## Licença

Este projeto está licenciado sob a Licença MIT - consulte o arquivo LICENSE para detalhes.

## Suporte

Para problemas ou dúvidas:
- Abra uma issue no GitHub
- Entre em contato com os mantenedores

## Versão

**Versão Atual:** 0.0.1-SNAPSHOT

**Última Atualização:** 2024

---

**Construído com ❤️ por [golfettozh](https://github.com/golfettozh)**
