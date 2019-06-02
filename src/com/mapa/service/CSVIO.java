package com.mapa.service;

import com.mapa.model.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDateTime;

public class CSVIO {

    public static void Save(String[] values, String fileName) {
        try {
            FileWriter fileWriter = new FileWriter("data/" + fileName, true);
            fileWriter.append("\n");
            fileWriter.append(String.join(",", values));
            fileWriter.append(",");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Optional<Integer> checkCredentials(String emailAddress, String passwordHash) {
        try {
            BufferedReader credentialsBuffer = new BufferedReader(new FileReader("data/credentials.csv"));
            String line;
            while ((line = credentialsBuffer.readLine()) != null) {
                String[] info = line.split(",");
                if (info[0].equals(emailAddress) && info[1].equals(passwordHash)) {
                    return Optional.of(Integer.valueOf(info[2]));
                }
            }
            credentialsBuffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static void SaveUserData(User user, String passwordHash) {
        String birthDay = new SimpleDateFormat("dd/MM/yyyy").format(user.getBirthDay());
        String[] userInfo = {String.valueOf(user.getId()), user.getFirstName(), user.getLastName(), user.getEmailAddress(), birthDay};
        String[] credentialsInfo = {user.getEmailAddress(), passwordHash, String.valueOf(user.getId())};

        Save(userInfo, "users.csv");
        Save(credentialsInfo, "credentials.csv");
        Logger.Log("User data saved");
    }

    public static long GetNextUserId() {
        Path path = Paths.get("data/credentials.csv");
        try {
            long userId = Files.lines(path).count() + 1;
            return userId;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Map<Integer, CalendarEntry> loadCalendarEntries(int userId) {
        Map<Integer, CalendarEntry> calendarEvents = new HashMap<>();
        try {
            FileReader reminders = new FileReader("data/reminders.csv");
            BufferedReader buffer = new BufferedReader(reminders);
            String line;
            while ((line = buffer.readLine()) != null) {
                String[] values = line.split(",");
                int id = Integer.valueOf(values[0]);
                int uid = Integer.valueOf(values[1]);
                int catid = Integer.valueOf(values[2]);
                Category category = CategoryManager.getInstanceWhenLoggedIn().GetCategory(catid);
                String name = values[3];
                LocalDateTime date = LocalDateTime.parse(values[4], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                boolean allDay = Boolean.parseBoolean(values[5]);
                if (uid == userId) {
                    Reminder reminder = new Reminder(id, uid, name, date, allDay);
                    reminder.setCategory(category);
                    calendarEvents.put(id, reminder);
                }
            }
            buffer.close();
            reminders.close();
            FileReader events = new FileReader("data/events.csv");
            buffer = new BufferedReader(events);
            while ((line = buffer.readLine()) != null) {
                String[] values = line.split(",");
                int id = Integer.valueOf(values[0]);
                int uid = Integer.valueOf(values[1]);
                int catid = Integer.valueOf(values[2]);
                Category category = CategoryManager.getInstanceWhenLoggedIn().GetCategory(catid);
                String name = values[3];
                String description = values[4];
                String location = values[5];
                LocalDateTime date = LocalDateTime.parse(values[6], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                LocalDateTime endDate = LocalDateTime.parse(values[7], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                boolean allDay = Boolean.parseBoolean(values[8]);
                if (uid == userId) {
                    Event event = new Event(id, uid, name, description, location, date, endDate, allDay);
                    event.setCategory(category);
                    calendarEvents.put(id, event);
                }
            }
            buffer.close();
            events.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return calendarEvents;
    }

    public static Map<Integer, Note> loadNotes(int userId) {
        Map<Integer, Note> notes = new HashMap<>();
        try {
            FileReader notesFile = new FileReader("data/notes.csv");
            BufferedReader buffer = new BufferedReader(notesFile);
            String line;
            while ((line = buffer.readLine()) != null) {
                String[] values = line.split(",");
                int id = Integer.valueOf(values[0]);
                int uid = Integer.valueOf(values[1]);
                int catid = Integer.valueOf(values[2]);
                Category category = CategoryManager.getInstanceWhenLoggedIn().GetCategory(catid);
                String name = values[3];
                String content = values[4];
                if (uid == userId) {
                    Note note = new Note(id, uid, name, content);
                    note.setCategory(category);
                    notes.put(id, note);
                }
            }
            buffer.close();
            notesFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return notes;
    }

    public static Map<Integer, Task> loadTasks(int userId) {
        Map<Integer, Task> tasks = new HashMap<>();
        try {
            FileReader tasksFile = new FileReader("data/tasks.csv");
            BufferedReader buffer = new BufferedReader(tasksFile);
            String line;
            while ((line = buffer.readLine()) != null) {
                String[] values = line.split(",");
                int id = Integer.valueOf(values[0]);
                int uid = Integer.valueOf(values[1]);
                int catid = Integer.valueOf(values[2]);
                Category category = CategoryManager.getInstanceWhenLoggedIn().GetCategory(catid);
                String name = values[3];
                String description = values[4];
                TaskState state = TaskState.valueOf(values[5]);
                if (uid == userId) {
                    Task task = new Task(id, uid, name, description, state);
                    task.setCategory(category);
                    tasks.put(id, task);
                }
            }
            buffer.close();
            tasksFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public static User GetUserById(Integer userId) {
        User user = null;
        try {
            BufferedReader usersBuffer = new BufferedReader(new FileReader("data/users.csv"));
            String line;
            while ((line = usersBuffer.readLine()) != null) {
                String[] info = line.split(",");
                if (userId.compareTo(Integer.parseInt(info[0])) == 0) {
                    user = new User(
                            userId,
                            info[1],
                            info[2],
                            info[3],
                            LocalDateTime.parse(info[4], DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    break;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static List<Category> LoadCategories(int userId) {
        List<Category> categoryList = new ArrayList<>();
        try {
            FileReader categories = new FileReader("data/categories.csv");
            BufferedReader buffer = new BufferedReader(categories);
            String line;
            while((line = buffer.readLine()) != null){
                String[] values = line.split(",");
                int id = Integer.valueOf(values[0]);
                int uid = Integer.valueOf(values[1]);
                String label = values[2];
                if (uid == userId) {
                    categoryList.add(new Category(id, uid, label));
                }
            }
            buffer.close();
            categories.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    public static void SaveCategory(Category category, int userId) {
        String[] values = {String.valueOf(category.getId()), String.valueOf(userId), category.getLabel()};
        Save(values, "categories.csv");

        Logger.Log("Category saved");
    }

    public static void SaveReminder(Reminder reminder, int userId) {
        String[] values = {
                String.valueOf(reminder.getId()),
                String.valueOf(userId),
                String.valueOf(reminder.getCategory().getId()),
                reminder.getName(),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(reminder.getDate()),
                String.valueOf(reminder.getAllDay())};
        Save(values, "reminders.csv");
        Logger.Log("Reminder saved");
    }

    public static void SaveEvent(Event event, int userId) {
        String[] values = {
                String.valueOf(event.getId()),
                String.valueOf(userId),
                String.valueOf(event.getCategory().getId()),
                event.getName(),
                event.getDescription(),
                event.getLocation(),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(event.getDate()),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(event.getEndDate()),
                String.valueOf(event.getAllDay())};
        Save(values, "events.csv");
        Logger.Log("Event saved");
    }

    public static void SaveNote(Note note, int userId) {
        String[] values = {
                String.valueOf(note.getId()),
                String.valueOf(userId),
                String.valueOf(note.getCategory().getId()),
                note.getName(),
                note.getContent()};
        Save(values, "notes.csv");
        Logger.Log("Note saved");
    }

    public static void SaveTask(Task task, int userId) {
        String[] values = {
                String.valueOf(task.getId()),
                String.valueOf(userId),
                String.valueOf(task.getCategory().getId()),
                task.getName(),
                task.getDescription(),
                String.valueOf(task.getState())};
        Save(values, "tasks.csv");
        Logger.Log("Task saved");
    }
}
