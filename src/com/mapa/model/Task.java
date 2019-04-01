package com.mapa.model;

public class Task extends AgendaEntry {
    private String description;
    private TaskState state;


    public Task(int id, String name) {
        super(id, name);
        this.state = TaskState.TODO;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public void complete() {
        this.state = TaskState.DONE;
    }

    public void start() {
        this.state = TaskState.DOING;
    }

    public void setTodo() {
        this.state = TaskState.TODO;
    }
}
