package com.mapa.ui;

import com.mapa.model.Category;
import com.mapa.service.AgendaManager;
import com.mapa.service.CategoryManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class CategoriesDialog extends JDialog {
    public CategoriesDialog(JFrame parentFrame) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        // GRID
        cs.fill = GridBagConstraints.HORIZONTAL;
        List<Category> categories = CategoryManager.getInstanceWhenLoggedIn().GetCategories();
        DefaultListModel listmodel = new DefaultListModel();
        for (var entry : categories) {
            listmodel.addElement(new UiItem(entry.getId(), "Category", entry.getLabel()));
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
        panel.setVisible(true);

        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list1.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent event)
            {
                int i = list1.locationToIndex(event.getPoint());
                UiItem item = (UiItem) list1.getModel().getElementAt(i);
                CategoryManager.getInstanceWhenLoggedIn().deleteCategory(item.getId());
                dispose();
            }
        });

        add(panel);
        setSize(new Dimension(600, 300));
        panel.setSize(new Dimension(500, 500));
    }
}
