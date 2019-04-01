package com.mapa.model;

import java.util.Date;

public class Event extends CalendarEntry {
    private String description;
    //TODO: add geolocation instead of String
    private String location;

    public Event(int id, String name, Date date) {
        super(id, name, date);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return getName() + ", " + getDescription() + ", " + getLocation() + ", " + getDate();
    }
}
