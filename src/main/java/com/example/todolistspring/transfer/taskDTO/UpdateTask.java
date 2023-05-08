package com.example.todolistspring.transfer.taskDTO;

public class UpdateTask {

    private boolean done;


    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }



    @Override
    public String toString() {
        return "UpdateTask [done=" + done + "]";
    }

}
