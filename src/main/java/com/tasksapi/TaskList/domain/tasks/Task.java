package com.tasksapi.TaskList.domain.tasks;

import com.tasksapi.TaskList.domain.users.User;
import com.tasksapi.TaskList.dto.DatosEditarTask;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity(name = "Task")
@Table(name = "tasks")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private boolean completed;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "fecha_de_creacion", nullable = false)
    private LocalDate fechaDeCreacion;

    public Task(String title, String description, TaskPriority priority, User id) {
        this.id = null;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.completed = false;
        this.user = id;
        this.fechaDeCreacion = fechaDeCreacion();
    }

    public void actualizarTarea(DatosEditarTask datos) {
        if (datos.title() != null)
            this.title = datos.title();
        if (datos.description() != null)
            this.description = datos.description();
        this.completed = datos.completed();
        if (datos.priority() != null)
            this.priority = datos.priority();
    }

    private LocalDate fechaDeCreacion() {
        return LocalDate.now();
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                ", priority=" + priority +
                ", user=" + user +
                ", fechaDeCreacion=" + fechaDeCreacion +
                '}';
    }
}
