package com.mapa.service;

import com.mapa.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

@SuppressWarnings("MagicConstant")
public class DatabaseManager {
    private static Connection conn;
    private static DatabaseManager instance;

    private DatabaseManager() {
        conn = ConnectionUtils.getInstance().getConnection();
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public int Create(User user) {
        String query = "insert into user(u_id, first_name, last_name, email, birth_date)" +
                "values(?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setNull(1, Types.INTEGER);
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getEmailAddress());
            ps.setDate(5, java.sql.Date.valueOf(user.getBirthDay()));
            ps.executeUpdate();
            ResultSet key = ps.getGeneratedKeys();
            key.next();
            return key.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int Create(Category category) {
        String query = "insert into category(c_id, u_id, c_label)" +
                "values(?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setNull(1, Types.INTEGER);
            ps.setInt(2, category.getUid());
            ps.setString(3, category.getLabel());
            ps.executeUpdate();
            ResultSet key = ps.getGeneratedKeys();
            key.next();
            return key.getInt(1);
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void CreateCredential(int u_id, String email, String password_hash) {
        String query = "insert into credential(u_id, email, password_hash)" +
                "values(?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, u_id);
            ps.setString(2, email);
            ps.setString(3, password_hash);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int Create(Event event)  {
        String query = "insert into event(e_id, u_id, c_id, e_all_day, e_desc, e_end_date, e_location, e_name, e_start_date)" +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setNull(1, Types.INTEGER);
            ps.setInt(2, event.getUid());
            var cat = event.getCategory();
            if (cat == null)  {
                ps.setNull(3, Types.INTEGER);
            }
            else {
                ps.setInt(3, cat.getId());
            }
            ps.setBoolean(4, event.getAllDay());
            ps.setString(5, event.getDescription());
            ps.setDate(6, java.sql.Date.valueOf(event.getEndDate()));
            ps.setString(7, event.getLocation());
            ps.setString(8, event.getName());
            ps.setDate(9, java.sql.Date.valueOf(event.getDate()));
            ps.executeUpdate();
            ResultSet key = ps.getGeneratedKeys();
            key.next();
            return key.getInt(1);
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int Create(Note note)  {
        String query = "insert into note(n_id, u_id, c_id, n_cont, n_name)" +
                "values(?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setNull(1, Types.INTEGER);
            ps.setInt(2, note.getUid());
            var cat = note.getCategory();
            if (cat == null)  {
                ps.setNull(3, Types.INTEGER);
            }
            else {
                ps.setInt(3, cat.getId());
            }
            ps.setString(4, note.getContent());
            ps.setString(5, note.getName());
            ps.executeUpdate();
            ResultSet key = ps.getGeneratedKeys();
            key.next();
            return key.getInt(1);
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int Create(Task task)  {
        String query = "insert into task(t_id, u_id, c_id, t_desc, t_name, t_state)" +
                "values(?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setNull(1, Types.INTEGER);
            ps.setInt(2, task.getUid());
            var cat = task.getCategory();
            if (cat == null)  {
                ps.setNull(3, Types.INTEGER);
            }
            else {
                ps.setInt(3, cat.getId());
            }
            ps.setString(4, task.getDescription());
            ps.setString(5, task.getName());
            ps.setString(6, task.getState().name());
            ps.executeUpdate();
            ResultSet key = ps.getGeneratedKeys();
            key.next();
            return key.getInt(1);
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int Create(Reminder reminder)  {
        String query = "insert into reminder(r_id, u_id, c_id, r_all_day, r_date, r_name)" +
                "values(?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setNull(1, Types.INTEGER);
            ps.setInt(2, reminder.getUid());
            var cat = reminder.getCategory();
            if (cat == null)  {
                ps.setNull(3, Types.INTEGER);
            }
            else {
                ps.setInt(3, cat.getId());
            }
            ps.setBoolean(4, reminder.getAllDay());
            ps.setDate(5, java.sql.Date.valueOf(reminder.getDate()));
            ps.setString(6, reminder.getName());
            ps.executeUpdate();
            ResultSet key = ps.getGeneratedKeys();
            key.next();
            return key.getInt(1);
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void Update(User user) {
        String query = "update user set first_name = ?, last_name = ?, email = ?, birth_date = ?" +
                " where u_id = ?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmailAddress());
            ps.setDate(4, java.sql.Date.valueOf(user.getBirthDay()));
            ps.setInt(5, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Update(Category category) {
        String query = "update category set c_label = ?)" +
                " where c_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, category.getLabel());
            ps.setInt(2, category.getId());
            ps.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void UpdateCredential(int u_id, String email, String password_hash) {
        String query = "update credential set email = ?, password_hash = ?" +
                " where u_id = ?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password_hash);
            ps.setInt(3, u_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Update(Event event)  {
        String query = "update event set c_id = ?, e_all_day = ?, e_desc = ?, e_end_date = ?, e_location = ?, e_name = ?, e_start_date = ?)" +
                " where e_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            var cat = event.getCategory();
            if (cat == null)  {
                ps.setNull(1, Types.INTEGER);
            }
            else {
                ps.setInt(1, cat.getId());
            }
            ps.setBoolean(2, event.getAllDay());
            ps.setString(3, event.getDescription());
            ps.setDate(4, java.sql.Date.valueOf(event.getEndDate()));
            ps.setString(5, event.getLocation());
            ps.setString(6, event.getName());
            ps.setDate(7, java.sql.Date.valueOf(event.getDate()));
            ps.setInt(8, event.getId());
            ps.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void Update(Note note)  {
        String query = "update note set c_id = ?, n_cont = ?, n_name = ?" +
                " where n_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            var cat = note.getCategory();
            if (cat == null)  {
                ps.setNull(1, Types.INTEGER);
            }
            else {
                ps.setInt(1, cat.getId());
            }
            ps.setString(2, note.getContent());
            ps.setString(3, note.getName());
            ps.setInt(3, note.getId());
            ps.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void Update(Task task)  {
        String query = "update task set c_id = ?, t_desc = ?, t_name = ?, t_state = ?" +
                " where t_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            var cat = task.getCategory();
            if (cat == null)  {
                ps.setNull(1, Types.INTEGER);
            }
            else {
                ps.setInt(1, cat.getId());
            }
            ps.setString(2, task.getDescription());
            ps.setString(3, task.getName());
            ps.setString(4, task.getState().name());
            ps.setInt(5, task.getId());
            ps.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void Update(Reminder reminder)  {
        String query = "update reminder set c_id = ?, r_all_day = ?, r_date = ?, r_name = ?" +
                " where r_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            var cat = reminder.getCategory();
            if (cat == null)  {
                ps.setNull(1, Types.INTEGER);
            }
            else {
                ps.setInt(1, cat.getId());
            }
            ps.setBoolean(2, reminder.getAllDay());
            ps.setDate(3, java.sql.Date.valueOf(reminder.getDate()));
            ps.setString(4, reminder.getName());
            ps.setInt(5, reminder.getId());
            ps.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public User SelectUser(int userId) {
        String query = "select * from user" +
                " where u_id = ?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String emailAddress = rs.getString("email");
                LocalDate birthDay = rs.getDate("birth_date").toLocalDate();
                return new User(userId, firstName, lastName, emailAddress, birthDay);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Category SelectCategory(int categoryId) {
        String query = "select * from category" +
                " where c_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int uid  = rs.getInt("u_id");
                String label = rs.getString("c_label");
                return new Category(categoryId, uid, label);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Category> SelectAllCategories(int uid) {
        String query = "select * from category" +
                " where u_id = ?";
        List<Category> m = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, uid);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int categoryId  = rs.getInt("c_id");
                String label = rs.getString("c_label");
                m.add(new Category(categoryId, uid, label));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return m;
    }

    public Optional<Integer> CheckCredential(String email, String password_hash) {
        String query = "select * from credential where email = ?";
        Optional<Integer> userId = Optional.empty();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String db_password_hash = rs.getString("password_hash");
                if (db_password_hash.equals(password_hash)) {
                    userId = Optional.of(rs.getInt("u_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }

    public Event SelectEvent(int eventId)  {
        String query = "select * from event where e_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int uid = rs.getInt("u_id");
                String name = rs.getString("e_name");
                String description = rs.getString("e_desc");
                String location = rs.getString("e_location");
                LocalDate date = rs.getDate("e_start_date").toLocalDate();
                LocalDate endDate = rs.getDate("e_end_date").toLocalDate();
                boolean allDay = rs.getBoolean("e_all_day");
                return new Event(eventId, uid, name, description, location, date, endDate, allDay);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<Integer, Event> SelectAllEvents(int uid)  {
        String query = "select * from event where u_id = ?";
        Map<Integer, Event> m = new HashMap<>();
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, uid);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int eventId = rs.getInt("e_id");
                String name = rs.getString("e_name");
                String description = rs.getString("e_desc");
                String location = rs.getString("e_location");
                LocalDate date = rs.getDate("e_start_date").toLocalDate();
                LocalDate endDate = rs.getDate("e_end_date").toLocalDate();
                boolean allDay = rs.getBoolean("e_all_day");
                m.put(eventId, new Event(eventId, uid, name, description, location, date, endDate, allDay));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return m;
    }

    public Note SelectNote(int noteId)  {
        String query = "select * from note where n_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, noteId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int uid = rs.getInt("u_id");
                String name = rs.getString("n_name");
                String content = rs.getString("content");
                return new Note(noteId, uid, name, content);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<Integer, Note> SelectAllNotes(int uid)  {
        String query = "select * from note where u_id = ?";
        Map<Integer, Note> m = new HashMap<>();
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, uid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int noteId = rs.getInt("n_id");
                String name = rs.getString("n_name");
                String content = rs.getString("content");
                m.put(noteId, new Note(noteId, uid, name, content));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return m;
    }

    public Task SelectTask(int taskId)  {
        String query = "select * from task where t_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, taskId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int uid = rs.getInt("u_id");
                String name = rs.getString("t_name");
                String description = rs.getString("t_desc");
                TaskState state = TaskState.valueOf(rs.getString("t_state"));
                return new Task(taskId, uid, name, description, state);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Map<Integer, Task> SelectAllTasks(int uid)  {
        String query = "select * from task where u_id = ?";
        Map<Integer, Task> m = new HashMap<>();
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, uid);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int taskId = rs.getInt("t_id");
                String name = rs.getString("t_name");
                String description = rs.getString("t_desc");
                TaskState state = TaskState.valueOf(rs.getString("t_state"));
                m.put(taskId, new Task(taskId, uid, name, description, state));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return m;
    }

    public Reminder SelectReminder(int reminderId)  {
        String query = "select * from reminder where r_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, reminderId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int uid = rs.getInt("u_id");
                String name = rs.getString("r_name");
                LocalDate date = rs.getDate("r_date").toLocalDate();
                boolean allDay = rs.getBoolean("r_all_day");
                return new Reminder(reminderId, uid, name, date, allDay);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<Integer, Reminder> SelectAllReminders(int uid)  {
        String query = "select * from reminder where u_id = ?";
        Map<Integer, Reminder> m = new HashMap<>();
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, uid);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int reminderId = rs.getInt("u_id");
                String name = rs.getString("r_name");
                LocalDate date = rs.getDate("r_date").toLocalDate();
                boolean allDay = rs.getBoolean("r_all_day");
                m.put(reminderId, new Reminder(reminderId, uid, name, date, allDay));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return m;
    }

    public void DeleteUser(int userId) {
        String query1 = "delete from user" +
                " where u_id = ?";
        PreparedStatement ps1 = null;
        String query2 = "delete from credential where u_id = ?";
        PreparedStatement ps2 = null;
        try {
            ps1 = conn.prepareStatement(query1);
            ps1.setInt(1, userId);
            ps1.executeUpdate();
            ps2 = conn.prepareStatement(query2);
            ps2.setInt(1, userId);
            ps2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DeleteCategory(int categoryId) {
        String query = "delete from category" +
                " where c_id = ?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, categoryId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DeleteNote(int noteId) {
        String query = "delete from note" +
                " where n_id = ?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, noteId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DeleteEvent(int eventId) {
        String query = "delete from event" +
                " where e_id = ?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, eventId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DeleteTask(int taskId) {
        String query = "delete from task" +
                " where t_id = ?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, taskId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DeleteReminder(int reminderId) {
        String query = "delete from reminder" +
                " where r_id = ?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, reminderId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
