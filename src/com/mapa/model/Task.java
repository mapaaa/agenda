package com.mapa.model;

public class Task extends AgendaEntry {
    private String description;
    private TaskState state;


    public Task(int id, String name, String description, TaskState state) {
        super(id, name);
        this.state = state;
        this.description = description;
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

    @Override
    public String toString() {
        return getName() + ", " + getDescription() + ", " + getState();
    }
}
