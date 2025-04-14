package com.tasksapi.TaskList.controller;

import com.tasksapi.TaskList.domain.users.User;
import com.tasksapi.TaskList.dto.DatosCrearTask;
import com.tasksapi.TaskList.dto.DatosDetallesTask;
import com.tasksapi.TaskList.dto.DatosEditarTask;
import com.tasksapi.TaskList.repository.TaskRepository;
import com.tasksapi.TaskList.services.TaskService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final TaskRepository taskRepository;

    @Autowired
    public TaskController(TaskService taskService, TaskRepository taskRepository) {
        this.taskService = taskService;
        this.taskRepository = taskRepository;
    }

    //Endpoint para crear una tarea
    @PostMapping
    @Transactional
    public ResponseEntity<DatosDetallesTask> crearTarea(@RequestBody @Valid DatosCrearTask datos, UriComponentsBuilder uriComponentsBuilder, @AuthenticationPrincipal User user) {
        //Se crea la tarea y almacena en la base de datos
        var task = taskService.createTask(datos, user);
        //Se pasan los datos creados de task a DatosDetallesTask para visualizar los resultados
        DatosDetallesTask datosDetallesTask = new DatosDetallesTask(task.getId(), task.getTitle(), task.getDescription(), task.isCompleted(), task.getPriority(), task.getUser().getId());
        //Se crea una URI para ubicar la tarea especifica
        URI urlTask = uriComponentsBuilder.path("/tasks/{id}").buildAndExpand(task.getId()).toUri();

        return ResponseEntity.created(urlTask).body(datosDetallesTask);
    }

    //Endpoint para actualizar una tarea
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosDetallesTask> editarTarea(@PathVariable Long id, @RequestBody @Valid DatosEditarTask datos, @AuthenticationPrincipal User user) {
        var task = new DatosDetallesTask(taskService.editarTarea(id, datos, user));
        return ResponseEntity.accepted().body(task);
    }


    //Endpoint para eliminar una tarea
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosDetallesTask> eliminarTarea(@PathVariable Long id, @AuthenticationPrincipal User user) {
        var task = taskService.buscarTarea(id, user);
        this.taskRepository.deleteById(task.getId());
        return ResponseEntity.noContent().build();
    }


    //Endpoint para listar todas las tareas
    @GetMapping
    public ResponseEntity<List<DatosDetallesTask>> listarTasks(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(taskService.listarTareasUser(user));
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosDetallesTask> mostrarTarea(@PathVariable Long id, @AuthenticationPrincipal User user) {
        var task = this.taskService.buscarTarea(id, user);
        var detallesTarea = new DatosDetallesTask(task);
        return ResponseEntity.ok(detallesTarea);
    }
}
