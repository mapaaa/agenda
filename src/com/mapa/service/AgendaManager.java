package com.mapa.service;

import com.mapa.model.*;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class AgendaManager {
    private static int userId;
    private static AgendaManager instance;
    private static Agenda agenda;

    private AgendaManager(User user){
        agenda = new Agenda(user);
        userId = user.getId();
    }

    static AgendaManager getInstance(User user) {
        if (instance == null) {
            return instance = new AgendaManager(user);
        }
        return instance;
    }

    // Add
    public void addEvent(int id, String name, String description, LocalDate date, LocalDate endDate, String location, boolean allDay, Category category) {
        Logger.Log("Create event locally");
        Event newEvent = new Event(id, userId, name, description, location, date, endDate, allDay);
        newEvent.setCategory(category);
        agenda.calendarEvents.put(id, newEvent);
        DatabaseManager.getInstance().Create(newEvent);
    }

    public void addNote(int id, String name, String content, Category category) {
        Logger.Log("Create note locally");
        Note newNote = new Note(id, userId, name, content);
        newNote.setCategory(category);
        agenda.notes.put(id, newNote);
        DatabaseManager.getInstance().Create(newNote);
    }

    public void addReminder(int id, String name, LocalDate date, boolean allDay, Category category) {
        Logger.Log("Create reminder locally");
        Reminder newReminder = new Reminder(id, userId, name, date, allDay);
        newReminder.setCategory(category);
        agenda.calendarEvents.put(id, newReminder);
        DatabaseManager.getInstance().Create(newReminder);
    }

    public void addTask(int id, String name, String description, TaskState state, Category category) {
        Logger.Log("Create task locally");
        Task newTask = new Task(id, userId, name, description, state);
        newTask.setCategory(category);
        agenda.tasks.put(id, newTask);
        DatabaseManager.getInstance().Create(newTask);
    }

    // Get
    public List<AgendaEntry> getAllEntries() {
        Logger.Log("Get all entries");
        List<AgendaEntry> allEntriesList = new ArrayList<>();
        allEntriesList.addAll(agenda.calendarEvents.values());
        allEntriesList.addAll(agenda.notes.values());
        allEntriesList.addAll(agenda.tasks.values());
        return allEntriesList;
    }

    public List<Event> getAllEvents() {
        Logger.Log("Get all events");
        return agenda.calendarEvents.values().stream()
                .filter(Event.class::isInstance)
                .map(Event.class::cast)
                .collect(Collectors.toList());
    }

    public List<Note> getAllNotes() {
        Logger.Log("Get all notes");
        List<Note> allNotesList = new ArrayList<>(agenda.notes.values());
        return allNotesList;
    }

    public List<Reminder> getAllReminders() {
        Logger.Log("Get all reminders");
        return agenda.calendarEvents.values().stream()
                .filter(Reminder.class::isInstance)
                .map(Reminder.class::cast)
                .collect(Collectors.toList());
    }

    public List<Task> getAllTasks() {
        Logger.Log("Get all tasks");
        return new ArrayList<>(agenda.tasks.values());
    }

    public Task getTaskById(int id) {
        Logger.Log("Get task by id");
        return agenda.tasks.get(id);
    }

    // Update
    public void completeTask(int id) {
        Logger.Log("Complete task locally");
        Task task = agenda.tasks.get(id);
        if (task != null) {
            task.complete();
            DatabaseManager.getInstance().Update(task);
        }
    }

    // delete
    public void deleteEvent(int id) {
        Logger.Log("Delete event locally");
        DatabaseManager.getInstance().DeleteEvent(id);
        agenda.calendarEvents.remove(id);
    }

    public void deleteNote(int id) {
        Logger.Log("Delete note locally");
        DatabaseManager.getInstance().DeleteNote(id);
        agenda.notes.remove(id);
    }

    public void deleteReminder(int id) {
        Logger.Log("Delete reminder locally");
        DatabaseManager.getInstance().DeleteReminder(id);
        agenda.calendarEvents.remove(id);
    }

    public void deleteTask(int id) {
        Logger.Log("Delete task locally");
        DatabaseManager.getInstance().DeleteTask(id);
        agenda.tasks.remove(id);
    }
}
