package com.mapa.model;

public class Note extends AgendaEntry {
    //TODO: contents could be more than just text i.e. images
    private String content;
    public Note(int id, int uid, String name, String content) {
        super(id, uid, name);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return getName() + ", " + getContent();
    }
}
