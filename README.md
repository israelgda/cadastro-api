# API de cadastro de dados de usuário
API de cadastro e consulta de dados de usuário fornecendo endpoints de CRUD completo. Para realizar a consulta de endereço o projeto está consumindo uma API externa ViaCEP e trazendo os dados de Bairro, Cidade e UF do usuário através do CEP informado.

## Sobre o projeto

### Objetivo
Este projeto foi criado com o intuito de praticar a criação de APIs Rest utilizando Java e Spring com boas práticas, qualidade de desenvolvimento e testes unitários.

### Integração
O projeto tem integração com a API ViaCEP para realizar a consulta do endereço de cadastro do usuário. A realização da consulta é feita por número do CEP. Documentação disponível no link abaixo:
```
https://viacep.com.br/
```
## Funcionalidades
### - Endpoint de cadastro -
```
POST  /v1/usuarios
```
* O cadastro é realizado através do método POST no endpoint indicado acima. Para realizar o cadastro, é necessário enviar os seguintes dados no corpo da requisição, em formato JSON:
```
1. nome : String
2. datanascimento : String (formato dd/MM/YYYY)
3. cep : String (formato de 8 digitos. Exemplo: 57046340)
4. cpf : String (apenas números)
5. telefone: String

Exemplo:
{
    "nome": "Marcos Marcelinho de Marques",
    "dataNascimento": "25/08/1989",
    "cep": "12023251",
    "cpf": "11122233345",
    "telefone": "82982250848"
}
```

### - Endpoint de consulta - 
```
GET  /v1/usuarios/{cpf}
```
* A consulta dos dados do usuário é realizada através do método GET no endpoint indicado acima passando o número do cpf (apenas números) como parâmetro na requisição. Caso o usuário exista, serão exibidos os dados no corpo da response no formato exemplo:
```
{
        "nome": "José Josenildo Santos",
        "idade": 27,
        "cidade": "Maceió",
        "bairro": "Serraria",
        "estado": "Alagoas",
        "cpf": "25548549",
        "telefone": "82984458965"
}
```
* Caso o cpf pesquisado não exista no registro, retornará uma resposta tratada Not Found.

### - Endpoint de update -
```
PUT  /v1/usuarios/{id}
```
* A alteração dos dados do usuário poderá ser realizada através do método PUT, passando o ID do registro por variável na URL e os dados no corpo da request no formato exemplo:
```
{
        "nome": "Nome Alterado",
        "idade": 27,
        "cidade": "Maceió",
        "bairro": "Serraria",
        "estado": "Alagoas",
        "cpf": "25548549",
        "telefone": "82984458965"
}
```
* Caso o id informado não exista no registro, retornará uma resposta tratada Not Found.

### - Endpoint de delete -
```
DELETE  /v1/usuarios/{id}
```
* O id do registro a ser apagado deverá ser passado por variável na URL através do método DELETE.
* Caso o id informado não exista no registro, retornará uma resposta tratada Not Found.

## Acesso a API em deploy no Heroku para testes
* A API pode ser testada no endereço abaixo:
```
Ainda será feito deploy
```

## Configurando o projeto para executar localmente ou com banco em nuvem
* Crie o banco PostgreSQL local com nome cadastro_api
* Para fazer a conexão local basta indicar o perfil "test" como ativo, no arquivo application.properties, como está indicada abaixo:
```
spring.profiles.active=test
```
* Caso deseje rodar no banco Cloud provisionado no Heroku, para facilitar a execução do projeto, basta indicar o perfil "prod" como ativo, assim como está indicado abaixo:
```
spring.profiles.active=prod
```
## Tecnologias utilizadas
#### - Frameworks e Banco- 
* Desenvolvido com Java 11 e Spring 2.6.1;
* Banco PostgreSQL;

#### - Linguagens - 
* JAVA

