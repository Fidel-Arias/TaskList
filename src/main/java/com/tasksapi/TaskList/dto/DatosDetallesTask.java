package com.tasksapi.TaskList.dto;

import com.tasksapi.TaskList.domain.tasks.Task;
import com.tasksapi.TaskList.domain.tasks.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.BooleanFlag;

public record DatosDetallesTask(
        Long id,
        String title,
        String description,

        @NotNull
        @BooleanFlag
        boolean completed,
        TaskPriority priority,
        Long idUser
) {
    public DatosDetallesTask (Task task) {
        this(task.getId(), task.getTitle(), task.getDescription(), task.isCompleted(), task.getPriority(), task.getUser().getId());
    }
}
