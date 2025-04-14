package com.tasksapi.TaskList.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DatosRegistrarUser(
        @NotBlank
        @NotNull
        String username,

        @Email
        @NotBlank
        String email,

        @NotBlank
        @Size(min = 8, max = 20)
        String password
) {
}
