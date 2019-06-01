package com.mapa.model;

public abstract class AgendaEntry {
    private int id;
    private String name;
    private Category category;
    private int uid;

    AgendaEntry(int id, int uid, String name) {
        this.id = id;
        this.uid = uid;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public int getUid() {
        return uid;
    }
}
