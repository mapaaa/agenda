package com.mapa.ui;

import com.mapa.model.Event;
import com.mapa.model.Reminder;
import com.mapa.service.AgendaManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class RemindersDialog extends JDialog {
    public RemindersDialog(JFrame parentFrame) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        // GRID
        cs.fill = GridBagConstraints.HORIZONTAL;
        List<Reminder> events = AgendaManager.getInstance().getAllReminders();
        DefaultListModel listmodel = new DefaultListModel();
        for (Reminder reminder : events) {
            if (reminder.getAllDay()){
                listmodel.addElement(new UiItem(reminder.getId(), "Reminder", reminder.getName()));
            }
            else {
                listmodel.addElement(new UiItem(reminder.getId(), "Reminder",reminder.getName() + " " + reminder.getDateTime()));
            }
        }
        JList list1 = new JList(listmodel);
        list1.setLayoutOrientation(JList.VERTICAL);
        list1.setVisibleRowCount(-1);
        cs.gridx = 0;
        cs.gridwidth = 1;
        cs.gridy = 0;
        panel.add(list1, cs);
        pack();
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list1.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent event)
            {
                int i = list1.locationToIndex(event.getPoint());
                UiItem item = (UiItem) list1.getModel().getElementAt(i);
                AgendaManager.getInstance().deleteReminder(item.getId());
                dispose();
            }
        });
        setResizable(false);
        setLocationRelativeTo(parentFrame);
        panel.setBackground(new Color(76, 191, 160));
        panel.setVisible(true);
        add(panel);
        setSize(new Dimension(600, 300));
        panel.setSize(new Dimension(500, 500));
    }
}
