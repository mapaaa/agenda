package com.mapa.service;

import com.mapa.model.User;

import javax.swing.*;
import java.io.*;
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
    public boolean Login(String email, String password) {
        Logger.Log("Login initiated");
        Optional<Integer> userId;
        String passwordHash = md5HashPassword(password);
        userId = DatabaseManager.getInstance().CheckCredential(email, passwordHash);
        if (userId.isPresent()) {
            user = getUserById(userId.get());
            return true;
        } else {
            return false;
        }
    }

    // Register new user
    // TODO: validations
    public void Register() {
        Logger.Log("Register initiated");
        System.out.println("Please complete the following register form: ");
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));

        try {
            boolean formCompleted = false;
            while(!formCompleted) {
                System.out.println("Enter first name: ");
                String firstName = buffer.readLine();
                System.out.println("Enter last name: ");
                String lastName = buffer.readLine();
                System.out.println("Enter email address: ");
                String emailAddress = buffer.readLine();
                System.out.println("Enter password: ");
                JPasswordField pf1 = new JPasswordField();
                String password1 = JOptionPane.showConfirmDialog(null, pf1, "Enter password: ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION ? new String(pf1.getPassword()) : "";
                System.out.println("Confirm password: ");
                JPasswordField pf2 = new JPasswordField();
                String password2 = JOptionPane.showConfirmDialog(null, pf2, "Confirm password: ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION ? new String(pf2.getPassword()) : "";
                if (!password1.equals(password2)) {
                    System.out.println("Passwords don't match. Try again");
                    continue;
                }
                String passwordHash = md5HashPassword(password1);
                System.out.println("Enter birth date (dd/mm/yyyy format): ");
                LocalDate birthDay = LocalDate.parse(buffer.readLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                var user = createNewUser(firstName, lastName, emailAddress, birthDay);
                formCompleted = true;

                SaveData(user, passwordHash);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Register successful! Redirecting to login...");
    }

    private void SaveData(User user, String passwordHash) {
        DatabaseManager.getInstance().Create(user);
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
