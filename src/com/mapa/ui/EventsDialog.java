package com.mapa.ui;

import com.mapa.model.Event;
import com.mapa.service.AgendaManager;
import com.mapa.service.CategoryManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class EventsDialog extends JDialog {
    public EventsDialog(JFrame parentFrame) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        // GRID
        cs.fill = GridBagConstraints.HORIZONTAL;
        List<Event> events = AgendaManager.getInstance().getAllEvents();
        DefaultListModel listmodel = new DefaultListModel();
        for (Event event : events) {
            if (event.getAllDay()){
                listmodel.addElement(new UiItem(event.getId(), "Event", event.getName() + " " + event.getLocation() + " " + event.getDescription()));
            }
            else {
                listmodel.addElement(new UiItem(event.getId(), "Event",event.getName() + " " + event.getLocation() + " " + event.getDescription() + " " + event.getDateTime() + "->" + event.getEndDateTime()));
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
        setResizable(false);
        setLocationRelativeTo(parentFrame);
        panel.setBackground(new Color(76, 191, 160));

        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list1.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent event)
            {
                int i = list1.locationToIndex(event.getPoint());
                UiItem item = (UiItem) list1.getModel().getElementAt(i);
                AgendaManager.getInstance().deleteEvent(item.getId());
                dispose();
            }
        });

        panel.setVisible(true);
        add(panel);
        setSize(new Dimension(600, 300));
        panel.setSize(new Dimension(500, 500));
    }
}
