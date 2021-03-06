# Wishlist Api

Este repósitorio contém uma Api para gerenciar lista de desejos.

* [Estrutura do Projeto](#estrutura-do-projeto)
* [Requisições disponiveis](#requisies-disponiveis)
* [Executando o Projeto](#executando-o-projeto)

## Estrutura do Projeto

### Representaçao da lista de desejos:

Uma lista de desejo terá um identificador próprio, permitindo a busca e gestão através do id
específico da lista, o identificador do cliente que a possui, e a lista de produtos que está
organizada em um HashMap no qual a key representa o ID do produto e o valor da quantidade do mesmo.

````json
{
  "_id": "61d20c48d7d5c821c6776b3f",
  "clientId": "12",
  "products": {
    "product-key": 1
  },
  "createdAt": "2022-01-02T17:34:16.785-03:00",
  "updatedAt": "2022-01-02T17:34:17.128-03:00"
}
````

### Regras sobre a lista de desejos:

* Não há necessidade de se criar uma lista de desejos, ao adicionar o produto para um clientId, 
caso ele não tenha uma lista de desejo ela srá criada. 
* Um cliente pode ter apenas uma lista de desejo.
* A quantidade máxima permitida em uma lista de desejo é de 20 itens.
* Desde que respeite o limite definido, o cliente pode adicionar e remover produtos sobre a lista de
  desejos
* Para estar na lista de produtos a quantidade do item deve ser maior que 0.

## Requisições Disponiveis

- [GET /wish-list/[id]](#get-by-id)
- [GET /wish-list/client/[clientId]](#get-by-client-id)
- [Get /wish-list/client/[clientId]/contains-product/[productKey]](#get-exists-product-in-wishlist)
- [POST /wish-list/add-products/](#post-add-products)
- [POST /wish-list/remove-products/](#post-remove-products)

### GET By ID

Description: Given an objectId representing the Wishlist object, returns the wishlist information.

#### URL: 
* /wish-list/[id]

#### URL Params:
* Id = String, example = "61d20ae070539b48e808dcc0"

#### Response:
##### Sucess:
###### Code: 200 - Wishlist information
````json
{
  "clientId": "61d20ae070539b48e808dcc0",
  "createdAt": "2022-01-02T20:34:16.785Z",
  "id": 12,
  "products": {
    "product-key": 1
  },
  "updatedAt": "2022-01-02T20:34:16.785Z"
}
````
##### Error:
###### Code 400
````json
{
  "timestamp": "2022-01-02T21:20:40.485+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid id, must be a non empty string"
}
````

###### Code 404
````json
{
  "timestamp": "2022-01-02T21:21:43.636+00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Not found: no wish list for this id"
}
````

### GET By Client ID

Description: Given an objectId representing the Wishlist object, returns the wishlist information.

#### URL:
* /wish-list/[clientId]

#### URL Params:
* clientId = String, example = "12"

#### Response:

##### Sucess:
###### Code: 200 - Wishlist information
````json
{
  "clientId": "61d20ae070539b48e808dcc0",
  "createdAt": "2022-01-02T20:34:16.785Z",
  "id": 12,
  "products": {
    "product-key": 1
  },
  "updatedAt": "2022-01-02T20:34:16.785Z"
}
````
##### Error:
###### Code 400 - Bad Request
````json
{
  "timestamp": "2022-01-02T21:20:40.485+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid client Id, must be a non empty string",
}
````
###### Code 404 - Not Found
````json
{
  "timestamp": "2022-01-02T21:21:43.636+00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Not found: no wish list for this clientId",
}
````
###### Code 409 - Conflict
````json
{
  "timestamp": "2022-01-02T21:21:43.636+00:00",
  "status": 409,
  "error": "Conflict",
  "message": "There is more than one wish list associated with this client",
}
````

### GET exists product in wishlist
Description: Given an objectId representing the Wishlist object, returns the wishlist information.

#### URL:
* /wish-list/client/[clientId]/contains-product/[productKey]

#### URL Params:
* clientId = String, example  = "12"
* productKey = String, example = "product-key"

#### Response:
##### Sucess:
###### Code: 200
````json
true
````
##### Error:
###### Code 400 - Bad Request
````json
{
"timestamp": "2022-01-02T21:20:40.485+00:00",
"status": 400,
"error": "Bad Request",
"message": "Invalid clientId or productKey, must be a non empty string"
}
````
###### Code 404 - Not Found
````json
{
  "timestamp": "2022-01-02T21:21:43.636+00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Not found: no wish list for this id"
}
````
###### Code 409 - Conflict
````json
{
  "timestamp": "2022-01-02T21:21:43.636+00:00",
  "status": 409,
  "error": "Conflict",
  "message": "There is more than one wish list associated with this client",
}
````
### POST add products
Description: Adds products to the client's wish list, if it doesn't exist, a new one is created.
#### URL:
* /wish-list/add-products/
#### Data Params:
````json
{
  "clientId": 12,
  "productsToBeUpdated": {
    "product-key": 1
  }
}
````

#### Response:
##### Sucess:
###### Code: 200  - Products on customer's wish list after insert
````json
{
  "products": {
    "product-key": 1
  }
}
````
##### Error:
###### Code 400 - Bad Request
````json
{
"timestamp": "2022-01-02T21:20:40.485+00:00",
"status": 400,
"error": "Bad Request",
"message": "Invalid product list, has a blank product key or negative quantity",
}
````
###### Code 409 - Conflict
````json
{
  "timestamp": "2022-01-02T21:21:43.636+00:00",
  "status": 409,
  "error": "Conflict",
  "message": "There is more than one wish list associated with this client",
}
````

### POST Remove Products

Description: Remove products from customer's wish list.

#### URL:
* /wish-list/remove-products/

#### Data Params:
````json
{
  "clientId": 12,
  "productsToBeUpdated": {
    "product-key": 1
  }
}
````
#### Response:
##### Sucess:
###### Code: 200  - Products on customer's wish list after removal
````json
{
  "products": {
    "product-key-2": 1
  }
}
````
##### Error:
###### Code 400 - Bad Request
````json
{
"timestamp": "2022-01-02T21:20:40.485+00:00",
"status": 400,
"error": "Bad Request",
"message": "Invalid product list, has a blank product key or negative quantity",
}
````
###### Code 409 - Conflict
````json
{
  "timestamp": "2022-01-02T21:21:43.636+00:00",
  "status": 409,
  "error": "Conflict",
  "message": "There is more than one wish list associated with this client",
}
````
## Executando o Projeto:

Este projeto contém um Docker compose, então a partir da execução de um [Docker](https://www.docker.com/products/docker-desktop)
e possivel executar tanto a aplicação quanto o bando de dados não relacional utilizado: MongoDB

Primeiramente executa-se na raiz do projeto o comando
`mvn clean package`

Após a finalização do build e a geração do arquivo *.jar* na pasta *target* basta executar:

```
docker-compose up -d --build
````
Assim que a aplicação estiver a rodar, será possivel acessar a sua documentação através do Swagger:

```
http://localhost:9095/swagger-ui/index.html#
````

![swagger](./readme-images/swagger.jpg)

Para encerrar a execuçao basta roda: 
```
docker-compose down
````

## Dependencias do Projeto:

Esta Aplicação utiliza-se do:
* Framework: SpringBoot
* Linguagem: Java 11
* Banco de dados não relacional: MongoDB
* Gerenciador de dependencia: Maven
* Deploy: Docker
* Documentação: Swagger
