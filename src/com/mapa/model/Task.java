package com.mapa.model;

public class Task extends AgendaEntry {
    private String description;
    Task(String name) {
        super(name);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
