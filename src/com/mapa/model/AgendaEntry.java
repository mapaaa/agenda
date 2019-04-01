package com.mapa.model;

public abstract class AgendaEntry {
    private String name;

    AgendaEntry(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
