package com.mapa.ui;

import com.mapa.model.CalendarEntry;
import com.mapa.model.Event;
import com.mapa.model.Reminder;
import com.mapa.service.AgendaManager;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class DayEntriesDialog extends JDialog {
    public DayEntriesDialog(JFrame parentFrame, LocalDateTime clickedDate) {
        setTitle(clickedDate.getDayOfMonth() + "/" + clickedDate.getMonth() + "/" + clickedDate.getYear());
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        // GRID
        cs.fill = GridBagConstraints.HORIZONTAL;
        List<CalendarEntry> allDayEntries = AgendaManager.getInstance().getAllDayCalendarEntriesFromThisDate(clickedDate);
        List<CalendarEntry> todayCalendarEntries = AgendaManager.getInstance().getAllCalendarEntriesFromThisDate(clickedDate);
        List<CalendarEntry> notStartedTodayCalendarEntries = AgendaManager.getInstance().getAllCalendarEntriesNotStartedInThisDate(clickedDate);

        todayCalendarEntries.sort(Comparator.comparing(CalendarEntry::getDate));
        allDayEntries.sort(Comparator.comparing(CalendarEntry::getDate));
        notStartedTodayCalendarEntries.sort(Comparator.comparing(CalendarEntry::getDate));

        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(new JLabel("All day:"), cs);

        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        DefaultListModel list1model = new DefaultListModel();
        for (var entry : allDayEntries) {
            if (Event.class.isInstance(entry)) {
                var event = (Event) entry;
                list1model.addElement("EVENT: " + event.getName() + " " + event.getLocation() + " " + event.getDescription());
            }
            if (Reminder.class.isInstance(entry)) {
                var reminder = (Reminder) entry;
                list1model.addElement("REMINDER: " + reminder.getName());
            }
        }
        JList list1 = new JList(list1model);
        list1.setLayoutOrientation(JList.VERTICAL);
        list1.setVisibleRowCount(-1);
        panel.add(list1, cs);

        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(new JLabel("Started earlier:"), cs);

        cs.gridx = 0;
        cs.gridy = 3;
        cs.gridwidth = 1;
        DefaultListModel list2model = new DefaultListModel();
        for (var entry : notStartedTodayCalendarEntries) {
            if (Event.class.isInstance(entry)) {
                var event = (Event) entry;
                list2model.addElement("EVENT: " + event.getName() + " " + event.getLocation() + " " + event.getDescription() + " " + event.getDateTime() + "->" + event.getEndDateTime());
            }
            if (Reminder.class.isInstance(entry)) {
                var reminder = (Reminder) entry;
                list2model.addElement("REMINDER: " + reminder.getName() + " at " + reminder.getDateTime());
            }
        }
        JList list2 = new JList(list2model);
        list2.setLayoutOrientation(JList.VERTICAL);
        list2.setVisibleRowCount(-1);
        panel.add(list2, cs);

        cs.gridx = 0;
        cs.gridy = 4;
        cs.gridwidth = 1;
        panel.add(new JLabel("Today:"), cs);

        cs.gridx = 0;
        cs.gridy = 5;
        cs.gridwidth = 1;
        DefaultListModel list3model = new DefaultListModel();
        for (var entry : todayCalendarEntries) {
            if (Event.class.isInstance(entry)) {
                var event = (Event) entry;
                list3model.addElement("EVENT: " + event.getName() + " " + event.getLocation() + " " + event.getDescription() + " " + event.getDateTime() + "->" + event.getEndDateTime());
            }
            if (Reminder.class.isInstance(entry)) {
                var reminder = (Reminder) entry;
                list3model.addElement("REMINDER: " + reminder.getName() + " at " + reminder.getDateTime());
            }
        }
        JList list3 = new JList(list3model);
        list3.setLayoutOrientation(JList.VERTICAL);
        list3.setVisibleRowCount(-1);
        panel.add(list3, cs);

        add(panel);

        pack();
        setLocationRelativeTo(parentFrame);
        panel.setBackground(new Color(76, 191, 160));
        setSize(new Dimension(600, 300));
        panel.setSize(new Dimension(500, 500));
    }
}
