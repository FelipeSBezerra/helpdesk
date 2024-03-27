# Readme da API do Sistema Simplificado HelpDesk em Spring (Java)

## Acesse a documentação da API no link abaixo:

[Documentação da API do Sistema Simplificado HelpDesk](https://documenter.getpostman.com/view/26431503/2s93zB7325)

#### Observação:

Durante a inicialização da aplicação, uma classe é responsável por popular o banco de dados H2. Para realizar o login e obter um Token JWT, utilize os seguintes dados na requisição de login:

- email: felipe@mail.com
- senha: 123456

Todos os e-mails cadastrados durante a inicialização possuem a mesma senha.

## Descrição

A API do Sistema Simplificado HelpDesk é uma aplicação que permite criar, acessar e gerenciar Ordens de Serviço, permitindo o controle dos chamados de clientes de um negócio de pequeno porte. 

## Finalidade

O objetivo principal deste projeto é explorar e aplicar os conceitos de APIs RESTful, segurança com autenticação e autorização usando Tokens JWT, bem como práticas de desenvolvimento em camadas e outras técnicas relevantes no contexto do desenvolvimento de software moderno.

## Tecnologias Utilizadas

A API foi desenvolvida utilizando as seguintes tecnologias:

- **Java 17**: Linguagem de programação utilizada no projeto.
- **Spring**: Framework para o desenvolvimento rápido de aplicativos Java.

	 Projetos Spring utilizados:
	- **Spring Boot**: Simplifica o processo de configuração, desenvolvimento e implantação de aplicativos bas
	- eados em Spring-Java;
	- **Spring Data JPA**: Facilita o acesso a dados relacionais usando a plataforma Java 	Persistence API (JPA);
	- **Spring Web (MVC)**: Facilita o desenvolvimento de aplicativos web baseados em Java;
	- **Spring Security**: Para gerenciamento de autenticação e autorização;
- **Hibernate**: Framework ORM para mapeamento objeto-relacional.
- **H2**: Banco de dados em memória. (Utilizado H2 pois a aplicação é apenas para fins didáticos)
- **Lombok**: Biblioteca Java utilizada para reduzir a verbosidade do código, automatizando a geração de getters, setters, construtores e outros métodos comuns.
- **Maven**: Ferramenta de automação de compilação e gerenciamento de dependências.

## Estrutura do Projeto

O projeto segue a arquitetura em camadas, dividido em módulos principais:

- **Controller:** Camada responsável por receber as requisições HTTP e chamar os serviços adequados.
- **Service:** Lógica de negócios e regras específicas do domínio.
- **Repository:** Interação com o banco de dados.
- **Domain:** Classes que representam o modelo de domínio.
- **DTO (Data Transfer Object):** Objetos para transferência de dados entre as camadas.
- **Config:** Configurações específicas do Spring.
- **Security:** Classes relacionadas à segurança.
