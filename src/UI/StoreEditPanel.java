package UI;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import PD.Store;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class StoreEditPanel extends JPanel
{
    private JTextField textField;

    /**
     * Create the panel.
     */
    public StoreEditPanel(JFrame currentFrame, Store store)
    {
        setLayout(null);

        // Define constants for layout
        int labelX = 100;
        int fieldX = 220;
        int labelWidth = 100;
        int fieldWidth = 200;
        int labelHeight = 25;
        int fieldHeight = 25;
        int verticalSpacing = 30;
        int initialY = 130;

        JLabel lblStoreEdit = new JLabel("Store Edit");
        lblStoreEdit.setHorizontalAlignment(SwingConstants.CENTER);
        lblStoreEdit.setBounds(12, 53, 776, 25);
        add(lblStoreEdit);

        // Name Label
        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(labelX, initialY, labelWidth, labelHeight);
        add(lblName);

        // Name TextField
        textField = new JTextField(store.getName());
        textField.setBounds(fieldX, initialY, fieldWidth, fieldHeight);
        add(textField);
        textField.setColumns(10);

        // Save Button
        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                store.setName(textField.getText());
                currentFrame.getContentPane().removeAll();
                currentFrame.getContentPane().add(new POSHome(currentFrame, store));
                currentFrame.getContentPane().revalidate();
            }
        });
        btnSave.setBounds(200, 445, 97, 25);
        add(btnSave);

        // Cancel Button
        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                currentFrame.getContentPane().removeAll();
                currentFrame.getContentPane().add(new POSHome(currentFrame, store));
                currentFrame.getContentPane().revalidate();
            }
        });
        btnCancel.setBounds(497, 445, 97, 25);
        add(btnCancel);
    }
}
