# TODOApp

## How To Run TodoApp Project?

1- Firstly, you must have `Docker` application.\
2- You should run following command after pulling the project in your local.
```shell 
docker-compose up -d
``` 
3- You can access from following link the swagger of TodoApp project.
> http://localhost:8081/api/swagger-ui/index.html

![image](https://user-images.githubusercontent.com/55146789/188334201-1c919cd5-2e1d-4d33-b46b-744bb7cc7f6e.png)

4- You can access from following link the Couchbase UI of TodoApp project.
> http://localhost:8091/ui/index.html#!/overview

![image](https://user-images.githubusercontent.com/55146789/188334188-de336cf9-544a-4f87-8517-f87a7de62a85.png)

4- You can use following informations for the Couchbase.
```shell 
username = root
password = password
```

5- Follow the steps below to use the TodoApp Project.
- Firstly, You must create a user with /user/signup path.
- Secondly, You must login using /auth/login path with created user.
- You must authorize with the token returned from /auth/login request.

![image](https://user-images.githubusercontent.com/55146789/188334713-c96255f6-d4d7-43b9-a958-4e817ebaa453.png)

```shell 
Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJST0xFIjoiQURNSU4iLCJzdWIiOiJlc3JhIiwiaXNzIjoidXNyIiwiZXhwIjoxNjYy
```

## Used Dependencies
- [Java 8]
- [Spring Boot]
- [Spring Data Couchbase]
- [Couchbase]
- [Swagger]
- [Maven]
- [Docker]
- [JUnit and Mockito]
- [JWT]
- [Spring Boot Security]
- [Mapstruct]
- [Lombok]
