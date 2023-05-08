package com.example.todolistspring.service;

import com.example.todolistspring.domain.Task;
import com.example.todolistspring.exception.ResourceNotFoundException;
import com.example.todolistspring.persistance.TaskRepository;
import com.example.todolistspring.transfer.taskDTO.CreateTaskRequest;
import com.example.todolistspring.transfer.taskDTO.TasksResponse;
import com.example.todolistspring.transfer.taskDTO.UpdateTask;
import com.example.todolistspring.transfer.taskDTO.UpdateTaskContent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(CreateTaskRequest request) {
        LOGGER.info("Creating task: " + request);

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDeadline(request.getDeadline());
        task.setDescription(request.getDescription());
        task.setDone(request.isDone());

        return taskRepository.save(task);
    }

    public Task getTask(long id) {
        LOGGER.info("Get task with id: " + id);

        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task " + id + " does not exist"));
    }


    public Page<TasksResponse> findTasksWithPagination(Pageable pageable) {
        LOGGER.info("Getting a page of tasks: page_number->[{}], page_size->[{}], offset->[{}]",
         pageable.getPageNumber(), pageable.getPageSize(), pageable.getOffset());

        Page<Task> pageOfTasks = taskRepository.findAll(pageable);

        List<TasksResponse> mappedTasks = new ArrayList<>();
        for(Task primaryTask : pageOfTasks.getContent()) {
            TasksResponse tasksResponse = mapTaskResponse(primaryTask);
            mappedTasks.add(tasksResponse);
        }

        return new PageImpl<>(mappedTasks, pageable, pageOfTasks.getTotalElements());
    }

    // public List<Task> getAllTasks (){
    //     LOGGER.info("Getting all tasks");
    //     List<Task> newList = taskRepository.findAll();

    //     return newList;
    // }

    public Integer getTotalNrOfTasks() {
        LOGGER.info("Getting total number of tasks.");
        List<Task> allTasks = taskRepository.findAll();

        return allTasks.size();
    }


    public Task updateTask(long id, UpdateTask request) {
        LOGGER.info("Update task " + id + " with " + request);

        Task updateTask = getTask(id);

        BeanUtils.copyProperties(request, updateTask);

        return taskRepository.save(updateTask);
    }

    public Task updateTaskContent(long id, UpdateTaskContent requContent) {
        LOGGER.info("Update task \"{}\" with content: Title - \"{}\", Description - \"{}\"", id, requContent.getTitle(), requContent.getDescription());

        Task task = taskRepository.getById(id);

        task.setTitle(requContent.getTitle().trim());
        task.setDescription(requContent.getDescription().trim());

        return taskRepository.save(task);
    }



    @Transactional
    public void deleteTask(long id) {
        LOGGER.info("Deleting task " + id);

        taskRepository.deleteById(id);
    }

    //posible to use in future
    private TasksResponse mapTaskResponse(Task task) {
        TasksResponse tasksResponse = new TasksResponse();
        tasksResponse.setId(task.getId());
        tasksResponse.setTitle(task.getTitle());
        tasksResponse.setDescription(task.getDescription());
        tasksResponse.setDeadline(task.getDeadline());
        tasksResponse.setDone(task.isDone());

        return tasksResponse;
    }
}
