package com.mapa.service;

import com.mapa.model.User;

import javax.swing.*;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

public class AccountManager {
    private static AccountManager instance;
    private  User user;

    private AccountManager(){ }

    public static AccountManager getInstance() {
        if (instance == null) {
            return instance = new AccountManager();
        }
        return instance;
    }

    public  User getUser() {
        return user;
    }

    public void modifyUser(User newUser) {
        user = newUser;
    }

    // Performs username and password verification and returns corresponding user id
    public void Login() throws FileNotFoundException {
        Optional<Integer> userId = Optional.empty();
        int attempts = 3;
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Login");
        boolean foundCredentials = false;
        try {
            while (attempts != 0 && !foundCredentials) {
                --attempts;

                System.out.println("Enter email address: ");
                String userName = buffer.readLine();
                System.out.println("Enter password");
                JPasswordField pf = new JPasswordField();
                String password = JOptionPane.showConfirmDialog(null, pf, "Enter password: ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION ? new String(pf.getPassword()) : "";

                MessageDigest md5 = MessageDigest.getInstance("md5");
                md5.update(password.getBytes());

                byte[] passwordBytes = md5.digest();
                StringBuilder stringBuilder = new StringBuilder();
                for (byte passwordByte : passwordBytes) {
                    stringBuilder.append(Integer.toString((passwordByte & 0xff) + 0x100, 16).substring(1));
                }
                String passwordHash = stringBuilder.toString();
                BufferedReader credentialsBuffer = new BufferedReader(new FileReader("data/Credentials.txt"));
                String line;
                while ((line = credentialsBuffer.readLine()) != null) {
                    String[] info = line.split("\\s+");
                    if (info[0].equals(userName) && info[1].equals(passwordHash)) {
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
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if (userId.isPresent()) {
            User user = getUserById(userId.get());
            System.out.println("Login successful");
            System.out.println("Welcome back " + user.getFirstName());
         }
    }

    private User getUserById(Integer userId) throws FileNotFoundException {
        BufferedReader usersBuffer = new BufferedReader(new FileReader("data/Users.txt"));
        String line;
        User user = null;
        try {
            while ((line = usersBuffer.readLine()) != null) {
                String[] info = line.split("\\s+");
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
