# 🗂️ Task API — Gerenciamento de Tarefas com Cache Redis

> API REST para gerenciamento de tarefas com performance otimizada usando Redis como camada de cache.

---

## 📋 Sobre

Esse projeto nasceu de uma pergunta simples: o que acontece com a performance de um sistema quando muitos usuários acessam os mesmos dados ao mesmo tempo?

A resposta foi construir uma API que resolve esse problema na prática. Toda vez que alguém lista as tarefas, o sistema guarda o resultado no Redis. Na próxima consulta, em vez de ir ao banco de dados, ele responde direto da memória — muito mais rápido.

Desenvolvido do zero com foco em entender como o cache funciona na prática e por que ele é tão importante em sistemas reais.

---

## ⚙️ Tecnologias

| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 21 | Linguagem principal |
| Spring Boot | 3.5 | Framework base |
| Spring Cache | 3.5 | Abstração de cache |
| Redis | 7 | Armazenamento em memória |
| Spring Data JPA | 3.5 | Persistência de dados |
| Hibernate | 6.6 | ORM |
| MySQL | 8.0 | Banco de dados |
| Docker | - | Ambiente isolado |
| Lombok | 1.18 | Redução de boilerplate |

---

## 🏗️ Arquitetura

```
src/main/java/com/douglas/api_cach_redis/
├── config/
│   └── SecurityConfig.java         # Configuração de segurança
├── controller/
│   └── TaskController.java         # Endpoints da API
├── entity/
│   └── Task.java                   # Entidade de tarefa
├── repository/
│   └── TaskRepository.java         # Acesso ao banco de dados
└── service/
    └── TaskService.java            # Regras de negócio + cache
```

---

## ⚡ Como o cache funciona

```
PRIMEIRA CHAMADA
Cliente ──── GET /tasks/listarTask ────► Service
Service ──── busca no banco ────────────► MySQL
Service ──── salva no Redis ────────────► Redis
Cliente ◄─── retorna lista ─────────────────────

SEGUNDA CHAMADA
Cliente ──── GET /tasks/listarTask ────► Service
Service ──── busca no Redis ────────────► Redis (sem ir ao banco!)
Cliente ◄─── retorna lista ─────────────────────

CRIAR/ATUALIZAR/DELETAR
Cliente ──── POST/PUT/DELETE ──────────► Service
Service ──── atualiza o banco ──────────► MySQL
Service ──── limpa o cache ─────────────► Redis (força nova consulta)
```

---

## 🔒 Anotações de cache utilizadas

- `@Cacheable("tasks")` — guarda o resultado no Redis na primeira consulta
- `@CacheEvict(value = "tasks", allEntries = true)` — limpa o cache quando os dados mudam

---

## 🚀 Como rodar localmente

### Pré-requisitos

- [Java 21+](https://adoptium.net)
- [Docker Desktop](https://www.docker.com/products/docker-desktop)
- [Postman](https://www.postman.com/downloads)

### Passo a passo

```bash
# 1. Clone o repositório
git clone https://github.com/SantoBug/task-api.git
cd task-api

# 2. Suba o banco e o Redis
docker compose up -d

# 3. Rode a aplicação
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8081`

---

## 📡 Endpoints

### Criar tarefa

```http
POST /tasks/criarTask
Content-Type: application/json

{
  "titulo": "Estudar Spring Boot",
  "status": "Pendente",
  "prazo": "2026-06-30"
}
```

### Listar todas as tarefas

```http
GET /tasks/listarTask
```

### Atualizar tarefa

```http
PUT /tasks/atualizarTask
Content-Type: application/json

{
  "id": 1,
  "titulo": "Estudar Spring Boot",
  "status": "Concluído",
  "prazo": "2026-06-30"
}
```

### Deletar tarefa

```http
DELETE /tasks/deletarTask
Content-Type: application/json

{
  "id": 1
}
```

---

## 🧱 Dificuldades que enfrentei

Esse projeto foi construído com muita tentativa e erro — e cada erro me ensinou algo importante.

**Docker travando no Windows**
O Docker Desktop ficava preso no "Starting" sem avançar. Resolvi com `wsl --shutdown` no PowerShell como administrador, que reinicia o WSL sem precisar reiniciar o computador inteiro.

**Porta já em uso**
Ao rodar o projeto duas vezes sem parar a instância anterior, aparecia o erro `Port 8081 was already in use`. Aprendi a sempre parar o processo anterior antes de rodar de novo.

**Import errado do @Cacheable**
O Java tem dois `@Cacheable` — um do JPA (`jakarta.persistence`) e um do Spring Cache (`org.springframework.cache.annotation`). O IntelliJ importou o errado automaticamente e o cache não funcionava. Aprendi a sempre verificar os imports.

**Cache desatualizado após criar tarefa**
Após implementar o `@Cacheable`, percebi que criar uma nova tarefa não atualizava a lista — o Redis ainda retornava os dados antigos. A solução foi adicionar `@CacheEvict` em todas as operações que modificam dados.

**Senha do banco errada**
O `docker-compose.yml` tinha `MYSQL_ROOT_PASSWORD: verysecret` mas o `application.yml` estava com `password: root`. O erro `Access denied` aparecia e só foi resolvido ao alinhar as senhas.

---

## 📁 Variáveis de ambiente

| Variável | Descrição | Valor |
|---|---|---|
| `spring.datasource.url` | URL do banco | localhost:3307/taskdb |
| `spring.datasource.password` | Senha do banco | verysecret |
| `spring.data.redis.port` | Porta do Redis | 6379 |
| `spring.data.redis.password` | Senha do Redis | redispass |

---

## 👨‍💻 Autor

**Douglas Tavares**
Estudante de Engenharia de Software focado em desenvolvimento backend Java.

[![GitHub](https://img.shields.io/badge/GitHub-black?style=flat&logo=github)](https://github.com/SantoBug)
