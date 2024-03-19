package com.example.todolistspring.persistance;

import com.example.todolistspring.domain.Task;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
