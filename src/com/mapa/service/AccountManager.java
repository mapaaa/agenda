package com.mapa.service;

import com.mapa.model.User;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class AccountManager {
    private static AccountManager instance;
    private User user;

    private AccountManager(){ }

    public static AccountManager getInstance() {
        if (instance == null) {
            instance = new AccountManager();
        }
        if (instance.user == null) {
            instance.Login();
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
    public void Login() {
        Optional<Integer> userId = Optional.empty();
        int attempts = 3;
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Login");
        boolean foundCredentials = false;
        try {
            while (attempts != 0 && !foundCredentials) {
                --attempts;
                System.out.println("Enter email address: ");
                String emailAddress = buffer.readLine();
                System.out.println("Enter password: ");
                JPasswordField pf = new JPasswordField();
                String password = JOptionPane.showConfirmDialog(null, pf, "Enter password: ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION ? new String(pf.getPassword()) : "";
                String passwordHash = md5HashPassword(password);
                BufferedReader credentialsBuffer = new BufferedReader(new FileReader("data/credentials.csv"));
                String line;
                while ((line = credentialsBuffer.readLine()) != null) {
                    String[] info = line.split(",");
                    if (info[0].equals(emailAddress) && info[1].equals(passwordHash)) {
                        foundCredentials = true;
                        userId = Optional.of(Integer.parseInt(info[2]));
                        break;
                    }
                }
                if (!foundCredentials) {
                    System.out.println("Wrong username or password. Please try again.");
                }

            }
            if (attempts == 0) {
                System.out.println("Sorry...too many login attempts");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (userId.isPresent()) {
            user = getUserById(userId.get());
            if (user != null) {
                System.out.println("Login successful");
                System.out.println("Welcome back " + user.getFirstName());
            }
         }
    }

    // Register new user
    // TODO: validations
    public void Register() {
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
                Date birthDay = new SimpleDateFormat("dd/MM/yyyy").parse(buffer.readLine());

                var user = createNewUser(firstName, lastName, emailAddress, birthDay);
                formCompleted = true;

                SaveData(user, passwordHash);
            }
        }
        catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Register successful! Redirecting to login...");
        Login();
    }

    private void SaveData(User user, String passwordHash) {
        try {
            BufferedWriter usersBuffer = new BufferedWriter(new FileWriter("data/users.csv", true));
            BufferedWriter credentialsBuffer = new BufferedWriter(new FileWriter("data/credentials.csv", true));

            String birthDay = new SimpleDateFormat("dd/MM/yyyy").format(user.getBirthDay());
            String userInfo = user.getId() + " " + user.getFirstName() + " " + user.getLastName() + " " + user.getEmailAddress() + " " + birthDay;
            String credentialsInfo = user.getEmailAddress() + " " + passwordHash + " " + user.getId();

            usersBuffer.newLine();
            usersBuffer.append(userInfo);

            credentialsBuffer.newLine();
            credentialsBuffer.append(credentialsInfo);
            credentialsBuffer.close();
            usersBuffer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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

    private User createNewUser(String firstName, String lastName, String emailAddress, Date birthDay) {
        User newUser = null;
        try {
            Path path = Paths.get("data/credentials.csv");
            long userId = Files.lines(path).count() + 1;
            System.out.println(userId);
            newUser = new User((int)userId, firstName, lastName, emailAddress, birthDay);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newUser;
    }

    private User getUserById(Integer userId) {
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
                            new SimpleDateFormat("dd/MM/yyyy").parse(info[4]));
                    break;
                }
            }
        }
        catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return user;
    }
}
