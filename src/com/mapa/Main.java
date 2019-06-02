package com.mapa;

import com.mapa.service.Application;
import com.mapa.ui.LoginDialog;
import com.mapa.ui.LoginRegister;
import com.mapa.ui.MainPage;
import com.mapa.ui.RegisterDialog;

import javax.swing.*;
import java.awt.*;

public class Main {
    private static Application application;
    private static Event loginEvent;

    public static void main(String[] args) {
        application = new Application();
        application.Start();
        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());

        // All ui panels
        LoginRegister loginRegister = new LoginRegister();
        MainPage mainPage = new MainPage();

        loginRegister.setVisible(true);
        mainPage.setVisible(false);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(loginButton.getFont().deriveFont(30f));
        loginButton.setPreferredSize(new Dimension(200, 100));
        JButton registerButton = new JButton("Register");
        registerButton.setFont(loginButton.getFont().deriveFont(30f));
        registerButton.setPreferredSize(new Dimension(200, 100));

        loginRegister.add(loginButton);
        loginRegister.add(registerButton);
        loginButton.addActionListener(e -> {
            LoginDialog loginDialog = new LoginDialog(mainFrame, application);
            loginDialog.setVisible(true);
            if (loginDialog.loginSuccessful()) {
                loginRegister.setVisible(false);
                mainPage.setVisible(true);
            }
        });
        registerButton.addActionListener(e -> {
            RegisterDialog registerDialog = new RegisterDialog(mainFrame, application);
            registerDialog.setVisible(true);
            if (registerDialog.registerSuccessful()) {
                loginRegister.setVisible(false);
                mainPage.setVisible(true);
            }
        });

        mainFrame.add(loginRegister, BorderLayout.CENTER);
        mainFrame.add(mainPage, BorderLayout.SOUTH);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}
