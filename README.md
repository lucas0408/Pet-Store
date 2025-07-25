# Pet Store
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/lucas0408/Pet-Store/blob/main/LICENSE) 

# Sobre o projeto

https://pet-shop-front-end-nu51.vercel.app/

Pet Store é uma aplicação web full stack desenvolvida para facilitar o cadastro e gerenciamento de produtos e funcionários em pet shops. A plataforma permite que os administradores cadastrem novos produtos, organizem o estoque e gerenciem os funcionários de forma eficiente.

## Layout web
![web 1](https://github.com/user-attachments/assets/a265b082-f884-4747-a24c-a8e9249aa57a)

![web 2](https://github.com/user-attachments/assets/4f0166ab-4f89-4296-b466-ee0a37ec5cef)

![web 3](https://github.com/user-attachments/assets/5979ea22-6ed1-4ed3-8938-62dd9d25c1d1)

## Modelo conceitual
<img src="https://github.com/user-attachments/assets/71e564cd-0543-4d72-8ca5-dc85b44969bd" width="350">

# Tecnologias utilizadas
## Back end
- Java
- Spring Boot
- JPA / Hibernate
- Maven
- Docker
- JWT
## Front end
- HTML / CSS / JS / TypeScript
- Angular
- Node.js
## Implantação em produção
- Back end: railway
- Front end web: Vercel
- Banco de dados: Postgresql

## Testes

O projeto possui testes automatizados para garantir a qualidade e confiabilidade do código.

### Back-end
- **Testes unitários**: Testam as regras de negócio e lógica das classes de serviço utilizando mocks
- **Testes de integração**: Validam os endpoints da API e a integração entre as camadas usando MockMvc

#### Executar os testes

```bash
# Executar todos os testes
./mvnw test

# Executar apenas testes unitários
./mvnw test -Dtest="**/unit/**/*"

# Executar apenas testes de integração
./mvnw test -Dtest="**/integration/**/*"

# Executar teste específico
./mvnw test -Dtest="CategoryServiceUnitTest"
```

#### Principais cenários testados
- Cadastro e validação de produtos
- Gerenciamento de funcionários
- Validação de dados de entrada
- Tratamento de exceções e respostas HTTP
- Endpoints da API REST

#### Ferramentas utilizadas
- **JUnit 5**: Framework de testes unitários e de integração
- **Mockito**: Mock de dependências e simulação de comportamentos
- **MockMvc**: Testes de integração dos controllers e endpoints da API
- **Spring Boot Test**: Configuração do contexto de testes


# Como executar o projeto

## Back end
Pré-requisitos: Java 17

```bash
# Clonar o repositório
git clone https://github.com/lucas0408/Pet-Store

# Entrar na pasta do projeto back-end
cd Pet-Store

# Compilar e executar o projeto
./mvnw spring-boot:run
```

## Front end web
Pré-requisitos: Node.js 18.x / Angular CLI 17

```bash
# Clonar o repositório
git clone https://github.com/lucas0408/Pet-Store_front-end

# Entrar na pasta do projeto
cd Pet-Shop_front-end

# Instalar as dependências
npm install

# Executar o projeto
ng serve
```

# Autor

Lucas Gabriel Navas

https://www.linkedin.com/in/lucas-gabriel-navas-sabino-150640250
