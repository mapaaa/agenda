package com.mapa.model;

import java.util.Date;

public class Reminder extends CalendarEntry {
    public Reminder(int id, String name, Date date) {
        super(id, name, date);
    }

    public boolean isNowOrPast() {
        return getDate().compareTo(new Date()) <= 0;
    }
}
