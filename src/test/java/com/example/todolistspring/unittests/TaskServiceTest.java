package com.example.todolistspring.unittests;

import com.example.todolistspring.domain.Task;
import com.example.todolistspring.persistance.TaskRepository;
// import com.example.todolistspring.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;
    // private TaskService taskService;

    @Test
    void saveTaskWhenExistingTask() {
        Task task = new Task();
        task.setDeadline(LocalDate.now());
        task.setDescription("qwerty");
        task.setDone(false);

        taskRepository.save(task);
        verify(taskRepository).save(task);
    }

}
