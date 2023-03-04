# Spring Security JWT Nisum Evaluacion | Lucas Pirito

Buenas! 👋 Decidi implementar mi solucion utilizando Spring Security desde 0.

Esta aplicacion contiene:

- Validacion de campos de la request
- Manejo de excepciones customizados
- RESTful API
- Registro y Login mediante usuario
- Authenticacion mediante JWT
- H2 (Base de datos en memoria)

## Setup

1. Clonamos el repositorio en alguna carpeta, por ejemplo nuestro escritorio:

```git
git clone https://github.com/lucaspiritogit/nisum_eval.git
```

2. Entramos a la carpeta del repositorio clonado o abrimos una terminal en la carpeta del mismo:

```bash
cd Desktop/nisum_eval
```

3. Corremos el siguiente comando para instalar las dependencias y levantar el programa:

```bash
mvn clean install && mvn spring-boot:run
```

## API Endpoints

### Register **POST**

**Requiere de JWT en Header?**: No 🔓

Endpoint: http://localhost:8080/api/v1/auth/register

Ejemplo de body:

```json
{
  "name": "lucas",
  "email": "lucas@dominio.cl",
  "password": "1234",
  "phones": [
    {
      "number": "1234567",
      "cityCode": "1",
      "countryCode": "57"
    }
  ]
}
```

Ejemplo de Response 200 ✅:

```json
{
  "id": "1df663d8-6a86-4c6f-9fdd-2a24eaf3b68c",
  "name": "lucas",
  "password": "$2a$10$vBv1YnNSn3p1Ng3k2fIKbOma6nwwmUtzoK.mmLknkBIOrCXFFUS72",
  "email": "lucas@dominio.cl",
  "phones": [
    {
      "number": "1234567",
      "cityCode": "1",
      "countryCode": "57"
    }
  ],
  "created": "2023-03-04T17:15:47.4554083",
  "modified": null,
  "lastLogin": "2023-03-04T17:15:47.4554083",
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsdWNhc0Bkb21pbmlvLmNsIiwiZXhwIjoxNjc3OTg0OTQ3LCJpYXQiOjE2Nzc5NjA5NDd9.bd5ZW8LVNbQKBf3uIDRfuXstsGeymLmNGdtvgTXDxV0",
  "active": true
}
```

### Login **POST**

**Requiere de JWT en Header?**: No 🔓

Endpoint: http://localhost:8080/api/v1/auth

Ejemplo de body:

```json
{
  "email": "lucas@dominio.cl",
  "password": "1234"
}
```

Ejemplo de Response 200 ✅:

```json
{
  "id": "1df663d8-6a86-4c6f-9fdd-2a24eaf3b68c",
  "name": "lucas",
  "password": "$2a$10$vBv1YnNSn3p1Ng3k2fIKbOma6nwwmUtzoK.mmLknkBIOrCXFFUS72",
  "email": "lucas@dominio.cl",
  "phones": [
    {
      "number": "1234567",
      "cityCode": "1",
      "countryCode": "57"
    }
  ],
  "created": "2023-03-04T17:15:47.455408",
  "modified": null,
  "lastLogin": "2023-03-04T17:16:28.9329323",
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsdWNhc0Bkb21pbmlvLmNsIiwiZXhwIjoxNjc3OTg0OTQ3LCJpYXQiOjE2Nzc5NjA5NDd9.bd5ZW8LVNbQKBf3uIDRfuXstsGeymLmNGdtvgTXDxV0",
  "active": true
}
```

### Editar Usuario **PUT**

**Requiere de JWT en Header?**: Si 🔐

Endpoint: http://localhost:8080/api/v1/user/${idUsuario}

Ejemplo de body:

```json
{
  "name": "Lucas Modified User",
  "email": "lucas@dominio.cl",
  "password": "1234",
  "phones": [
    {
      "number": "1234567",
      "cityCode": "1",
      "countryCode": "57"
    }
  ]
}
```

Ejemplo de Response 200 ✅:

```json
{
  "id": "1df663d8-6a86-4c6f-9fdd-2a24eaf3b68c",
  "name": "Lucas Modified User",
  "password": "$2a$10$6AWl9OWnupOU6HHCPvieHOeD1p4hivPzS4WMkeRBUfU4cNDEQtp5i",
  "email": "lucas@dominio.cl",
  "phones": [
    {
      "number": "1234567",
      "cityCode": "1",
      "countryCode": "57"
    }
  ],
  "created": "2023-03-04T17:15:47.455408",
  "modified": "2023-03-04T17:19:37.4023406",
  "lastLogin": "2023-03-04T17:15:47.455408",
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsdWNhc0Bkb21pbmlvLmNsIiwiZXhwIjoxNjc3OTg1MTc3LCJpYXQiOjE2Nzc5NjExNzd9.WU19a7XG7n--g1ck-ckRUk5el1mzyEMo-ThEGZjRSQE",
  "active": true
}
```

### Retornar Usuario **GET**

_Metodo para corroborar que se modifico o se creo satisfactoriamente_

**Requiere de JWT en Header?**: Si 🔐

Endpoint: http://localhost:8080/api/v1/user/${idUsuario}

Ejemplo de Response 200 ✅:

```json
{
  "id": "1df663d8-6a86-4c6f-9fdd-2a24eaf3b68c",
  "name": "Lucas Modified User",
  "password": "$2a$10$6AWl9OWnupOU6HHCPvieHOeD1p4hivPzS4WMkeRBUfU4cNDEQtp5i",
  "email": "lucas@dominio.cl",
  "phones": [
    {
      "number": "1234567",
      "cityCode": "1",
      "countryCode": "57"
    }
  ],
  "created": "2023-03-04T17:15:47.455408",
  "modified": "2023-03-04T17:19:37.402341",
  "lastLogin": "2023-03-04T17:15:47.455408",
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsdWNhc0Bkb21pbmlvLmNsIiwiZXhwIjoxNjc3OTg1MTc3LCJpYXQiOjE2Nzc5NjExNzd9.WU19a7XG7n--g1ck-ckRUk5el1mzyEMo-ThEGZjRSQE",
  "active": true
}
```

# Diagramas

Los diagramas se encuentran en:

```bash
src/resource/diagrams
```

![Diagrama de flujo API AUTH](./src/main/resources/diagrams/Auth.png)
