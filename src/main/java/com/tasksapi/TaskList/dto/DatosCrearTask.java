package com.tasksapi.TaskList.dto;

import com.tasksapi.TaskList.domain.tasks.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosCrearTask(
        @NotNull
        @NotBlank
        String title,

        @NotNull
        String description,

        @NotNull
        TaskPriority priority

) {
}
