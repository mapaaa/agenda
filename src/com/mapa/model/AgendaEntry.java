package com.mapa.model;

public abstract class AgendaEntry {
    private int id;
    private String name;
    private Category category;

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String customToString() {
        return this.toString() + ", " + category.toString();
    }
}
