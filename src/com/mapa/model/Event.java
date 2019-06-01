package com.mapa.model;

import java.time.LocalDate;

public class Event extends CalendarEntry {
    private String description;
    //TODO: add geolocation instead of String
    private String location;
    private LocalDate endDate;

    public Event(int id, int uid, String name, String description, String location, LocalDate date, LocalDate endDate, boolean allDay) {
        super(id, uid, name, date, allDay);
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

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return getName() + ", " + getDescription() + ", " + getLocation() + ", " + getDate() + ", " + getEndDate();
    }
}
