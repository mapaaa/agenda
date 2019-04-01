package com.mapa.service;

import com.mapa.model.User;

public class AccountManager {
    private static AccountManager instance;
    private static User user;

    //TODO: authentication
    private AccountManager(User user){
        AccountManager.user = user;
    }

    public static AccountManager getInstance(User user) {
        if (instance == null) {
            return instance = new AccountManager(user);
        }
        return instance;
    }

    public static User getUser() {
        return user;
    }

    public void modifyUser(User newUser) {
        user = newUser;
    }
}
