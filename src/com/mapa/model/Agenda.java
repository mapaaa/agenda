package com.mapa.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Agenda {
    private User user;

    // Events and reminders need to appear on the calendar
    public List<CalendarEntry> calendarEventsList;

    // Notes are standalone
    public List<Note> notesList;

    // Tasks are standalone
    public Map<Integer, Task> tasksList;

    public Agenda() {
        this.calendarEventsList = new ArrayList<>();
        this.notesList = new ArrayList<>();
        this.tasksList = new HashMap<>();
    }
}
