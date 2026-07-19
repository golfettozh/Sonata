# Sonata

Uma API RESTful de gerenciamento de músicas construída com Spring Boot. Sonata fornece uma solução completa de backend para gerenciar usuários e catálogos de música com autenticação baseada em JWT e controle de acesso por função.

## Índice

- [Visão Geral](#visão-geral)
- [Tecnologias](#tecnologias)
- [Requisitos](#requisitos)
- [Instalação](#instalação)
- [Configuração](#configuração)
- [Executando a Aplicação](#executando-a-aplicação)
- [Autenticação](#autenticação)
- [Endpoints da API](#endpoints-da-api)
- [Exemplos de Requisições](#exemplos-de-requisições)
- [Banco de Dados](#banco-de-dados)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Desenvolvimento](#desenvolvimento)

## Visão Geral

Sonata é uma plataforma pronta para produção que oferece gerenciamento de músicas e possibilita:
- Registro de usuários e autenticação baseada em JWT
- Controle de acesso por função (ROLE_USER, ROLE_ARTIST, ROLE_ADMIN)
- Gerenciamento completo de perfil de usuário
- Gerenciamento de catálogo de música com operações CRUD completas
- Endpoints seguros com integração de Spring Security

## Tecnologias

- **Java 21** - Linguagem moderna da JVM com recursos de ponta
- **Spring Boot 4.1.0** - Framework web de nível empresarial
- **Spring Security** - Framework abrangente de autenticação e autorização
- **Spring Data JPA** - Mapeamento objeto-relacional e acesso a dados
- **JWT (JJWT 0.12.6)** - Tokens de autenticação sem estado
- **PostgreSQL** - Banco de dados para produção
- **H2 Database** - Banco de dados em memória para testes
- **Lombok** - Reduz código repetitivo
- **Maven** - Gerenciamento de build e dependências

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

### 2. Configurar PostgreSQL

Certifique-se de que o PostgreSQL está instalado e executando no seu sistema.

#### Windows (Chocolatey)
```powershell
choco install postgresql
```

#### macOS (Homebrew)
```bash
brew install postgresql@15
brew services start postgresql@15
```

#### Linux (Ubuntu/Debian)
```bash
sudo apt-get install postgresql postgresql-contrib
sudo systemctl start postgresql
```

### 3. Criar o Banco de Dados

```bash
psql -U postgres
```

Depois no prompt do PostgreSQL:

```sql
CREATE DATABASE sonata;
```

## Configuração

### Variáveis de Ambiente

Crie um arquivo `.env` na raiz do projeto com as seguintes variáveis:

```env
USER_DB=postgres
PASSWORD_DB=sua_senha
URL_DB=jdbc:postgresql://localhost:5432/sonata
API_SECURITY_TOKEN_SECRET=sua_chave_jwt_secreta_aqui_faça_longa_e_aleatória
```

#### Definindo Variáveis de Ambiente

**Windows (PowerShell):**
```powershell
$env:USER_DB = "postgres"
$env:PASSWORD_DB = "sua_senha"
$env:URL_DB = "jdbc:postgresql://localhost:5432/sonata"
$env:API_SECURITY_TOKEN_SECRET = "sua_chave_jwt_secreta"
```

**macOS/Linux (Bash):**
```bash
export USER_DB=postgres
export PASSWORD_DB=sua_senha
export URL_DB=jdbc:postgresql://localhost:5432/sonata
export API_SECURITY_TOKEN_SECRET=sua_chave_jwt_secreta
```

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

# Configuração de Segurança
application.jwt.secret=change_this_secret_to_a_long_random_value
application.jwt.token.secret=${API_SECURITY_TOKEN_SECRET}

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

## Executando a Aplicação

### Usando Maven

```bash
# Construir a aplicação
mvn clean install

# Executar a aplicação
mvn spring-boot:run

# Executar com perfil específico
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

### Usando Java Diretamente

```bash
# Depois de construir
java -jar target/Sonata-0.0.1-SNAPSHOT.jar
```

### Executando Testes

```bash
# Executar todos os testes
mvn test

# Executar classe de teste específica
mvn test -Dtest=UserServiceTest
```

A aplicação iniciará em `http://localhost:8081`

## Autenticação

Sonata usa JWT (JSON Web Tokens) para autenticação sem estado. Todos os endpoints protegidos requerem um token JWT válido no header `Authorization`.

### Funções de Usuário

- **ROLE_USER** - Usuário padrão com permissões básicas
- **ROLE_ARTIST** - Conta de artista com permissões estendidas
- **ROLE_ADMIN** - Conta administrativa com permissões completas

Usuários podem ter múltiplas funções simultaneamente (ex: ROLE_ADMIN inclui permissões de ROLE_USER).

## Endpoints da API

A API é versionada em `/api/v1`. Todos os endpoints esperam e retornam JSON.

### Endpoints de Autenticação

#### Registrar Usuário

**POST** `/api/v1/auth/register`

Criar uma nova conta de usuário.

**Corpo da Requisição:**
```json
{
  "email": "usuario@example.com",
  "password": "senhaSegura123",
  "role": "ROLE_USER"
}
```

**Resposta:**
```
200 OK
```

**Códigos de Status:**
- `200 OK` - Usuário registrado com sucesso
- `400 BAD REQUEST` - Requisição inválida ou email já em uso

---

#### Fazer Login

**POST** `/api/v1/auth/login`

Autenticar e receber um token JWT.

**Corpo da Requisição:**
```json
{
  "email": "usuario@example.com",
  "password": "senhaSegura123"
}
```

**Resposta:**
```
200 OK
Header: Authorization: Bearer <jwt_token>
```

**Códigos de Status:**
- `200 OK` - Autenticação bem-sucedida
- `401 UNAUTHORIZED` - Credenciais inválidas

---

### Endpoints de Usuários

#### Obter Todos os Usuários

**GET** `/api/v1/users`

Recuperar todos os usuários. Requer autenticação.

**Resposta:**
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440001",
    "email": "usuario1@example.com"
  },
  {
    "id": "550e8400-e29b-41d4-a716-446655440002",
    "email": "usuario2@example.com"
  }
]
```

**Código de Status:** `200 OK`

---

#### Obter Usuário por ID

**GET** `/api/v1/users/{id}`

Recuperar um usuário específico. Requer autenticação.

**Parâmetros de Caminho:**
- `id` (UUID, obrigatório) - ID do usuário

**Resposta:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440001",
  "email": "usuario@example.com"
}
```

**Códigos de Status:**
- `200 OK` - Usuário encontrado
- `404 NOT FOUND` - Usuário não encontrado
- `401 UNAUTHORIZED` - Token inválido ou ausente

---

#### Criar Usuário

**POST** `/api/v1/users`

Criar um novo usuário. Requer ROLE_ADMIN.

**Corpo da Requisição:**
```json
{
  "email": "usuario@example.com",
  "password": "senhaSegura123",
  "role": "ROLE_ARTIST"
}
```

**Resposta:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440001",
  "email": "usuario@example.com"
}
```

**Códigos de Status:**
- `201 CREATED` - Usuário criado com sucesso
- `400 BAD REQUEST` - Erro de validação
- `409 CONFLICT` - Email já existe
- `403 FORBIDDEN` - Permissões insuficientes

---

#### Deletar Usuário

**DELETE** `/api/v1/users/{id}`

Deletar um usuário. Requer ROLE_ADMIN.

**Parâmetros de Caminho:**
- `id` (UUID, obrigatório) - ID do usuário a deletar

**Resposta:**
```
204 NO CONTENT
```

**Códigos de Status:**
- `204 NO CONTENT` - Usuário deletado com sucesso
- `404 NOT FOUND` - Usuário não encontrado
- `403 FORBIDDEN` - Permissões insuficientes

---

### Endpoints de Músicas

#### Obter Todas as Músicas

**GET** `/api/v1/musics`

Recuperar todos os registros de música. Requer autenticação.

**Resposta:**
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440001",
    "title": "Bohemian Rhapsody",
    "artist": "Queen",
    "durationInMinutes": 5.55
  },
  {
    "id": "550e8400-e29b-41d4-a716-446655440002",
    "title": "Imagine",
    "artist": "John Lennon",
    "durationInMinutes": 3.03
  }
]
```

**Código de Status:** `200 OK`

---

#### Obter Música por ID

**GET** `/api/v1/musics/{id}`

Recuperar um registro de música específico. Requer autenticação.

**Parâmetros de Caminho:**
- `id` (UUID, obrigatório) - ID da música

**Resposta:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440001",
  "title": "Bohemian Rhapsody",
  "artist": "Queen",
  "durationInMinutes": 5.55
}
```

**Códigos de Status:**
- `200 OK` - Música encontrada
- `404 NOT FOUND` - Música não encontrada

---

#### Criar Música

**POST** `/api/v1/musics`

Adicionar um novo registro de música. Requer ROLE_ARTIST ou ROLE_ADMIN.

**Corpo da Requisição:**
```json
{
  "title": "Bohemian Rhapsody",
  "artist": "Queen",
  "durationInMinutes": 5.55
}
```

**Resposta:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440001",
  "title": "Bohemian Rhapsody",
  "artist": "Queen",
  "durationInMinutes": 5.55
}
```

**Códigos de Status:**
- `201 CREATED` - Música criada com sucesso
- `400 BAD REQUEST` - Erro de validação
- `403 FORBIDDEN` - Permissões insuficientes

---

#### Deletar Música

**DELETE** `/api/v1/musics/{id}`

Deletar um registro de música. Requer ROLE_ARTIST ou ROLE_ADMIN.

**Parâmetros de Caminho:**
- `id` (UUID, obrigatório) - ID da música a deletar

**Resposta:**
```
204 NO CONTENT
```

**Códigos de Status:**
- `204 NO CONTENT` - Música deletada com sucesso
- `404 NOT FOUND` - Música não encontrada
- `403 FORBIDDEN` - Permissões insuficientes

---

## Exemplos de Requisições

### Usando cURL

#### Registrar Usuário

```bash
curl -X POST http://localhost:8081/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "joao@example.com",
    "password": "senhaSegura123",
    "role": "ROLE_USER"
  }'
```

#### Fazer Login

```bash
curl -X POST http://localhost:8081/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "joao@example.com",
    "password": "senhaSegura123"
  }'
```

#### Obter Todos os Usuários (com token JWT)

```bash
curl -X GET http://localhost:8081/api/v1/users \
  -H "Authorization: Bearer seu_token_jwt_aqui" \
  -H "Accept: application/json"
```

#### Criar Música

```bash
curl -X POST http://localhost:8081/api/v1/musics \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer seu_token_jwt_aqui" \
  -d '{
    "title": "Bohemian Rhapsody",
    "artist": "Queen",
    "durationInMinutes": 5.55
  }'
```

#### Obter Todas as Músicas

```bash
curl -X GET http://localhost:8081/api/v1/musics \
  -H "Authorization: Bearer seu_token_jwt_aqui" \
  -H "Accept: application/json"
```

#### Deletar Música

```bash
curl -X DELETE http://localhost:8081/api/v1/musics/550e8400-e29b-41d4-a716-446655440001 \
  -H "Authorization: Bearer seu_token_jwt_aqui"
```

### Usando Postman

1. Crie uma coleção chamada `Sonata API`
2. Configure variáveis de ambiente:
   - `base_url`: http://localhost:8081/api/v1
   - `token`: (será preenchida após login)

3. Crie as requisições:

**Registrar**
- Método: `POST`
- URL: `{{base_url}}/auth/register`
- Corpo:
  ```json
  {
    "email": "usuario@example.com",
    "password": "senha123",
    "role": "ROLE_USER"
  }
  ```

**Login**
- Método: `POST`
- URL: `{{base_url}}/auth/login`
- Corpo:
  ```json
  {
    "email": "usuario@example.com",
    "password": "senha123"
  }
  ```
- Na seção de testes, adicione:
  ```javascript
  var jsonData = pm.response.json();
  pm.environment.set("token", jsonData.token);
  ```

**Obter Todos os Usuários**
- Método: `GET`
- URL: `{{base_url}}/users`
- Headers: `Authorization: Bearer {{token}}`

**Obter Todas as Músicas**
- Método: `GET`
- URL: `{{base_url}}/musics`
- Headers: `Authorization: Bearer {{token}}`

## Banco de Dados

### Modelos de Banco de Dados

#### Tabela Usuários

| Coluna | Tipo | Restrições | Descrição |
|--------|------|-----------|-------------|
| id | UUID | PRIMARY KEY | Identificador único do usuário |
| email | VARCHAR | NOT NULL, UNIQUE | Endereço de email do usuário |
| password | VARCHAR | NOT NULL | Senha criptografada |
| role | VARCHAR | NOT NULL | Função do usuário (ROLE_USER, ROLE_ARTIST, ROLE_ADMIN) |

#### Tabela Músicas

| Coluna | Tipo | Restrições | Descrição |
|--------|------|-----------|-------------|
| id | UUID | PRIMARY KEY | Identificador único da música |
| title | VARCHAR | NOT NULL | Título da faixa |
| artist | VARCHAR | NOT NULL | Nome do artista |
| durationInMinutes | DOUBLE | NOT NULL | Duração em minutos |

### Características do Banco de Dados

- **Auto-DDL:** Hibernate configurado com `ddl-auto=update`, gerencia automaticamente o schema
- **Connection Pooling:** HikariCP configurado pelo Spring Boot
- **Gerenciamento de Transações:** Todas as operações gerenciadas por transação pelo Spring
- **Geração de UUID:** Geração automática de UUID para todos os IDs

## Estrutura do Projeto

```
Sonata/
├── src/
│   ├── main/
│   │   ├── java/com/golfettozh/sonata/
│   │   │   ├── controller/
│   │   │   │   ├── AuthenticationController.java
│   │   │   │   ├── UserController.java
│   │   │   │   └── MusicController.java
│   │   │   ├── service/
│   │   │   │   ├── UserService.java
│   │   │   │   ├── MusicService.java
│   │   │   │   └── AuthorizationService.java
│   │   │   ├── model/
│   │   │   │   ├── user/
│   │   │   │   │   ├── User.java
│   │   │   │   │   └── UserRole.java
│   │   │   │   └── music/
│   │   │   │       └── Music.java
│   │   │   ├── repository/
│   │   │   │   ├── UserRepository.java
│   │   │   │   └── MusicRepository.java
│   │   │   ├── dto/
│   │   │   │   ├── request/
│   │   │   │   │   ├── AuthenticationRequestDTO.java
│   │   │   │   │   ├── RegisterRequestDTO.java
│   │   │   │   │   ├── UserRequestDTO.java
│   │   │   │   │   └── MusicRequestDTO.java
│   │   │   │   └── response/
│   │   │   │       ├── UserResponseDTO.java
│   │   │   │       └── MusicResponseDTO.java
│   │   │   ├── infra/
│   │   │   │   └── security/
│   │   │   │       └── SecurityConfiguration.java
│   │   │   ├── exception/
│   │   │   │   └── ResourceNotFoundException.java
│   │   │   └── SonataApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-dev.properties
│   │       └── application-test.properties
│   └── test/
│       ├── java/com/golfettozh/sonata/
│       │   └── ...
│       └── resources/
│           └── application-test.properties
├── .env
├── pom.xml
└── README.pt-BR.md
```

### Visão Geral dos Componentes

- **Controllers** - Endpoints REST e roteamento de requisições
- **Services** - Lógica de negócio e operações de domínio
- **Repositories** - Camada de acesso a dados (JPA)
- **DTOs** - Objetos de transferência de dados para contratos de API
- **Models** - Classes de entidade representando objetos de domínio
- **Security** - Configuração de JWT e autenticação
- **Exception Handling** - Tratamento centralizado de erros

## Desenvolvimento

### Construindo o Projeto

```bash
mvn clean install
```

### Executando Testes

```bash
# Todos os testes
mvn test

# Classe de teste específica
mvn test -Dtest=UserServiceTest

# Com relatório de cobertura
mvn test jacoco:report
```

### Ferramentas de Desenvolvimento

O projeto inclui Spring Boot DevTools para recarga automática durante o desenvolvimento.

```bash
mvn spring-boot:run
```

Qualquer alteração em arquivos Java será automaticamente recompilada e a aplicação reiniciada.

## Resolução de Problemas

### Problemas de Conexão com PostgreSQL

**Verifique se PostgreSQL está executando:**
```bash
# Windows
Get-Service | grep postgres

# macOS/Linux
sudo systemctl status postgresql
```

**Verifique se o banco de dados existe:**
```bash
psql -U postgres -l | grep sonata
```

**Teste a conexão:**
```bash
psql -U postgres -d sonata -c "SELECT 1"
```

### Porta Já em Uso

Se a porta 8081 já está em uso:

```bash
# Encontre o processo usando a porta
# Windows
netstat -ano | findstr :8081

# macOS/Linux
lsof -i :8081
```

Altere a porta em `application.properties`:
```properties
server.port=8082
```

### Problemas com Token JWT

- Certifique-se de que a variável de ambiente `API_SECURITY_TOKEN_SECRET` está definida
- Formato do token no header: `Authorization: Bearer <token>`
- Verifique o tempo de expiração do token nos logs

## Contribuindo

1. Faça um Fork do repositório
2. Crie uma branch de funcionalidade (`git checkout -b feature/minha-feature`)
3. Commit suas mudanças (`git commit -m 'Adicionar minha feature'`)
4. Push para a branch (`git push origin feature/minha-feature`)
5. Abra um Pull Request

## Licença

Este projeto está licenciado sob a Licença MIT - consulte o arquivo LICENSE para detalhes.

## Suporte

Para problemas ou dúvidas:
- Abra uma issue no GitHub
- Consulte a documentação existente e exemplos

## Versão

**Versão Atual:** 0.0.1-SNAPSHOT

**Última Atualização:** 2025

---

**Desenvolvido com ❤️ por [golfettozh](https://github.com/golfettozh)**
