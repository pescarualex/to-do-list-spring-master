package com.example.todolistspring.transfer.taskDTO;

import java.time.LocalDate;

import javax.validation.constraints.Size;

public class GetTasksRequest {
    
    @Size(min = 5, max = 250)
    private String title;
    @Size(min = 5, max = 2000)
    private String description;
    private LocalDate deadline;
    private boolean done;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "Task [title=" + title + ", description=" + description + ", deadline=" + deadline
                + ", done=" + done + "]";
    }
}
