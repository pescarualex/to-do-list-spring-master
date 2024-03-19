package com.example.todolistspring.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todolistspring.domain.Task;
import com.example.todolistspring.service.TaskService;
import com.example.todolistspring.transfer.taskDTO.TasksResponse;

@CrossOrigin
@RestController
@RequestMapping("/homepage")
public class HomepageTaskController {

    public final TaskService taskService;

    @Autowired
    public HomepageTaskController(TaskService taskService) {
        this.taskService = taskService;
    }

@GetMapping("/total-all-tasks-number")
public ResponseEntity<Integer> totalNrOfTasks() {
    Integer allTasks = taskService.getTotalNrOfTasks();

    return new ResponseEntity<Integer>(allTasks, HttpStatus.OK);
}




@GetMapping("/total-task-number/all-tasks-titles")
public ResponseEntity<List<String>> allTasksTitles(){
    List<String> tasksTitle = taskService.getAllTasksTitles();

    return new ResponseEntity<>(tasksTitle, HttpStatus.OK);
}








@GetMapping("/total-this-day-tasks-number")
public ResponseEntity<Integer> thisDayTotalNrOfTasks() {
    Integer allTasks = taskService.getThisDayTotalNrOfTasks();

    return new ResponseEntity<Integer>(allTasks, HttpStatus.OK);
}


@GetMapping("/total-task-number/this-day-tasks-titles")
public ResponseEntity<List<String>> thisDayTasksTitles(){
    List<String> tasksTitle = taskService.getThisDayTasksTitle();

    return new ResponseEntity<>(tasksTitle, HttpStatus.OK);
}

  

    // @GetMapping("/tasks-titles")
    // public ResponseEntity<List<Task>> getAllTasks(){
    //     List<Task> tasks = taskService.getAllTasks();

    //     return new ResponseEntity<>(tasks, HttpStatus.OK);
    // }




}