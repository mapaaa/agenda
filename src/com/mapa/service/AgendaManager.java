package com.mapa.service;

import com.mapa.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AgendaManager {
    private static AgendaManager instance;
    private static Agenda agenda;

    private AgendaManager(){
        agenda = new Agenda();
    }

    public static AgendaManager getInstance() {
        if (instance == null) {
            return instance = new AgendaManager();
        }
        return instance;
    }

    // ADD
    public void addEvent(String name, String description, Date date, String location, boolean allDay) {
        Event newEvent = new Event(name, date);
        newEvent.setDescription(description);
        newEvent.setLocation(location);
        newEvent.setAllDay(allDay);
    }

    public void addNote(String name, String content) {
        Note newNote = new Note(name);
        newNote.setContent(content);
    }

    public void addReminder(String name, Date date, boolean allDay) {
        Reminder reminder = new Reminder(name, date);
        reminder.setAllDay(allDay);
    }

    public void addTask(String name, String description) {
        Task newTask = new Task(name);
        newTask.setDescription(description);
        agenda.tasksList.add(newTask);
    }

    // GET
    public List<AgendaEntry> getAllEntries() {
        List<AgendaEntry> allEntriesList = new ArrayList<>();
        allEntriesList.addAll(agenda.calendarEventsList);
        allEntriesList.addAll(agenda.notesList);
        allEntriesList.addAll(agenda.tasksList);
        return allEntriesList;
    }

    public List<Event> getAllEvents() {
        return agenda.calendarEventsList.stream()
                .filter(Event.class::isInstance)
                .map(Event.class::cast)
                .collect(Collectors.toList());
    }

    public List<Note> getAllNotes() {
        return agenda.notesList;
    }

    public List<Reminder> getAllReminders() {
        return agenda.calendarEventsList.stream()
                .filter(Reminder.class::isInstance)
                .map(Reminder.class::cast)
                .collect(Collectors.toList());
    }

    public List<Task> getAllTasks() {
        return agenda.tasksList;
    }
}
