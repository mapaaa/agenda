package com.mapa.model;

public class Note extends AgendaEntry {
    //TODO: contents could be more than just text i.e. images
    private String content;
    public Note(int id, String name) {
        super(id, name);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
