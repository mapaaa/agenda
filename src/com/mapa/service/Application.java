package com.mapa.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Application {
    private Logger logger;
    private AccountManager accountManager;
    private AgendaManager agendaManager;
    private CategoryManager categoryManager;

    public void Start() {
        logger = new Logger("log.csv");
        logger.Log("Application Started");
        accountManager = AccountManager.getInstance();
    }

    public void Demo() {
        var entries = agendaManager.getAllEntries();
        System.out.println("These are all your entries: " + entries.size());
        for (var entry : entries) {
            System.out.println(entry.toString());
        }

        // Adding some entries for user 1
        agendaManager.addNote(3, "New Note", "Content", categoryManager.GetCategory(1));
        agendaManager.addReminder(3,
                "New Reminder",
                LocalDate.parse("2019-12-12 10:10:10", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                false,
                categoryManager.GetCategory(1));

        System.out.println("\n------------------");
        entries = agendaManager.getAllEntries();
        System.out.println("These are all your entries: " + entries.size());
        for (var entry : entries) {
            System.out.println(entry.toString());
        }
    }

    public boolean Register(String firstName, String lastName, String birthDate, String email, String password) {
        boolean registered = accountManager.Register(firstName, lastName, birthDate, email, password);
        if (registered) {
            categoryManager = CategoryManager.getInstance(accountManager.getUser().getId());
            agendaManager = AgendaManager.getInstance(accountManager.getUser());
        }
        return registered;
    }

    public boolean Login(String email, String password) {
        if (accountManager.Login(email, password)) {
            categoryManager = CategoryManager.getInstance(accountManager.getUser().getId());
            agendaManager = AgendaManager.getInstance(accountManager.getUser());
            return true;
        }
        return false;
    }

    public String getFirstName() {
        if (accountManager.getUser() != null) {
            return accountManager.getUser().getFirstName();
        }
        else {
            return "";
        }
    }
}
