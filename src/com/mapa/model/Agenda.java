package com.mapa.model;

import java.util.ArrayList;
import java.util.List;

public class Agenda {
    private User user;

    // Events and reminders need to appear on the calendar
    public List<CalendarEntry> calendarEventsList;

    // Notes are standalone
    public List<Note> notesList;

    // Tasks are standalone
    public List<Task> tasksList;

    public Agenda(User user) {
        this.user = user;
        this.calendarEventsList = new ArrayList<>();
        this.notesList = new ArrayList<>();
        this.tasksList = new ArrayList<>();
    }

    public User getUser() {
        return user;
    }
}
