package com.example.todolistspring.integrationTests;

import com.example.todolistspring.domain.Task;
import com.example.todolistspring.service.TaskService;
import com.example.todolistspring.transfer.taskDTO.CreateTaskRequest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionSystemException;


import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@SpringBootTest
public class TaskServiceIntegrationTest {

    @Autowired
    private TaskService taskService;

    @Test
    public void createTask_whenValidRequest_thanReturnSavedTask() {
        CreateTaskRequest request = new CreateTaskRequest();
        request.setDescription("qwerty");
        request.setDeadline(LocalDate.now());
        request.setDone(true);

        Task task = taskService.createTask(request);

        assertThat(task, notNullValue());
        assertThat(task.getId(),notNullValue());
        assertThat(task.getId(), greaterThan(0L));
        assertThat(task.getDescription(), notNullValue());
        assertThat(task.getDescription(), is(request.getDescription()));
        assertThat(task.getDeadline(), notNullValue());
        assertThat(task.getDeadline(), is(request.getDeadline()));
        assertThat(task.isDone(), notNullValue());
    }

    @Test
    public void createTask_whenMissingTask_thanReturnNullPointerException() {
        Assertions.assertThrows(NullPointerException.class,
                () -> taskService.createTask(null));
    }

    @Test
    public void createTask_whenMissingDescription_thanThrowException() {
        CreateTaskRequest request = new CreateTaskRequest();
        request.setDeadline(LocalDate.now());
        request.setDone(true);

        Exception exception = null;

        try {
            taskService.createTask(request);
        } catch (Exception e) {
            exception = e;
        }

        assertThat("Missing description", exception instanceof TransactionSystemException);
    }



}
