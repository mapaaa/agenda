package com.mapa.model;

public class Category {
    private int id;
    private int uid;
    private String label;

    public Category(int id, int uid, String label) {
        this.id = id;
        this.label = label;
        this.uid = uid;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return label;
    }

    public int getUid() {
        return uid;
    }
}
