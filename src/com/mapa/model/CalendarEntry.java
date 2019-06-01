package com.mapa.model;

import java.time.LocalDate;

public abstract class CalendarEntry extends AgendaEntry {
    private LocalDate date;
    private boolean allDay;

    CalendarEntry(int id, int uid, String name, LocalDate date) {
        super(id, uid, name);
        this.allDay = false;
        this.date = date;
    }

    CalendarEntry(int id, int uid, String name, LocalDate date, boolean allDay) {
        super(id, uid, name);
        this.date = date;
        this.allDay = allDay;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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
}
