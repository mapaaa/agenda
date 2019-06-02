package com.mapa.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends CalendarEntry {
    private String description;
    //TODO: add geolocation instead of String
    private String location;
    private LocalDateTime endDate;

    public Event(int id, int uid, String name, String description, String location, LocalDateTime date, LocalDateTime endDate, boolean allDay) {
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

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return getName() + ", " + getDescription() + ", " + getLocation() + ", " + getDate() + ", " + getEndDate();
    }

    public String getEndDateTime() {
        return endDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }
}
