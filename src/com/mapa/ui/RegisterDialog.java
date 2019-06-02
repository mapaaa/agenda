package com.mapa.ui;

import com.mapa.service.Application;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class RegisterDialog  extends JDialog {
    private JPasswordField password;
    private JButton loginButton;
    private JButton cancelButton;
    private boolean succeeded;
    private JTextField firstName;
    private JTextField lastName;
    private JTextField email;
    private JTextField birthDate;

    public RegisterDialog(JFrame parentFrame, Application app) {
        super(parentFrame, "Register", true);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        // GRID
        cs.fill = GridBagConstraints.HORIZONTAL;

        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        JLabel fnlabel = new JLabel("First name: ");
        fnlabel.setForeground(Color.WHITE);
        panel.add(fnlabel, cs);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 1;
        firstName = new JTextField(20);
        panel.add(firstName, cs);


        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        JLabel lnlabel = new JLabel("Last name: ");
        lnlabel.setForeground(Color.WHITE);
        panel.add(lnlabel, cs);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 1;
        lastName = new JTextField(20);
        panel.add(lastName, cs);


        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        JLabel blabel = new JLabel("Birth date (dd/mm/yyyy): ");
        blabel.setForeground(Color.WHITE);
        panel.add(blabel, cs);
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 1;
        birthDate = new JTextField(20);
        panel.add(birthDate, cs);


        cs.gridx = 0;
        cs.gridy = 3;
        cs.gridwidth = 1;
        JLabel elabel = new JLabel("Email: ");
        elabel.setForeground(Color.WHITE);
        panel.add(elabel, cs);
        cs.gridx = 1;
        cs.gridy = 3;
        cs.gridwidth = 1;
        email = new JTextField(20);
        panel.add(email, cs);


        cs.gridx = 0;
        cs.gridy = 4;
        cs.gridwidth = 1;
        JLabel pass = new JLabel("Password: ");
        pass.setForeground(Color.WHITE);
        panel.add(pass, cs);
        cs.gridx = 1;
        cs.gridy = 4;
        cs.gridwidth = 1;
        password = new JPasswordField(20);
        panel.add(password, cs);


        panel.setBorder(new LineBorder(Color.GRAY));


        // Login Button
        loginButton = new JButton("Register");
        loginButton.addActionListener(e -> {
            if (app.Register(getFirstName(), getLastName(), getBirthDate(), getEmail(), getPassword())) {
                JOptionPane.showMessageDialog(RegisterDialog.this,
                        "Welcome to your new agenda, " + app.getFirstName() + "!", "Register", JOptionPane.INFORMATION_MESSAGE);
                succeeded = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(RegisterDialog.this,  "Parsing form fields has failed!", "Register",  JOptionPane.ERROR_MESSAGE);
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

    public String getFirstName() {
        return firstName.getText().trim();
    }

    public String getLastName() {
        return lastName.getText().trim();
    }

    public String getBirthDate() {
        return birthDate.getText().trim();
    }

    public String getEmail() {
        return email.getText().trim();
    }
    public String getPassword() {
        return new String(password.getPassword());
    }

    public boolean registerSuccessful() {
        return succeeded;
    }
}
