package com.example.todolistspring.web;

import com.example.todolistspring.domain.Task;
import com.example.todolistspring.service.TaskService;
import com.example.todolistspring.transfer.taskDTO.CreateTaskRequest;
import com.example.todolistspring.transfer.taskDTO.TasksResponse;
import com.example.todolistspring.transfer.taskDTO.UpdateTask;
import com.example.todolistspring.transfer.taskDTO.UpdateTaskContent;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Void> createTask(
        @RequestBody @Valid CreateTaskRequest request
    ) {
        taskService.createTask(request);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // TODO: change this path if it is necessary
    @GetMapping("/any")
    public ResponseEntity<Page<TasksResponse>> getAPageOfTasks(
        @RequestParam(name = "title") String title,
        Pageable pageable
    ) {
        Page<TasksResponse> findTasksWithPagination =
            taskService.findTasksWithPagination(title, pageable);

        return new ResponseEntity<>(findTasksWithPagination, HttpStatus.OK);
    }

    @GetMapping("/dialog/{id}")
    public ResponseEntity<Task> getOneTask(@PathVariable("id") Long id) {
        Task taskResponse = taskService.getTask(id);

        return new ResponseEntity<>(taskResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
        @PathVariable long id,
        @RequestBody UpdateTask request
    ) {
        Task task = taskService.updateTask(id, request);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @RequestMapping(value = "/dialog/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Task> updateTaskContent(
        @PathVariable("id") long id,
        @RequestBody UpdateTaskContent request
    ) {
        Task task = taskService.getTask(id);
        UpdateTaskContent uTaskContent = new UpdateTaskContent();
        uTaskContent.setTitle(request.getTitle());
        uTaskContent.setDescription(request.getDescription());

        uTaskContent.getTitle().trim();
        uTaskContent.getDescription().trim();

        Task taskUpdated = taskService.updateTaskContent(
            task.getId(),
            uTaskContent
        );

        return new ResponseEntity<>(taskUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable long id) {
        taskService.deleteTask(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/this-day-tasks")
    public ResponseEntity<Page<TasksResponse>> getThisDayTasksWPage(
        @RequestParam(name = "title") String title,
        Pageable pageable
    ) {
        Page<TasksResponse> getThisDayTasks =
            taskService.getThisDayTasksWithPagination(title, pageable);

        return new ResponseEntity<>(getThisDayTasks, HttpStatus.OK);
    }

    @GetMapping("/overdue-tasks")
    public ResponseEntity<Page<TasksResponse>> getOverdueTasksWPage(
        @RequestParam(name = "title") String title,
        Pageable pageable
    ) {
        Page<TasksResponse> getThisDayTasks =
            taskService.getOverdueTasksWithPagination(title, pageable);

        return new ResponseEntity<>(getThisDayTasks, HttpStatus.OK);
    }
}
