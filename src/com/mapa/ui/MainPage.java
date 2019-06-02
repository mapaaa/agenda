package com.mapa.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        notesBtn.addActionListener(e -> {
            NotesDialog dialog = new NotesDialog(parentFrame);
            dialog.setVisible(true);
        });
        panel.add(notesBtn, cs);

        cs.gridx = 1;
        cs.gridy = 0;
        JButton tasksBtn = new JButton("Tasks");
        tasksBtn.addActionListener(e -> {
            TasksDialog dialog = new TasksDialog(parentFrame);
            dialog.setVisible(true);
        });
        panel.add(tasksBtn, cs);

        cs.gridx = 0;
        cs.gridy = 1;
        JButton categoryBtn = new JButton("Categories");
        categoryBtn.addActionListener(e -> {
            CategoriesDialog dialog = new CategoriesDialog(parentFrame);
            dialog.setVisible(true);
        });
        panel.add(categoryBtn, cs);

        cs.gridx = 1;
        cs.gridy = 1;
        JButton addEventBtn = new JButton("Events");
        addEventBtn.addActionListener(e -> {
            EventsDialog dialog = new EventsDialog(parentFrame);
            dialog.setVisible(true);
        });
        panel.add(addEventBtn, cs);

        cs.gridx = 2;
        cs.gridy = 1;
        JButton addReminder = new JButton("Reminders");
        addReminder.addActionListener(e -> {
            RemindersDialog dialog = new RemindersDialog(parentFrame);
            dialog.setVisible(true);
        });
        panel.add(addReminder, cs);
        panel.setVisible(true);
        panel.setBackground(new Color(76, 191, 160));

        add(panel, BorderLayout.CENTER);
    }
}
