package com.mapa.model;

import java.util.Date;

public class Event extends CalendarEntry {
    private String description;
    //TODO: add geolocation instead of String
    private String location;
    private Date endDate;

    public Event(int id, String name, String description, String location, Date date, Date endDate, boolean allDay) {
        super(id, name, date, allDay);
        this.description = description;
        this.location = location;
        this.endDate = endDate;
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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return getName() + ", " + getDescription() + ", " + getLocation() + ", " + getDate() + ", " + getEndDate();
    }
}
