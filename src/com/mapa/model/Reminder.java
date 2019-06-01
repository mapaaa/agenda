package com.mapa.model;

import java.time.LocalDate;

public class Reminder extends CalendarEntry {
    public Reminder(int id, int uid, String name, LocalDate date, boolean allDay) {
        super(id, uid, name, date, allDay);
    }

    public boolean isNowOrPast() {
        return getDate().compareTo(LocalDate.now()) <= 0;
    }

    @Override
    public String toString() {
        return getName() + ", " + getDate();
    }
}
