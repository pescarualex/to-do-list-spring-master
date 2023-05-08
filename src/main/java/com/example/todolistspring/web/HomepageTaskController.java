package com.example.todolistspring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todolistspring.service.TaskService;

@CrossOrigin
@RestController
@RequestMapping("/homepage")
public class HomepageTaskController {

    public final TaskService taskService;

    @Autowired
    public HomepageTaskController(TaskService taskService) {
        this.taskService = taskService;
    }

@GetMapping("/tasks-number")
    public ResponseEntity<Integer> totalNrOfTasks() {
        Integer allTasks = taskService.getTotalNrOfTasks();

        return new ResponseEntity<Integer>(allTasks, HttpStatus.OK);
    }
    
}
