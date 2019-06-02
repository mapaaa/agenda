package com.mapa.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class CalendarEntry extends AgendaEntry {
    private LocalDateTime date;
    private boolean allDay;

    CalendarEntry(int id, int uid, String name, LocalDateTime date) {
        super(id, uid, name);
        this.allDay = false;
        this.date = date;
    }

    CalendarEntry(int id, int uid, String name, LocalDateTime date, boolean allDay) {
        super(id, uid, name);
        this.date = date;
        this.allDay = allDay;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    public boolean getAllDay() {
        return allDay;
    }

    @Override
    public String toString() {
        return getName() + ", " + getDate() + ", " + getAllDay();
    }

    public String getDateTime() {
        return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }
}
