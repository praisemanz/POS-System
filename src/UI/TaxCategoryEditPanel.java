package UI;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import PD.Store;
import PD.TaxCategory;
import PD.TaxRate;
import java.awt.event.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TaxCategoryEditPanel extends JPanel
{
    private static final long serialVersionUID = 1L;

    private JButton btnEdit;
    private JButton btnDelete;
    private DefaultListModel<TaxRate> listModel;
    private JList<TaxRate> list;
    private JTextField textField;

    private JPanel currentPanel = this;
    private TaxCategory taxCategory;

    public TaxCategoryEditPanel(JFrame currentFrame, Store store, TaxCategory taxCategory, Boolean isAdd)
    {
        this.taxCategory = taxCategory;
        setLayout(null);

        JLabel lblEditTaxCategory = new JLabel("Edit Tax Category");
        lblEditTaxCategory.setHorizontalAlignment(SwingConstants.CENTER);
        lblEditTaxCategory.setBounds(12, 53, 776, 16);
        add(lblEditTaxCategory);

        JLabel lblCategory = new JLabel("Category:");
        lblCategory.setBounds(102, 132, 56, 16);
        add(lblCategory);

        textField = new JTextField(taxCategory.getCategory());
        textField.setBounds(181, 129, 116, 22);
        add(textField);
        textField.setColumns(10);

        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                taxCategory.setCategory(textField.getText());
                if (isAdd) store.addTaxCategory(taxCategory);
                currentFrame.getContentPane().removeAll();
                currentFrame.getContentPane().add(new TaxCategoryListPanel(currentFrame, store));
                currentFrame.getContentPane().revalidate();
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
                currentFrame.getContentPane().add(new TaxCategoryListPanel(currentFrame, store));
                currentFrame.getContentPane().revalidate();
            }
        });
        btnCancel.setBounds(497, 445, 97, 25);
        add(btnCancel);

        // Initialize the list model and list
        listModel = new DefaultListModel<TaxRate>();
        list = new JList<TaxRate>(listModel);
        refreshTaxRateList(); // Populate the list model

        // Create and set up the scroll pane
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(497, 130, 258, 190);
        add(scrollPane);

        JLabel lblTaxRates = new JLabel("Tax Rates:");
        lblTaxRates.setBounds(497, 97, 72, 16);
        add(lblTaxRates);

        JButton btnAdd = new JButton("Add");
        btnAdd.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                currentFrame.getContentPane().removeAll();
                currentFrame.getContentPane().add(new TaxRateEditPanel(currentFrame, currentPanel, store, taxCategory, new TaxRate(), true));
                currentFrame.getContentPane().revalidate();
            }
        });
        btnAdd.setBounds(497, 347, 72, 25);
        add(btnAdd);

        btnEdit = new JButton("Edit");
        btnEdit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                currentFrame.getContentPane().removeAll();
                currentFrame.getContentPane().add(new TaxRateEditPanel(currentFrame, currentPanel, store, taxCategory, list.getSelectedValue(), false));
                currentFrame.getContentPane().revalidate();
            }
        });
        btnEdit.setBounds(589, 347, 72, 25);
        btnEdit.setEnabled(false);
        add(btnEdit);

        btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                // Remove from taxCategory
                taxCategory.removeTaxRate(list.getSelectedValue());

                // Remove from list model
                listModel.removeElement(list.getSelectedValue());

                // Clear selection and disable buttons
                list.clearSelection();
                btnDelete.setEnabled(false);
                btnEdit.setEnabled(false);

                // Refresh the list
                list.revalidate();
                list.repaint();
            }
        });
        btnDelete.setBounds(683, 347, 72, 25);
        btnDelete.setEnabled(false);
        add(btnDelete);

        // Add the list selection listener
        list.addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e)
            {
                if (!e.getValueIsAdjusting() && list.getSelectedValue() != null)
                {
                    btnEdit.setEnabled(true);

                    if (list.getSelectedValue().isUsed())
                        btnDelete.setEnabled(false);
                    else
                        btnDelete.setEnabled(true);
                }
                else
                {
                    btnEdit.setEnabled(false);
                    btnDelete.setEnabled(false);
                }
            }
        });
    }

    public void refreshTaxRateList()
    {
        // Clear the list model
        listModel.clear();

        // Repopulate the list model with updated Tax Rates
        for (TaxRate taxRate : taxCategory.getTaxRates())
        {
            listModel.addElement(taxRate);
        }

        // Refresh the list to display the updated data
        list.revalidate();
        list.repaint();
    }
}
