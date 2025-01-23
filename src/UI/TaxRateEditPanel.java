package UI;

import javax.swing.*;
import PD.Store;
import PD.TaxCategory;
import PD.TaxRate;
import java.awt.event.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TaxRateEditPanel extends JPanel
{
    private JTextField rateField;
    private JTextField dateField;

    public TaxRateEditPanel(JFrame currentFrame, JPanel currentPanel, Store store, TaxCategory taxCategory, TaxRate taxRate, Boolean isAdd)
    {
        setLayout(null);

        JLabel lblTaxRateEdit = new JLabel("Tax Rate Edit");
        lblTaxRateEdit.setHorizontalAlignment(SwingConstants.CENTER);
        lblTaxRateEdit.setBounds(12, 53, 776, 16);
        add(lblTaxRateEdit);

        JLabel lblRate = new JLabel("Rate:");
        lblRate.setBounds(65, 131, 56, 16);
        add(lblRate);

        JLabel lblEffectiveDate = new JLabel("Effective Date:");
        lblEffectiveDate.setBounds(65, 163, 83, 16);
        add(lblEffectiveDate);

        rateField = new JTextField(taxRate.getTaxRate().toString());
        rateField.setBounds(170, 129, 116, 22);
        add(rateField);
        rateField.setColumns(10);

        dateField = new JTextField(taxRate.getEffectiveDate().format(DateTimeFormatter.ofPattern("M/d/yyyy")));
        dateField.setBounds(170, 161, 116, 22);
        add(dateField);
        dateField.setColumns(10);

        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    taxRate.setTaxRate(new BigDecimal(rateField.getText()));
                    taxRate.setEffectiveDate(LocalDate.parse(dateField.getText(), DateTimeFormatter.ofPattern("M/d/yyyy")));

                    if (isAdd)
                    {
                        taxCategory.addTaxRate(taxRate);
                    }

                    currentFrame.getContentPane().removeAll();
                    currentFrame.getContentPane().add(currentPanel);

                    if (currentPanel instanceof TaxCategoryEditPanel)
                    {
                        ((TaxCategoryEditPanel) currentPanel).refreshTaxRateList();
                    }

                    currentFrame.getContentPane().revalidate();
                    currentFrame.getContentPane().repaint();
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(currentFrame, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnSave.setBounds(200, 445, 97, 25);
        add(btnSave);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                currentFrame.getContentPane().removeAll();
                currentFrame.getContentPane().add(currentPanel);
                currentFrame.getContentPane().revalidate();
                currentFrame.getContentPane().repaint();
            }
        });
        btnCancel.setBounds(497, 445, 97, 25);
        add(btnCancel);
    }
}
