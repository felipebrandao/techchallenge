# Projeto: Tech Challenge - APIs de Cadastro e Gestão de Dados de Consumo de Energia

## Descrição do Projeto
Este projeto faz parte do Tech Challenge e tem como objetivo desenvolver APIs para cadastro e gestão de dados de consumo de energia. As APIs são responsáveis por cadastrar endereços, pessoas e eletrodomésticos, fornecendo os dados necessários para o cálculo do consumo mensal de energia.

## Funcionalidades
- API de Gestão de Endereços
- API de Gestão de Pessoas
- API de Gestão de Eletrodomésticos
- API de Gestão de Moradores
- API de Gestão de Consumo

## Tecnologias Utilizadas
- **Linguagem:** Java 17
- **Framework/API:** 
  - [Spring Boot](https://spring.io/projects/spring-boot)
  - [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
  - [Spring Web](https://spring.io/projects/spring-web)
  - [Spring Feign](https://spring.io/projects/spring-cloud-openfeign)
  - [Lombok](https://projectlombok.org/)
  - [MapStruct](https://mapstruct.org/)
  - [Postman](https://www.postman.com/)
  - [Log4j2](https://logging.apache.org/log4j/2.x/)
- **Banco de Dados:** [Postgresql](https://www.postgresql.org/)
- **Ferramentas de desenvolvimento:**
  - **IDE:** [IntelliJ IDEA](https://www.jetbrains.com/idea/)
  - **Controle de Versão:** [Git](https://git-scm.com/)
  - **Plataforma de Hospedagem:** [GitHub](https://github.com/)
  - **Documentação:** [Swagger](https://swagger.io/)
  - **Gerenciador de Dependências:** [Maven](https://maven.apache.org/)
  - **Containerização:** [Docker](https://www.docker.com/)

## Documentação da API:

Cada microsserviço possui um Swagger integrado para facilitar o acesso à documentação gerada.

## Como Executar o Projeto
1. Clone este repositório.
2. Instale as dependências necessárias.
3. Configure o ambiente de desenvolvimento.
4. Execute o servidor local.
5. Acesse as APIs através do endpoint correspondente.


## Executando o Projeto Localmente

Para executar o projeto localmente usando Docker Compose, siga estas etapas:

1. Clone este repositório em sua máquina local:
    ```
    git clone https://github.com/felipebrandao/techchallenge.git
    ```

2. Navegue até o diretório do projeto:

3. Certifique-se de ter o Docker e o Docker Compose instalados em sua máquina. Se não tiver, siga as instruções de instalação [aqui](https://docs.docker.com/get-docker/).

4. Execute o seguinte comando para iniciar todos os serviços necessários, incluindo o banco de dados, usando Docker Compose:
    ```
    docker-compose up
    ```

5. Após iniciar todos os serviços, você pode acessar a documentação da API e começar a interagir com os endpoints.


## Licença
Este projeto está licenciado sob a [Licença MIT](LICENSE).
