package com.example.todolistspring.transfer.taskDTO;

public class UpdateTaskContent {

    private String title;
    private String description;

    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    
    @Override
    public String toString() {
        return "UpdateTaskContent [title=" + title + ", description=" + description + "]";
    }

}
