package com.tasksapi.TaskList.repository;

import com.tasksapi.TaskList.domain.tasks.Task;
import com.tasksapi.TaskList.domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long idUser);

    Task findByIdAndUserId(Long id, Long idUser);
}
