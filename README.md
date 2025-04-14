# TASK_LIST

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


