package com.tasksapi.TaskList.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosAuthenticationUser(
        @NotNull
        @NotBlank
        String email,

        @NotNull
        @NotBlank
        String password
) {
}
