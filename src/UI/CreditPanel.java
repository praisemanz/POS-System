package UI;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import PD.Credit;
import PD.Sale;
import PD.Session;
import PD.Store;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox; // Import JComboBox
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;

public class CreditPanel extends JPanel
{
    private JTextField textField_1; // Amount
    private JTextField textField_2; // Account Number
    private JTextField textField_3; // Expire Date

    /**
     * Create the panel.
     */
    public CreditPanel(JFrame currentFrame, JPanel paymentPanel, Store store, Session session, Sale sale, Credit credit)
    {
        CreditPanel thisPanel = this;
        setLayout(null);

        JLabel lblCredit = new JLabel("Credit");
        lblCredit.setHorizontalAlignment(SwingConstants.CENTER);
        lblCredit.setBounds(12, 32, 376, 16);
        add(lblCredit);

        // Amount Label and TextField
        JLabel lblAmount = new JLabel("Amount:");
        lblAmount.setBounds(47, 61, 56, 16);
        add(lblAmount);

        textField_1 = new JTextField();
        textField_1.setBounds(125, 58, 116, 22);
        add(textField_1);
        textField_1.setColumns(10);

        // Card Type Label
        JLabel lblCardType = new JLabel("Card Type:");
        lblCardType.setBounds(47, 104, 64, 16);
        add(lblCardType);

        // Card Type ComboBox
        String[] cardTypes = {"Visa", "Mastercard", "American Express", "Discover"};
        JComboBox<String> cardTypeComboBox = new JComboBox<>(cardTypes);
        cardTypeComboBox.setBounds(125, 101, 116, 22);
        add(cardTypeComboBox);

        // Account Number Label and TextField
        JLabel lblAccount = new JLabel("Account #:");
        lblAccount.setBounds(47, 143, 64, 16);
        add(lblAccount);

        textField_2 = new JTextField();
        textField_2.setBounds(125, 140, 116, 22);
        add(textField_2);
        textField_2.setColumns(10);

        // Expire Date Label and TextField
        JLabel lblExpireDate = new JLabel("Expire Date:");
        lblExpireDate.setBounds(47, 185, 70, 16);
        add(lblExpireDate);

        textField_3 = new JTextField();
        textField_3.setBounds(125, 182, 116, 22);
        add(textField_3);
        textField_3.setColumns(10);

        JLabel lblMmddyyyy = new JLabel("MM/yyyy");
        lblMmddyyyy.setBounds(253, 185, 78, 16);
        add(lblMmddyyyy);

        // Save Button
        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                credit.setAmtTendered(textField_1.getText());
                credit.setCardType((String) cardTypeComboBox.getSelectedItem());
                credit.setAcctNumber(textField_2.getText());
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    LocalDate expireDate = LocalDate.parse(textField_3.getText(), formatter);
                    credit.setExpireDate(expireDate);
                } catch (Exception e) {
                    // Handle invalid date format
                    System.out.println("Invalid date format. Please use MM/dd/yyyy.");
                    return; // Exit the method without proceeding
                }
                sale.addPayment(credit);
                paymentPanel.remove(thisPanel);
                currentFrame.getContentPane().removeAll();
                currentFrame.getContentPane().add(paymentPanel);
                currentFrame.getContentPane().repaint();
            }
        });
        btnSave.setBounds(73, 240, 97, 25);
        add(btnSave);

        // Cancel Button
        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                paymentPanel.remove(thisPanel);
                currentFrame.getContentPane().removeAll();
                currentFrame.getContentPane().add(paymentPanel);
                currentFrame.getContentPane().repaint();
            }
        });
        btnCancel.setBounds(221, 240, 97, 25);
        add(btnCancel);
    }
}
