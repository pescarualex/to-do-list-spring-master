package com.example.todolistspring.persistance;

import com.example.todolistspring.domain.Task;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findAllByDeadline(LocalDate deadline, Pageable pageable);

    @Query(
        value = "SELECT task FROM Task task " +
        "WHERE (:searchTitle IS NULL OR task.title LIKE %:searchTitle%)"
    )
    Page<Task> findByTitleContainingIgnoreCase(
        String searchTitle,
        Pageable pageable
    );
}
