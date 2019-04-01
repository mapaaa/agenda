package com.mapa.model;

import java.util.Calendar;
import java.util.Date;

public class Reminder extends CalendarEntry {
    public Reminder(String name, Date date) {
        super(name, date);
    }

    public boolean isNowOrPast() {
        return getDate().compareTo(new Date()) <= 0;
    }
}
