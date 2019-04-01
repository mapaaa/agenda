package com.mapa.model;

import java.util.Date;

public abstract class CalendarEntry extends AgendaEntry {
    private Date date;
    private String description;
    //TODO: add geolocation instead of String
    private String location;

    CalendarEntry(String name, Date date) {
        super(name);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
