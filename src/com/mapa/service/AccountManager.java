package com.mapa.service;

import com.mapa.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class AccountManager {
    private static AccountManager instance;
    private User user;

    private AccountManager(){ }

    public static AccountManager getInstance() {
        if (instance == null) {
            instance = new AccountManager();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void modifyUser(User newUser) {
        user = newUser;
    }

    // Performs username and password verification and returns corresponding user id
    public boolean Login(String emailAddress, String password) {
        Logger.Log("Login initiated");
        Optional<Integer> userId;
        String passwordHash = md5HashPassword(password);
        userId = DatabaseManager.getInstance().CheckCredential(emailAddress, passwordHash);
        if (userId.isPresent()) {
            user = getUserById(userId.get());
            return true;
        } else {
            return false;
        }
    }

    public boolean Register(String firstName, String lastName, String birthDay, String emailAddress, String password) {
        String passwordHash = md5HashPassword(password);
        try {
            LocalDate birthDate = LocalDate.parse(birthDay, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            var user = createNewUser(firstName, lastName, emailAddress, birthDate);
            SaveData(user, passwordHash);
            Login(emailAddress, password);
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }

    private void SaveData(User user, String passwordHash) {
        DatabaseManager.getInstance().CreateCredential(user.getId(), user.getEmailAddress(), passwordHash);
    }

    private String md5HashPassword(String password) {
        String passwordHash = "";
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            md5.update(password.getBytes());

            byte[] passwordBytes = md5.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for (byte passwordByte : passwordBytes) {
                stringBuilder.append(Integer.toString((passwordByte & 0xff) + 0x100, 16).substring(1));
            }
            passwordHash = stringBuilder.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return passwordHash;
    }

    private User createNewUser(String firstName, String lastName, String emailAddress, LocalDate birthDay) {
        User user = new User(0, firstName, lastName, emailAddress, birthDay);
        int userId = DatabaseManager.getInstance().Create(user);
        user = new User(userId, firstName, lastName, emailAddress, birthDay);
        return user;
    }

    private User getUserById(Integer userId) {
        return DatabaseManager.getInstance().SelectUser(userId);
    }
}
