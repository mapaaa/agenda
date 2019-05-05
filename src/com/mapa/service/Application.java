package com.mapa.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Application {
    private Logger logger;
    private AccountManager accountManager;
    private AgendaManager agendaManager;
    private CategoryManager categoryManager;

    public void Start() {
        logger = new Logger("log.csv");
        logger.Log("Application Started");
        accountManager = AccountManager.getInstance();
        categoryManager = CategoryManager.getInstance(accountManager.getUser().getId());
        agendaManager = AgendaManager.getInstance(accountManager.getUser());
    }

    public void Demo() {
        var entries = agendaManager.getAllEntries();
        System.out.println("These are all your entries: " + entries.size());
        for (var entry : entries) {
            System.out.println(entry.toString());
        }

        // Adding some entries for user 1
        agendaManager.addNote(3, "New Note", "Content", categoryManager.GetCategory(1));
        try {
            agendaManager.addReminder(3,
                    "New Reminder",
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-12-12 10:10:10"),
                    false,
                    categoryManager.GetCategory(1));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("\n------------------");
        entries = agendaManager.getAllEntries();
        System.out.println("These are all your entries: " + entries.size());
        for (var entry : entries) {
            System.out.println(entry.toString());
        }
    }
}
