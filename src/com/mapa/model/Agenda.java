package com.mapa.model;

import com.mapa.service.CategoryManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Agenda {
    private User user;

    // Events and reminders need to appear on the calendar
    public Map<Integer, CalendarEntry> calendarEventsList;

    // Notes are standalone
    public Map<Integer, Note> notesList;

    // Tasks are standalone
    public Map<Integer, Task> tasksList;

    public Agenda(User user) {
        this.user = user;
        loadEntries();
    }

    private void loadEntries() {
        this.calendarEventsList = new HashMap<>();
        try {
            FileReader reminders = new FileReader("data/reminders.csv");
            BufferedReader buffer = new BufferedReader(reminders);
            String line;
            while((line = buffer.readLine()) != null){
                String[] values = line.split(",");
                int id = Integer.valueOf(values[0]);
                int uid = Integer.valueOf(values[1]);
                int catid = Integer.valueOf(values[2]);
                Category category = CategoryManager.getInstanceWhenLoggedIn().GetCategory(catid);
                String name = values[3];
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(values[4]);
                boolean allDay = Boolean.parseBoolean(values[5]);
                if (uid == user.getId()) {
                    Reminder reminder = new Reminder(id, name, date, allDay);
                    reminder.setCategory(category);
                    calendarEventsList.put(id, reminder);
                }
            }
            buffer.close();
            reminders.close();
            FileReader events = new FileReader("data/events.csv");
            buffer = new BufferedReader(events);
            while((line = buffer.readLine()) != null){
                String[] values = line.split(",");
                int id = Integer.valueOf(values[0]);
                int uid = Integer.valueOf(values[1]);
                int catid = Integer.valueOf(values[2]);
                Category category = CategoryManager.getInstanceWhenLoggedIn().GetCategory(catid);
                String name = values[3];
                String description = values[4];
                String location = values[5];
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(values[6]);
                Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(values[7]);
                boolean allDay = Boolean.parseBoolean(values[8]);
                if (uid == user.getId()) {
                    Event event = new Event(id, name, description, location, date, endDate, allDay);
                    event.setCategory(category);
                    calendarEventsList.put(id, event);
                }
            }
            buffer.close();
            events.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        this.notesList = new HashMap<>();
        try {
            FileReader notes = new FileReader("data/notes.csv");
            BufferedReader buffer = new BufferedReader(notes);
            String line;
            while((line = buffer.readLine()) != null){
                String[] values = line.split(",");
                int id = Integer.valueOf(values[0]);
                int uid = Integer.valueOf(values[1]);
                int catid = Integer.valueOf(values[2]);
                Category category = CategoryManager.getInstanceWhenLoggedIn().GetCategory(catid);
                String name = values[3];
                String content = values[4];
                if (uid == user.getId()) {
                    Note note = new Note(id, name, content);
                    note.setCategory(category);
                    notesList.put(id, note);
                }
            }
            buffer.close();
            notes.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.tasksList = new HashMap<>();
        try {
            FileReader tasks = new FileReader("data/tasks.csv");
            BufferedReader buffer = new BufferedReader(tasks);
            String line;
            while((line = buffer.readLine()) != null){
                String[] values = line.split(",");
                int id = Integer.valueOf(values[0]);
                int uid = Integer.valueOf(values[1]);
                int catid = Integer.valueOf(values[2]);
                Category category = CategoryManager.getInstanceWhenLoggedIn().GetCategory(catid);
                String name = values[3];
                String description = values[4];
                TaskState state = TaskState.valueOf(values[5]);
                if (uid == user.getId()) {
                    Task task = new Task(id, name, description, state);
                    task.setCategory(category);
                    tasksList.put(id, task);
                }
            }
            buffer.close();
            tasks.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
