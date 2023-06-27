## Projeto: Tech Challenge

Ferramentas Utilizadas:

- IDE: Intellij
- Framework: Spring Boot
- Linguagem: Java 17
- Controle de versão: Git
- Plataforma de hospedagem: GitHub

## Desafios Encontrados:
Nesta primeira etapa do projeto Tech Challenge, não encontramos nenhuma dificuldade, pois já estamos familiarizados com os conceitos apresentados neste primeiro módulo.

## Documentação da API: EletrodomesticoController
Este endpoint é usado para cadastrar um novo eletrodoméstico.

A URL base para acessar os endpoints desta API é: http://localhost:8080/eletrodomestico

#### Parâmetros da Solicitação

O corpo da solicitação deve ser um objeto JSON contendo as informações do eletrodoméstico a ser cadastrado. O formato esperado para o objeto EletrodomesticoDTO é o seguinte:
* `nome` (obrigatório): O nome do eletrodoméstico.
* `modelo` (obrigatório): O modelo do eletrodoméstico.
* `potencia` (obrigatório): A potencia do eletrodoméstico

#### Exemplo de Solicitação
POST /eletrodomestico <br>
Host: localhost:8080 <br>
Content-Type: application/json <br>

<pre>
{
  "nome": "Geladeira",
  "modelo": "ABC123",
  "potencia": 110
}
</pre>

####  Respostas

Status: 201 Created

Exemplo de resposta bem-sucedida:

<pre>
{
  "id": 1,
  "nome": "Geladeira",
  "modelo": "ABC123",
  "potencia": 110
}
</pre>

## EnderecoController
A classe EnderecoController é responsável pelo cadastro de endereços.

A URL base para acessar os endpoints desta API é: http://localhost:8080/endereco

#### Parâmetros da Solicitação

O corpo da solicitação deve ser um objeto JSON contendo as informações do endereço a ser cadastrado. O formato esperado para o objeto EnderecoDTO é o seguinte:
* `rua` (obrigatório): A rua do endereço.
* `numero` (obrigatório): O número do endereço.
* `bairro` (obrigatório): O bairro do endereço.
* `cidade` (obrigatório): A cidade do endereço.
* `estado` (obrigatório): O estado do endereço.

#### Exemplo de Solicitação

POST /endereco <br>
Host: localhost:8080 <br>
Content-Type: application/json <br>

<pre>
{
    "rua": "Carlos Alberto",
    "numero": 400,
    "bairro": "Bela vista",
    "cidade": "Osasco",
    "estado": "SP"
}
</pre>

####  Respostas

Status: 201 Created

Exemplo de resposta bem-sucedida:

<pre>
{
    "id": 1,
    "rua": "Carlos Alberto",
    "numero": 400,
    "bairro": "Bela vista",
    "cidade": "Osasco",
    "estado": "SP"
}
</pre>

## PessoaController
Este endpoint é usado para cadastrar uma nova pessoa.

A URL base para acessar os endpoints desta API é: http://localhost:8080/pessoa

#### Parâmetros da Solicitação

O corpo da solicitação deve ser um objeto JSON contendo as informações da pessoa a ser cadastrada. O formato esperado para o objeto PessoaDTO é o seguinte:
* `nome` (obrigatório): O nome da pessoa.
* `dataDeNascimento` (obrigatório): A data de nascimento da pessoa no formato "dd/MM/yyyy".
* `sexo` (obrigatório): O sexo da pessoa. Os valores válidos são "MASCULINO" ou "FEMININO".
* `cpf` (obrigatório): O CPF (Cadastro de Pessoas Físicas) da pessoa.

#### Exemplo de Solicitação

POST /pessoa <br>
Host: localhost:8080 <br>
Content-Type: application/json <br>

<pre>
{
  "nome": "Thalita Ribeiro",
  "dataDeNascimento": "14/06/2000",
  "sexo": "FEMININO",
  "cpf": "03103079028"
}
</pre>

####  Respostas

Status: 201 Created

Exemplo de resposta bem-sucedida:

<pre>
{
  "id": 1,
  "nome": "Thalita Ribeiro",
  "dataDeNascimento": "14/06/2000",
  "sexo": "FEMININO",
  "cpf": "03103079028"
}
</pre>
