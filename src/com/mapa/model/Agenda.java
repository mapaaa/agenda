package com.mapa.model;

import com.mapa.service.CSVIO;
import com.mapa.service.DatabaseManager;

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
        this.calendarEvents = DatabaseManager.getInstance().SelectAllRemindersAndEvents(user.getId());
        this.notes = DatabaseManager.getInstance().SelectAllNotes(user.getId());
        this.tasks = DatabaseManager.getInstance().SelectAllTasks(user.getId());
    }
}
