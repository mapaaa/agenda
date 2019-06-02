package com.mapa.ui;

import com.mapa.model.Category;
import com.mapa.service.CategoryManager;

import javax.swing.*;
import javax.swing.border.LineBorder;
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
                Thread t = new Thread(() -> CategoryManager.getInstanceWhenLoggedIn().deleteCategory(item.getId()));
                t.start();
                dispose();
            }
        });

        cs.gridx = 0;
        cs.gridy = 1;
        JButton newElement = new JButton("Add");
        newElement.addActionListener(e -> {
            CategoryFormDialog dialog = new CategoryFormDialog(parentFrame);
            dialog.setVisible(true);
            if (dialog.getDone()) {
                 listmodel.addElement(new UiItem(dialog.getId(), "Category", dialog.getLabel()));
            }
        });
        panel.add(newElement, cs);

        add(panel);
        setSize(new Dimension(600, 300));
        panel.setSize(new Dimension(500, 500));
    }

    class CategoryFormDialog extends JDialog {
        private JTextField label;
        private JButton saveButton;
        private boolean done = false;
        private int id;

        CategoryFormDialog(JFrame parent) {
            super(parent, "New Category", true);
            setLocationRelativeTo(parent);
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints cs = new GridBagConstraints();

            // GRID
            cs.fill = GridBagConstraints.HORIZONTAL;

            cs.gridx = 0;
            cs.gridy = 0;
            cs.gridwidth = 1;
            JLabel elabel = new JLabel("Label: ");
            elabel.setForeground(Color.WHITE);
            panel.add(elabel, cs);

            cs.gridx = 1;
            cs.gridy = 0;
            cs.gridwidth = 2;
            label = new JTextField(20);
            panel.add(label, cs);

            saveButton = new JButton("Save");
            saveButton.addActionListener(e -> {
                Thread t = new Thread(() -> id = CategoryManager.getInstanceWhenLoggedIn().AddCategoryAndGetId(getLabel()));
                t.start();
                try {
                    t.join();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                done = true;
                dispose();
                    });
            JPanel bp = new JPanel();
            bp.add(saveButton);

            panel.setBorder(new LineBorder(Color.GRAY));
            setResizable(false);
            getContentPane().add(panel, BorderLayout.CENTER);
            getContentPane().add(bp, BorderLayout.PAGE_END);

            // Theme color
            panel.setBackground(new Color(76, 191, 160));
            bp.setBackground(new Color(76, 191, 160));
            pack();
        }

        private String getLabel() {
            return label.getText().trim();
        }

        public boolean getDone() {
            return done;
        }

        private int getId() {
            return id;
        }
    }
}
