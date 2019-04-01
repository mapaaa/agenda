package com.mapa;

import com.mapa.model.User;
import com.mapa.service.AccountManager;
import com.mapa.service.AgendaManager;

import java.util.Date;

public class Main {

    public static void main(String[] args) {
        User user = new User(123, "Jane", "Doe", "jane.dow@aaa.com", new Date());
        var agendaManager = AgendaManager.getInstance();
        var accountManager = AccountManager.getInstance(user);
        //TODO: maybe builders?
        //TODO: id manager
        agendaManager.addEvent(1, "event", "event description", new Date(), "11 Academiei", false);
        agendaManager.addEvent(2, "all day event", "all day event description", new Date(), "11 Academiei", true);
        agendaManager.addNote(3, "Shopping", "Tomatoes, Potatoes");
        agendaManager.addReminder(4, "Submit homework", new Date(), false);
        agendaManager.addTask(5,"Homework", "Details in Proiect.txt");

        var allEntries = agendaManager.getAllEntries();
        System.out.println(allEntries.size());  // 5
        for (var entry : allEntries) {
            System.out.println(entry);
        }

        System.out.println("\n");

        System.out.println("Let's complete task with id = 5");
        agendaManager.completeTask(5);
        System.out.println(agendaManager.getTaskById(5));

        System.out.println("\n");

        System.out.println("Current user: " + accountManager.getUser());
        user.setEmailAddress("notAnEmail@aa@bb.com");
        accountManager.modifyUser(user);
        System.out.println("User email modified");
        System.out.println("Current userL " + accountManager.getUser());
    }
}
