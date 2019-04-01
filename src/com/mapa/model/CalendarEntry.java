package com.mapa.model;

import java.util.Date;

public abstract class CalendarEntry extends AgendaEntry {
    private Date date;
    private boolean allDay;

    CalendarEntry(String name, Date date) {
        super(name);
        this.allDay = false;
        this.date = date;
    }

    CalendarEntry(String name, Date date, boolean allDay) {
        super(name);
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
}
