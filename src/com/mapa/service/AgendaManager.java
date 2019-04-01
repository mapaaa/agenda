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
    public void addEvent(int id, String name, String description, Date date, String location, boolean allDay) {
        Event newEvent = new Event(id, name, date);
        newEvent.setDescription(description);
        newEvent.setLocation(location);
        newEvent.setAllDay(allDay);
        agenda.calendarEventsList.add(newEvent);
    }

    public void addNote(int id, String name, String content) {
        Note newNote = new Note(id, name);
        newNote.setContent(content);
        agenda.notesList.add(newNote);
    }

    public void addReminder(int id, String name, Date date, boolean allDay) {
        Reminder newReminder = new Reminder(id, name, date);
        newReminder.setAllDay(allDay);
        agenda.calendarEventsList.add(newReminder);
    }

    public void addTask(int id, String name, String description) {
        Task newTask = new Task(id, name);
        newTask.setDescription(description);
        agenda.tasksList.put(id, newTask);
    }

    // MODIFY
    public void completeTask(int id) {
        Task task = agenda.tasksList.get(id);
        if (task != null) {
            task.complete();
        }
    }

    // GET
    public List<AgendaEntry> getAllEntries() {
        List<AgendaEntry> allEntriesList = new ArrayList<>();
        allEntriesList.addAll(agenda.calendarEventsList);
        allEntriesList.addAll(agenda.notesList);
        allEntriesList.addAll(agenda.tasksList.values());
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
        return new ArrayList<>(agenda.tasksList.values());
    }

    public Task getTaskById(int id) {
        return agenda.tasksList.get(id);
    }
}
