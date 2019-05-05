package com.mapa.service;

public class Application {
    private AccountManager accountManager;
    private AgendaManager agendaManager;
    private CategoryManager categoryManager;

    public void Start() {
        accountManager = AccountManager.getInstance();

        categoryManager = CategoryManager.getInstance(accountManager.getUser().getId());
        agendaManager = AgendaManager.getInstance(accountManager.getUser());
    }

    public void Demo() {
        System.out.println("These are all your entries: ");
        var entries = agendaManager.getAllEntries();
        for (var entry : entries) {
            System.out.println(entry.customToString());
        }
    }
}
