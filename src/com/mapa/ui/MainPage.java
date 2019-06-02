package com.mapa.ui;

import javax.swing.*;
import java.awt.*;

public class MainPage extends JPanel {
    public MainPage(JFrame parentFrame) {
        setLayout(new BorderLayout());
        AgendaCalendar agendaCalendar = new AgendaCalendar(parentFrame);

        add(agendaCalendar, BorderLayout.NORTH);
        setBackground(new Color(76, 191, 160));

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;

        cs.gridwidth = 1;
        cs.gridx = 0;
        cs.gridy = 0;
        JButton notesBtn = new JButton("Notes");
        panel.add(notesBtn, cs);

        cs.gridx = 1;
        cs.gridy = 0;
        JButton tasksBtn = new JButton("Tasks");
        panel.add(tasksBtn, cs);

        cs.gridx = 0;
        cs.gridy = 1;
        JButton categoryBtn = new JButton("Categories");
        panel.add(categoryBtn, cs);

        cs.gridx = 1;
        cs.gridy = 1;
        JButton addEventBtn = new JButton("Add Event");
        panel.add(addEventBtn, cs);

        cs.gridx = 2;
        cs.gridy = 1;
        JButton addReminder = new JButton("Add Reminders");
        panel.add(addReminder, cs);
        panel.setVisible(true);
        panel.setBackground(new Color(76, 191, 160));

        add(panel, BorderLayout.CENTER);
    }
}
