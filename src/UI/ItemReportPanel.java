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

public class ItemReportPanel extends JPanel {
    private JTextArea reportArea;
    private DatePicker datePicker;

    public ItemReportPanel(JFrame currentFrame, Store store) {
        setLayout(null);

        JLabel lblCashierReport = new JLabel("Item Report");
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

            // Initialize a map to store item numbers and their total quantities sold
            Map<String, Integer> itemSales = new TreeMap<>(); // Sorted order by item number

            // Prepopulate the map with all items from the store, initializing their quantities to 0
            for (Item item : store.getItems().values()) {
                itemSales.put(item.getNumber(), 0); // Initialize each item with 0 quantity
            }

            // Iterate through sessions to calculate quantities sold on the specified date
            for (Session session : store.getSessions()) {
                if (session.getStartDateTime().toLocalDate().equals(date)) {
                    for (Sale sale : session.getSales()) {
                        for (SaleLineItem lineItem : sale.getSaleLineItems()) {
                            String itemNumber = lineItem.getItem().getNumber();
                            int quantitySold = lineItem.getQuantity();

                            // Update the total quantity sold for the item
                            itemSales.put(itemNumber, itemSales.getOrDefault(itemNumber, 0) + quantitySold);
                        }
                    }
                }
            }

            // Generate the report
            StringBuilder report = new StringBuilder();
            report.append("Item Report for: ").append(date.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"))).append("\n\n");

            // Iterate through the item map and append the data to the report
            for (Map.Entry<String, Integer> entry : itemSales.entrySet()) {
                String itemNumber = entry.getKey();
                int quantity = entry.getValue();
                Item item = store.findItemForNumber(itemNumber);
                String itemName = (item != null) ? item.getDescription() : "Unknown Item";

                // Format the report line
                report.append(String.format("%-5s %-25s %d\n", itemNumber, itemName, quantity));
            }

            // Set the report text in the report area
            reportArea.setText(report.toString());
        } catch (Exception e) {
            reportArea.setText("Error generating report. Please check the date format and ensure data is available.");
            e.printStackTrace(); // For debugging purposes
        }
    }



}
