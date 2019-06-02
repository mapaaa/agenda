package com.mapa.ui;

public class UiItem {
    private int id;
    private String display;
    private String type;

    public UiItem(int id, String type, String display) {
        this.id = id;
        this.type = type;
        this.display = display;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String toString() {
        return display;
    }
}
