package com.mapa.service;

import com.mapa.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AgendaManager {
    private static AgendaManager instance;
    private static Agenda agenda;

    private AgendaManager(User user){
        agenda = new Agenda(user);
    }

    static AgendaManager getInstance(User user) {
        if (instance == null) {
            return instance = new AgendaManager(user);
        }
        return instance;
    }

    // Add
    public void addEvent(int id, String name, String description, Date date, Date endDate, String location, boolean allDay, Category category) {
        Event newEvent = new Event(id, name, description, location, date, endDate, allDay);
        newEvent.setCategory(category);
        agenda.calendarEventsList.put(id, newEvent);
    }

    public void addNote(int id, String name, String content, Category category) {
        Note newNote = new Note(id, name, content);
        newNote.setCategory(category);
        agenda.notesList.put(id, newNote);
    }

    public void addReminder(int id, String name, Date date, boolean allDay, Category category) {
        Reminder newReminder = new Reminder(id, name, date, allDay);
        newReminder.setCategory(category);
        agenda.calendarEventsList.put(id, newReminder);
    }

    public void addTask(int id, String name, String description, TaskState state, Category category) {
        Task newTask = new Task(id, name, description, state);
        newTask.setCategory(category);
        agenda.tasksList.put(id, newTask);
    }

    // Get
    public List<AgendaEntry> getAllEntries() {
        List<AgendaEntry> allEntriesList = new ArrayList<>();
        allEntriesList.addAll(agenda.calendarEventsList.values());
        allEntriesList.addAll(agenda.notesList.values());
        allEntriesList.addAll(agenda.tasksList.values());
        return allEntriesList;
    }

    public List<Event> getAllEvents() {
        return agenda.calendarEventsList.values().stream()
                .filter(Event.class::isInstance)
                .map(Event.class::cast)
                .collect(Collectors.toList());
    }

    public List<Note> getAllNotes() {
        List<Note> allNotesList = new ArrayList<>(agenda.notesList.values());
        return allNotesList;
    }

    public List<Reminder> getAllReminders() {
        return agenda.calendarEventsList.values().stream()
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

    // Update
    public void completeTask(int id) {
        Task task = agenda.tasksList.get(id);
        if (task != null) {
            task.complete();
        }
    }

    // delete
    public void deleteEvent(int id) {
        agenda.calendarEventsList.remove(id);
    }

    public void deleteNote(int id) {
        agenda.notesList.remove(id);
    }

    public void deleteReminder(int id) {
        agenda.calendarEventsList.remove(id);
    }

    public void deleteTask(int id) {
        agenda.tasksList.remove(id);
    }
}
