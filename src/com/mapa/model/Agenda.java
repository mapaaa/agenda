package com.mapa.model;

import com.mapa.service.CSVIO;

import java.util.*;

public class Agenda {
    private User user;

    // Events and reminders need to appear on the calendar
    public Map<Integer, CalendarEntry> calendarEvents;

    // Notes are standalone
    public Map<Integer, Note> notes;

    // Tasks are standalone
    public Map<Integer, Task> tasks;

    public Agenda(User user) {
        this.user = user;
        this.calendarEvents = CSVIO.loadCalendarEntries(user.getId());
        this.notes = CSVIO.loadNotes(user.getId());
        this.tasks = CSVIO.loadTasks(user.getId());
    }
}
