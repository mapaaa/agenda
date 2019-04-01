package com.mapa.model;

import java.util.Date;

public abstract class CalendarEntry extends AgendaEntry {
    private Date date;
    private boolean allDay;

    CalendarEntry(int id, String name, Date date) {
        super(id, name);
        this.allDay = false;
        this.date = date;
    }

    CalendarEntry(int id, String name, Date date, boolean allDay) {
        super(id, name);
        this.date = date;
        this.allDay = allDay;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
