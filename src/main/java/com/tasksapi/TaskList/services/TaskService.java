package com.tasksapi.TaskList.services;

import com.tasksapi.TaskList.domain.tasks.Task;
import com.tasksapi.TaskList.domain.users.User;
import com.tasksapi.TaskList.dto.DatosCrearTask;
import com.tasksapi.TaskList.dto.DatosDetallesTask;
import com.tasksapi.TaskList.dto.DatosEditarTask;
import com.tasksapi.TaskList.exceptions.ValidationException;
import com.tasksapi.TaskList.repository.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    //Inyeccion de dependencia del repositorio de tareas
    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    //Metodo para crear una tarea
    public Task createTask(DatosCrearTask datos, User user) {
        return taskRepository.save(new Task(datos.title(), datos.description(), datos.priority(), user));
    }

    public Task editarTarea(Long id, @Valid DatosEditarTask datos, User user) {
        Task task = buscarTarea(id, user);
        task.actualizarTarea(datos);
        return task;
    }
    public Task buscarTarea(Long idTask, User user) {
        if (idTask == null)
            throw new ValidationException("El id de la tarea no puede ser null");

        Task task = taskRepository.findByIdAndUserId(idTask, user.getId());
        if (task == null) {
            throw new RuntimeException("El usuario no tiene esta tarea");
        }
        if (!task.getUser().getId().equals(user.getId()))
            throw new RuntimeException("No tienes permiso para modificar esta tarea");

        return task;
    }

    public List<DatosDetallesTask> listarTareasUser(User user) {
        return taskRepository.findByUserId(user.getId()).stream()
                .map(DatosDetallesTask::new)
                .collect(Collectors.toList());
    }
}
