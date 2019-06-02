package com.mapa.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginRegister extends JPanel {

    public LoginRegister() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(new Insets(300, 200, 300, 200)));

        JLabel welcomeLabel = new JLabel("AGENDA");
        welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(45f));


        add(welcomeLabel);

        setBackground(new Color(76, 191, 160));
    }
}
