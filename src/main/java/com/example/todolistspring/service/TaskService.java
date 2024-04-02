package com.example.todolistspring.service;

import com.example.todolistspring.domain.Task;
import com.example.todolistspring.exception.ResourceNotFoundException;
import com.example.todolistspring.persistance.TaskRepository;
import com.example.todolistspring.transfer.taskDTO.CreateTaskRequest;
import com.example.todolistspring.transfer.taskDTO.TasksResponse;
import com.example.todolistspring.transfer.taskDTO.UpdateTask;
import com.example.todolistspring.transfer.taskDTO.UpdateTaskContent;

import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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




    // get all tasks number, titles and all content

    public Integer getTotalNrOfTasks() {
        LOGGER.info("Getting total number of all tasks.");
        List<Task> allTasks = taskRepository.findAll();

        return allTasks.size();
    }


    public List<String> getAllTasksTitles(){
        LOGGER.info("Getting all tasks title for homepage");
        List<Task> allTasks = taskRepository.findAll();
        
        List<String> tasksTitle = new ArrayList<>();
        for(Task task : allTasks){
            tasksTitle.add(task.getTitle());
        }  

        return tasksTitle;
  }

    public Page<TasksResponse> findTasksWithPagination(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), 16);

        LOGGER.info("Getting all tasks: page_number->[{}], page_size->[{}], offset->[{}]",
         pageable.getPageNumber(), pageable.getPageSize(), pageable.getOffset());

        Page<Task> pageOfTasks = taskRepository.findAll(pageable);

        List<TasksResponse> mappedTasks = new ArrayList<>();
        for(Task primaryTask : pageOfTasks.getContent()) {
            TasksResponse tasksResponse = mapTaskResponse(primaryTask);
            mappedTasks.add(tasksResponse);
        }

        return new PageImpl<>(mappedTasks, pageable, pageOfTasks.getTotalElements());
    }










// get current day  tasks number, titles and all content
  public Integer getThisDayTotalNrOfTasks() {
    LOGGER.info("Getting total number of today tasks.");
    List<Task> allTasks = taskRepository.findAll();
    LocalDate today = LocalDate.now();
    int countTask = 0;

    for(Task task : allTasks){
        if (task.getDeadline().isEqual(today)) {
            countTask++;
        }
    }
    return countTask;
}

  public List<String> getThisDayTasksTitle(){
    LOGGER.info("Getting today tasks title for homepage");
    List<Task> allTasks = taskRepository.findAll();
    
    List<String> thisDayTasksTitle = new ArrayList<>();
    LocalDate today = LocalDate.now();

    for(Task task : allTasks){
        if(task.getDeadline().isEqual(today)){
            thisDayTasksTitle.add(task.getTitle());
        }
        
    }  

    return thisDayTasksTitle;
}

public Page<TasksResponse> getThisDayTasksWithPagination(Pageable pageable) {
    pageable = PageRequest.of(pageable.getPageNumber(), 16);

    LOGGER.info("Getting today all tasks: page_number->[{}], page_size->[{}], offset->[{}]",
     pageable.getPageNumber(), pageable.getPageSize(), pageable.getOffset());
     LocalDate today = LocalDate.now();
    Page<Task> pageOfTasks = taskRepository.findAllByDeadline(today, pageable);


    List<TasksResponse> mappedTasks = new ArrayList<>();
    for(Task primaryTask : pageOfTasks.getContent()) {
            TasksResponse tasksResponse = mapTaskResponse(primaryTask);
            mappedTasks.add(tasksResponse);
        }

    return new PageImpl<>(mappedTasks, pageable, pageOfTasks.getTotalElements());

    }





   //get overdue tasks number, titles and all content

public Integer getNumberOfOverdueTasks(){
    LOGGER.info("Get number of overdue tasks");

    List<Task> allTasks = taskRepository.findAll();
    LocalDate today = LocalDate.now();
    int overdueNumber = 0;

    for(Task task : allTasks){
        if (task.getDeadline().isBefore(today)) {
            overdueNumber++;
        }
    }

    return overdueNumber;
}

public List<String> getOverdueTasksTitle(){
    LOGGER.info("Getting overdue tasks title for homepage");
    List<Task> allTasks = taskRepository.findAll();
    
    List<String> overdueTasks = new ArrayList<>();
    LocalDate today = LocalDate.now();

    for(Task task : allTasks){
        if(task.getDeadline().isBefore(today)){
            overdueTasks.add(task.getTitle());
        }
        
    }  

    return overdueTasks;
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
