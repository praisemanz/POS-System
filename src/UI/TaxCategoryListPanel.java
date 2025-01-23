package UI;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import PD.Store;
import PD.TaxCategory;
import java.awt.event.*;

public class TaxCategoryListPanel extends JPanel
{
    private JButton btnDelete;
    private JButton btnEdit;
    private JList<TaxCategory> list;
    private DefaultListModel<TaxCategory> listModel;

    public TaxCategoryListPanel(JFrame currentFrame, Store store)
    {
        setLayout(null);

        // Create and populate the list model
        listModel = new DefaultListModel<TaxCategory>();
        for (TaxCategory taxCat : store.getTaxCategories().values())
        {
            listModel.addElement(taxCat);
        }

        // Create the JList with the model
        list = new JList<TaxCategory>(listModel);

        // Create the JScrollPane with the list
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(104, 65, 242, 126);
        add(scrollPane);

        // Create and add labels, buttons, etc.
        JLabel lblTaxCategoriesList = new JLabel("Tax Categories List");
        lblTaxCategoriesList.setBounds(-189, 25, 776, 16);
        lblTaxCategoriesList.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTaxCategoriesList);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(307, 234, 85, 21);
        btnDelete.setEnabled(false);
        add(btnDelete);

        btnEdit = new JButton("Edit");
        btnEdit.setBounds(187, 234, 85, 21);
        btnEdit.setEnabled(false);
        add(btnEdit);

        // Add action listeners for the buttons

        btnDelete.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                // Remove from store
                store.removeTaxCategory(list.getSelectedValue());

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

        btnEdit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                currentFrame.getContentPane().removeAll();
                currentFrame.getContentPane().add(new TaxCategoryEditPanel(currentFrame, store, list.getSelectedValue(), false));
                currentFrame.getContentPane().revalidate();
            }
        });

        // Add the list selection listener

        list.addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e)
            {
                btnEdit.setEnabled(false);
                btnDelete.setEnabled(false);

                if (!e.getValueIsAdjusting() && list.getSelectedValue() != null)
                {
                    btnEdit.setEnabled(true);

                    if (list.getSelectedValue().isUsed())
                        btnDelete.setEnabled(false);
                    else
                        btnDelete.setEnabled(true);
                }
            }
        });

        // Add the Add button
        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(74, 232, 97, 25);
        btnAdd.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                currentFrame.getContentPane().removeAll();
                currentFrame.getContentPane().add(new TaxCategoryEditPanel(currentFrame, store, new TaxCategory(), true));
                currentFrame.getContentPane().revalidate();
            }
        });
        add(btnAdd);
    }
}
