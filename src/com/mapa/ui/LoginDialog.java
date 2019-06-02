package com.mapa.ui;

import com.mapa.service.Application;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginDialog  extends JDialog {
    private JTextField username;
    private JPasswordField password;
    private JButton loginButton;
    private JButton cancelButton;
    private boolean succeeded;

    public LoginDialog(JFrame parentFrame, Application app) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;

        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        JLabel email = new JLabel("Email: ");
        email.setForeground(Color.WHITE);
        panel.add(email, cs);

        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        username = new JTextField(20);
        panel.add(username, cs);

        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        JLabel pass = new JLabel("Password: ");
        pass.setForeground(Color.WHITE);
        panel.add(pass, cs);


        // (1, all line)

        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        password = new JPasswordField(20);
        panel.add(password, cs);

        panel.setBorder(new LineBorder(Color.GRAY));
        loginButton = new JButton("Login");

        loginButton.addActionListener(e -> {
            if (app.Login(getUsername(), getPassword())) {
                JOptionPane.showMessageDialog(LoginDialog.this,
                        "Hi " + getUsername() + "! You have successfully logged in.",
                        "Login",
                        JOptionPane.INFORMATION_MESSAGE);
                succeeded = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(LoginDialog.this,  "Invalid username or password",
                        "Login",
                        JOptionPane.ERROR_MESSAGE);
                // reset username and password
                username.setText("");
                password.setText("");
                succeeded = false;

            }
        });
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        JPanel bp = new JPanel();
        bp.add(loginButton);
        bp.add(cancelButton);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(parentFrame);
        panel.setBackground(new Color(76, 191, 160));
        bp.setBackground(new Color(76, 191, 160));
    }

    public String getUsername() {
        return username.getText().trim();
    }
    public String getPassword() {
        return new String(password.getPassword());
    }

    public boolean isSucceeded() {
        return succeeded;
    }
}
