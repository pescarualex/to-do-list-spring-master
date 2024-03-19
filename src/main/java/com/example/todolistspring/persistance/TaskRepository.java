package com.example.todolistspring.persistance;

import com.example.todolistspring.domain.Task;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

Page<Task> findAllByDeadline(LocalDate deadline, Pageable pageable);

}
