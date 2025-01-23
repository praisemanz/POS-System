package UI;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import PD.Store;
import PD.Session;
import PD.Sale;
import PD.SaleLineItem;
import PD.Cashier;
import PD.Item;
import java.util.TreeMap;

import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CashierReportPanel extends JPanel {
    private JTextArea reportArea;
    private DatePicker datePicker;

    public CashierReportPanel(JFrame currentFrame, Store store) {
        setLayout(null);

        JLabel lblCashierReport = new JLabel("Cashier Report");
        lblCashierReport.setHorizontalAlignment(SwingConstants.CENTER);
        lblCashierReport.setBounds(100, 10, 600, 20);
        add(lblCashierReport);

        JLabel lblDate = new JLabel("Select Date:");
        lblDate.setBounds(150, 50, 100, 20);
        add(lblDate);

        // Create and configure the DatePicker
        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setAllowKeyboardEditing(false); // Disable manual input
        datePicker = new DatePicker(dateSettings);
        datePicker.setBounds(250, 50, 200, 30);
        add(datePicker);

        JButton btnGenerate = new JButton("Generate");
        btnGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateReport(store, datePicker.getDate().toString());
            }
        });
        btnGenerate.setBounds(200, 400, 150, 25);
        add(btnGenerate);

        reportArea = new JTextArea();
        reportArea.setBounds(50, 100, 600, 250);
        add(reportArea);

        JButton btnBack = new JButton("Close");
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentFrame.getContentPane().removeAll();
                currentFrame.getContentPane().add(new POSHome(currentFrame, store));
                currentFrame.getContentPane().revalidate();
            }
        });
        btnBack.setBounds(400, 400, 100, 25);
        add(btnBack);
    }




    private void generateReport(Store store, String dateStr)
    {
        try {
            // Parse the date
            LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE);

            // Map to store cashier and total sales amount
            Map<Cashier, BigDecimal> cashierSales = new HashMap<>();
            BigDecimal grandTotal = BigDecimal.ZERO;

            // Loop through sessions
            for (Session session : store.getSessions())
            {
                // Check if session date matches
                if (session.getStartDateTime().toLocalDate().equals(date))
                {
                    Cashier cashier = session.getCashier();
                    BigDecimal totalSales = cashierSales.getOrDefault(cashier, BigDecimal.ZERO);

                    // Loop through sales
                    for (Sale sale : session.getSales())
                    {
                        totalSales = totalSales.add(sale.calcTotal());
                        grandTotal = grandTotal.add(sale.calcTotal());
                    }

                    cashierSales.put(cashier, totalSales);
                }
            }

            // Generate report text
            StringBuilder report = new StringBuilder();
            report.append("Cashier Sales Report for ").append(date.toString()).append("\n\n");
            report.append(String.format("%-10s %-30s %-20s\n", "No.", "Name", "Amount"));
            //report.append("-------------------------------------------------------------\n");

            int cashierNumber = 1; // Start numbering cashiers from 1

            for (Map.Entry<Cashier, BigDecimal> entry : cashierSales.entrySet())
            {
                Cashier cashier = entry.getKey();
                BigDecimal totalSales = entry.getValue();
                report.append(String.format("%-10d %-30s $%-20s\n", cashierNumber, cashier.getPerson().getName(), totalSales.toString()));
                cashierNumber++;
            }

            //report.append("\n-------------------------------------------------------------\n");
            report.append(String.format("%-40s $%-20s\n", "Grand Total", grandTotal.toString()));

            reportArea.setText(report.toString());
        } catch (Exception e) {
            reportArea.setText("Error generating report. Please ensure the date is in YYYY-MM-DD format.");
        }
    }

}
