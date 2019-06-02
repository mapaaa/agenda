package com.mapa.model;

import java.time.LocalDateTime;

public class Reminder extends CalendarEntry {
    public Reminder(int id, int uid, String name, LocalDateTime date, boolean allDay) {
        super(id, uid, name, date, allDay);
    }

    public boolean isNowOrPast() {
        return getDate().compareTo(LocalDateTime.now()) <= 0;
    }

    @Override
    public String toString() {
        return getName() + ", " + getDate();
    }
}
