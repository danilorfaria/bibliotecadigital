# BibliotecaDigital

# Como executar o projeto

```
Clone ou baixar o projeto do github conforme o endereço de URL abaixo:

https://github.com/xxxxxx/bibliotecadigital

```

## Primeira opção, ultilizando uma IDE:

```
Importar o projeto para Spring Tools for Eclipse ou IDE de preferência.

No STE execultar a classe BibliotecadigitalApplication.java com o Spring Boot App
```


## Segunda opção, pela linha de comando maven:

```
Na pasta raiz do projeto onde consta o comando mvnw, executar o projeto conforme a linha abaixo:

./mvnw spring-boot:run

```

## Terceira opção, criando e testando containers Docker:

```
Criar uma image docker para execultar no conteiner, conforme os passoas abaixo:

Empacotar a aplicação ./mvnw clean package

FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
ADD ./target/bibliotecadigital-0.0.1-SNAPSHOT.jar bibliotecadigital.jar
ENTRYPOINT ["java","-jar","/bibliotecadigital.jar"]

Criar image

docker build -t bibliotecadigital:v1 .

Rodar conteira da image

docker run -p 8080:8080 --name bibliotecadigital bibliotecadigital:v1

```

# Endpoints disponíveis

## Exemplos de requisições

# Testar APIs via Postman, conforme foi disponibilizado

## A variável {{host}}, está configurada como variável de ambiente do Postman em Environments com o nome biblioteca-digital, conforme disponibilizada.

```

Nome da Collection:

BibliotecaDigital


Funcionalidade Categoria:

All categorias: 
GET {{host}}/api/categorias


Livros da categoria: 
GET {{host}}/api/categorias/1/livros


New categoria: 
POST {{host}}/api/categorias

{
    "nome" : "Garden",
    "descricao": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
}


Funcionalidade Autor:

All autor paged
GET {{host}}/api/autores?page=0&linesPerPage=10


Autor by id
GET {{host}}/api/autores/5


New autor
POST {{host}}/api/autores


Update autor
PUT {{host}}/api/autores/4


Delete autor
DEL {{host}}/api/autores/5




Funcionalidade Livro:

Listar todos livros
GET {{host}}/api/livros


Listar todos livros parametros
GET {{host}}/api/livros?categoria=1&ano=2021&autor=2


Buscar por titulo
GET {{host}}/api/livros/search?titulo=java


Livro by id
GET {{host}}/api/livros/1


{{host}}/api/livros/1
POST {{host}}/api/livros

{
  "titulo": "Informação  senior",
  "isbn": "5552111114",
  "anoPublicacao": 2025,
  "preco": 102.0,
  "autorDTO": {
    "id": 5,
    "nome": "Mateus",
    "email": "mateus@gmail.com",
    "dataNascimento": "1921-12-01"
  },
  "categoriaDTO": {
    "id": 4,
    "nome" : "Informática",
    "descricao": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
    }
}


Update Livro
PUT {{host}}/api/livros/8

{
    "titulo": "Segurança da Informação e Tecnologia",
    "isbn": "5552369875",
    "anoPublicacao": 2000,
    "preco": 102.0,
    "autorDTO": {
        "id": 5,
        "nome": "Mateus",
        "email": "mateus@gmail.com",
        "dataNascimento": "1921-12-01"
    },
    "categoriaDTO": {
        "id": 4,
        "nome": "Informática e Tecnologia",
        "descricao": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
    }
}

Delete Livro
DEL {{host}}/api/livros/8



Funcionalidade Web Scraping:

Livro Scraping Amazon
POST {{host}}/api/livros/importar

{
"url": "https://www.amazon.com.br/dp/8535902775",
"autorId": 1,
"categoriaId": 2
}

```



# Testar APIs via navegador

```

http://localhost:8080/api/livros

```


## URL utilizada para scraping 

```

http://localhost:8080/api/livros/importar

{
"url": "https://www.amazon.com.br/dp/8535902775",
"autorId": 1,
"categoriaId": 2
}

```



# URL utilizada para acessar o banco

## Acessar H2 Console

```

http://localhost:8080/h2-console

```
