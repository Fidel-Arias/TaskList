# TASK_LIST

# 🔨 CONFIGURACION DE LAS PROPIEDADES DE LA APLICACIÓN

Se debe configurar en el archivo `application.properties` ubicada en la carpeta **`/src/main/resources/`**

## PARA LA BASE DE DATOS

Antes de configurar la base de datos se debe crear primero dentro de tu gestor con el comando: `CREATE DATABASE tasks_list;`

+ Para la base de datos se puede utilizar `mariadb` o `mysql` solo se debe cambiar en la propiedad **`spring.datasource.url`** y escoger una de las dos, luego colocar el nombre de tu base de datos en la variable `${DB_NAME}`
+ Para la propiedad `spring.datasource.username` se debe crear una variable de entorno con nombre `${DB_USER}` y configurar el user segun tu base de datos local
+ Para la propiedad `spring.datasource.password` se debe crear una variable de entorno con nombre `${DB_PASSWORD}` y configurar el password segun tu base de datos local

```
spring.datasource.url=jdbc:[mariadb|mysql]://localhost/${DB_NAME}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
```

## 🔑 API SECRET PARA JWT

Para la API_SECRET DE JWT es recomendable usar una variable de entorno llamada `${API_SECRET}` esta puede tener cualquier valor numeros, letras o alfanumerica

```
api.security.secret=${API_SECRET}
```

# 💻 PETICIONES PARA EL BACKEND

## Crear Usuarios

Para crear usuarios en la base de datos se deben ingresar estos tres campos: username, email, password.

POST: `http://localhost:8080/register`

```
{
    "username":"username",
    "email":"correo@dominio",
    "password": "########" //Mayor o igual a 8
}
```

## Iniciar Sesión

POST: `http://localhost:8080/login`

Para iniciar sesión se necesitan dos campos: email y password. Al iniciar sesión se enviará un token del usuario para poder utilizar las otras apis

```
{
    "email":"danielcoila@gmail.com",
    "password": "daniel1234"
}
```
Respuesta del backend:
```
{
  "token":"[Bearer toekn]"
}
```
## Crear Tarea

POST: `http://localhost:8080/tasks`

Para crear tareas se necesitan estos campos: title, description y priority. Antes de realizar la petición se debe enviar el token obtenido al iniciar sesión

### Authorization_Bearer Token

`Token: •••••••`

```
{
    "title": "Rutina de corrida",
    "description": "Correr hasta hasta la joya",
    "priority": "HIGH"
}
```

## Editar Tarea

PUT: `http://localhost:8080/tasks/7`

Para editar una tarea simplemente se debe colocar el campo que se desea cambiar ya sea: title, description, completed o priority.

Requiere usar token

### Authorization_Bearer Token

`Token: •••••••`

```
{
    "completed": false
}
```

## Eliminar Tarea

DELETE: `http://localhost:8080/tasks/6`

Para eliminar una tarea simplemente se coloca el id de la tarea que se desea eliminar en la url ejm: `http://localhost:8080/tasks/[id]`

Requiere token

### Authorization_Bearer Token

`Token: •••••••`

## Listar Tareas

GET: `http://localhost:8080/tasks`

Para realizar una peticion de todas las tareas del usuario solo escribir en el enlace: `http://localhost:8080/tasks`

Requiere token

### Authorization_Bearer Token

`Token: •••••••`

## Mostrar Tarea

GET: `http://localhost:8080/tasks/8`

Para mostrar una tarea en especifico simplemente colocar en el enlace el id.
Ejm: `http://localhost:8080/tasks/[id]`

Requiere token

### Authorization_Bearer Token

`Token: •••••••`


