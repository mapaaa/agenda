package com.mapa.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class AgendaCalendar extends JPanel {
    private DefaultTableModel model;
    private Calendar calendar;
    private JPanel monthYearPanel;
    private JLabel monthYearLabel;
    private String[] daysOfWeek = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

    public AgendaCalendar(JFrame parentFrame) {
        setBackground(new Color(76, 191, 160));
        calendar = new GregorianCalendar();
        LocalDateTime currentDate = LocalDateTime.now();
//        setBorder(new EmptyBorder(new Insets(300, 200, 300, 200)));
        setLayout(new BorderLayout());
        setVisible(true);

        // Month year
        monthYearPanel = new JPanel();
      //  monthYearPanel.setSize(new Dimension(100, 100));
        monthYearPanel.setLayout(new BorderLayout());
        JButton prev = new JButton("<");
        prev.addActionListener(e -> {
            calendar.add(Calendar.MONTH, -1);
            updateCalendar();
        });

        JButton next = new JButton(">");
        next.addActionListener(e -> {
            calendar.add(Calendar.MONTH, 1);
            updateCalendar();
        });

        monthYearLabel = new JLabel();
        monthYearLabel.setHorizontalAlignment(SwingConstants.CENTER);
        monthYearPanel.add(prev,BorderLayout.WEST);
        monthYearPanel.add(monthYearLabel,BorderLayout.CENTER);
        monthYearPanel.add(next,BorderLayout.EAST);
        model = new DefaultTableModel(null,daysOfWeek);
        JTable table = new JTable(model);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                if (row >= 0 && col >= 0) {
                    DayEntriesDialog entriesDialog = new DayEntriesDialog(parentFrame, getDate(model.getValueAt(row, col).toString()));
                    entriesDialog.setVisible(true);
                }
            }
        });
        JScrollPane pane = new JScrollPane(table);
        this.add(monthYearPanel,BorderLayout.NORTH);
        this.add(pane,BorderLayout.CENTER);
        updateCalendar();
    }

    private LocalDateTime getDate(String day) {
        return LocalDateTime.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, Integer.valueOf(day), 0, 0, 0);
    }

    private void updateCalendar() {
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ROOT);
        int year = calendar.get(Calendar.YEAR);
        monthYearLabel.setText(month + " " + year);
        int start = calendar.get(Calendar.DAY_OF_WEEK);
        int cntDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int cntWeeks = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);

        model.setRowCount(0);
        model.setRowCount(cntWeeks);

        int i;
        if (start >= 2) {
            i = start - 2;
        }
        else {
            i = 6;
            cntWeeks++;
            model.setRowCount(cntWeeks);
        }
        for(int d = 1; d <= cntDays; ++d){
            model.setValueAt(d, i / 7 , i % 7);
            i = i + 1;
        }
    }
}
