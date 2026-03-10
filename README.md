# Sistema de Requisição de Acessos (Java + PostgreSQL)

## Descrição

Este projeto é uma aplicação simples em **Java** que simula um **sistema de requisição de acessos**, inspirado em conceitos básicos de **IAM (Identity and Access Management)**.

A aplicação roda no **console (CMD)** e permite:

- Login de usuário
- Criação de novos usuários
- Solicitação de permissões
- Aprovação de permissões (apenas administradores)
- Exclusão de usuários
- Listagem de usuários, roles e solicitações

O sistema utiliza **JDBC para conexão com banco PostgreSQL** e foi desenvolvido aplicando **orientação a objetos e separação de responsabilidades com DAO (Data Access Object)**.

---

# Arquitetura do Projeto

O sistema foi estruturado separando responsabilidades em diferentes classes.

Console (CMD)
↓
Main.java
↓
DAO Classes
(UserDAO, RoleDAO, AccessRequestDAO)
↓
DatabaseJava.java
↓
PostgreSQL

### Responsabilidade das classes

| Classe | Responsabilidade |
|------|------|
| Main | Controla o menu do sistema e fluxo da aplicação |
| DatabaseJava | Realiza a conexão com o banco PostgreSQL |
| UserDAO | Gerencia operações relacionadas aos usuários |
| RoleDAO | Gerencia operações de roles/permissões |
| AccessRequestDAO | Gerencia solicitações de acesso |

---

# Modelo Relacional

O banco de dados possui **3 tabelas principais**.

## users

Armazena os usuários do sistema.

| Campo | Tipo |
|------|------|
| id | SERIAL (PK) |
| username | VARCHAR |
| password | VARCHAR |

---

## roles

Armazena os tipos de permissões.

| Campo | Tipo |
|------|------|
| id | SERIAL (PK) |
| role_name | VARCHAR |

Exemplos:

ADMIN  
USER  
GERENTE  

---

## access_requests

Armazena as solicitações de acesso feitas pelos usuários.

| Campo | Tipo |
|------|------|
| id | SERIAL (PK) |
| user_id | INTEGER (FK) |
| role_id | INTEGER (FK) |
| status | VARCHAR |

Status possíveis:

PENDING  
APPROVED  

---

# Relacionamento entre tabelas

users  
↑  
user_id  
access_requests  
role_id  
↓  
roles  

A tabela **access_requests** conecta usuários às permissões solicitadas.

---

# Regras de Negócio

O sistema implementa algumas regras simples:

- O usuário deve realizar **login para acessar o sistema**
- Existe um usuário padrão:

admin / admin

- Usuários podem **solicitar permissões**
- Apenas usuários com role **ADMIN** podem **aprovar solicitações**
- Usuários podem ser **excluídos do sistema**

---

# Tecnologias Utilizadas

- Java
- JDBC
- PostgreSQL
- SQL
- Orientação a Objetos
- DAO Pattern
- Console Application

---

# Conexão com Banco

A conexão com o banco é realizada utilizando **JDBC** através da classe `DatabaseJava`.

Exemplo:

```java
Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
