package com.mapa.model;

public abstract class AgendaEntry {
    private int id;
    private String name;

    AgendaEntry(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
