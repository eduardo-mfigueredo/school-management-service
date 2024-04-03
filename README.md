# School Management Service 

## POC - Desafio back-end

### Instruções para rodar o projeto localmente

#### Pré-requisitos

MongoDB 

```
docker run -d -p 27017:27017 --name meu-mongodb mongo
```

Java 11

#### O projeto subirá na porta 8081.

Para acessar o swagger, com a documentação da API, acesse:

###  http://localhost:8081/swagger-ui.html

Os endpoints podem ser testados pelo próprio swagger.

Ao rodar o projeto pela primeira vez, as collections de _students, activities e grades_ serão populadas.

Também serão criados 2 usuários, um ADMIN e um MANAGER, com as seguintes credenciais:


```
{
    "username": "admin@admin.com",
    "password": "admin"
}

{
    "username": "manager@manager.com",
    "password": "manager"
}
```

Para logar, utilizar a rota Rota /api/v1/auth/signin passando no body um dos payloads acima.

O token tem duração de 1 hora.

Permissionamento:

O admin tem permissão para acessar todos os endpoints (_students, activities, grades_).

O manager tem permissão para:
    Acessar todos os endpoints de _students_.
    Fazer GET ou POST nos endpoints de _grade_(notas).
    Fazer GET nos endpoints de _activities_.
