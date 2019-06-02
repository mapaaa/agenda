package com.mapa.ui;

import com.mapa.service.Application;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class LoginDialog  extends JDialog {
    private JTextField email;
    private JPasswordField password;
    private JButton loginButton;
    private JButton cancelButton;
    private boolean succeeded;

    public LoginDialog(JFrame parentFrame, Application app) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        // GRID
        cs.fill = GridBagConstraints.HORIZONTAL;

        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        JLabel elabel = new JLabel("Email: ");
        elabel.setForeground(Color.WHITE);
        panel.add(elabel, cs);

        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        email = new JTextField(20);
        panel.add(email, cs);

        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        JLabel pass = new JLabel("Password: ");
        pass.setForeground(Color.WHITE);
        panel.add(pass, cs);


        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        password = new JPasswordField(20);
        panel.add(password, cs);

        panel.setBorder(new LineBorder(Color.GRAY));


        // Login Button
        loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            if (app.Login(getEmail(), getPassword())) {
                JOptionPane.showMessageDialog(LoginDialog.this,
                        "Welcome back, " + app.getFirstName() + "!", "Login", JOptionPane.INFORMATION_MESSAGE);
                succeeded = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(LoginDialog.this,  "Invalid email or password", "Login",  JOptionPane.ERROR_MESSAGE);
                email.setText("");
                password.setText("");
                succeeded = false;
            }
        });


        // Cancel Button
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

        // Buttons panel
        JPanel bp = new JPanel();
        bp.add(loginButton);
        bp.add(cancelButton);

        // Add panels in dialog
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(parentFrame);

        // Theme color
        panel.setBackground(new Color(76, 191, 160));
        bp.setBackground(new Color(76, 191, 160));
    }

    public String getEmail() {
        return email.getText().trim();
    }
    public String getPassword() {
        return new String(password.getPassword());
    }

    public boolean loginSuccessful() {
        return succeeded;
    }
}
