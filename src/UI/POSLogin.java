package UI;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import PD.Cashier;
import PD.Register;
import PD.Sale;
import PD.Session;
import PD.Store;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

public class POSLogin extends JPanel
{
    private JTextField textField;
    private JPasswordField passwordField;
    JLabel lblPasswordIsInvalid;

    /**
     * Create the panel.
     */
    public POSLogin(JFrame currentFrame, Store store)
    {
        setLayout(null);
        
        // Define constants for layout
        int labelX = 246;
        int fieldX = 376;
        int labelWidth = 120;
        int fieldWidth = 160;
        int labelHeight = 16;
        int fieldHeight = 22;
        int rowSpacing = 45;
        int firstRowY = 148;
        
        JLabel lblLogin = new JLabel("Login");
        lblLogin.setBounds(348, 82, 56, 16);
        add(lblLogin);
        
        // Register Number Label and ComboBox
        JLabel lblRegister = new JLabel("Register Number:");
        lblRegister.setBounds(labelX, firstRowY, labelWidth, labelHeight);
        add(lblRegister);
        
        DefaultComboBoxModel<Register> registerModel = new DefaultComboBoxModel<>();
        for(Register register : store.getRegisters().values())
            registerModel.addElement(register);
            
        JComboBox<Register> comboBox = new JComboBox<Register>(registerModel);
        comboBox.setBounds(fieldX, firstRowY, fieldWidth, fieldHeight);
        add(comboBox);
        
        // Cashier Label and ComboBox
        JLabel lblCashier = new JLabel("Cashier:");
        lblCashier.setBounds(labelX, firstRowY + rowSpacing, labelWidth, labelHeight);
        add(lblCashier);
        
        DefaultComboBoxModel<Cashier> cashierModel = new DefaultComboBoxModel<>();
        for(Cashier cashier : store.getCashiers().values())
             cashierModel.addElement(cashier);
        
        JComboBox<Cashier> comboBox_1 = new JComboBox<Cashier>(cashierModel);
        comboBox_1.setBounds(fieldX, firstRowY + rowSpacing, fieldWidth, fieldHeight);
        add(comboBox_1);
        
        // Starting Cash Label and TextField
        JLabel lblStartingCash = new JLabel("Starting Cash:");
        lblStartingCash.setBounds(labelX, firstRowY + 2*rowSpacing, labelWidth, labelHeight);
        add(lblStartingCash);
        
        textField = new JTextField();
        textField.setBounds(fieldX, firstRowY + 2*rowSpacing, fieldWidth, fieldHeight);
        add(textField);
        textField.setColumns(10);
        
        // Password Label and PasswordField
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(labelX, firstRowY + 3*rowSpacing, labelWidth, labelHeight);
        add(lblPassword);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(fieldX, firstRowY + 3*rowSpacing, fieldWidth, fieldHeight);
        add(passwordField);
        
        // Invalid Password Label
        lblPasswordIsInvalid = new JLabel("Password is invalid!");
        lblPasswordIsInvalid.setBounds(fieldX, firstRowY + 3*rowSpacing + 30, 150, labelHeight);
        add(lblPasswordIsInvalid);
        lblPasswordIsInvalid.setVisible(false);
        
        // Login Button
        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                Cashier cashier = (Cashier)(comboBox_1.getSelectedItem());
                Register register = (Register)(comboBox.getSelectedItem());
                String password = new String(passwordField.getPassword()).trim();

                // Validate Cashier selection
                if (cashier == null) {
                    JOptionPane.showMessageDialog(currentFrame, "Please select a Cashier.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate Register selection
                if (register == null) {
                    JOptionPane.showMessageDialog(currentFrame, "Please select a Register.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate Password field
                if (password.isEmpty()) {
                    JOptionPane.showMessageDialog(currentFrame, "Password is required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate Starting Cash field
                String startingCashText = textField.getText().trim();
                if (startingCashText.isEmpty()) {
                    JOptionPane.showMessageDialog(currentFrame, "Starting Cash is required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate that Starting Cash is a valid number
                BigDecimal startingCash;
                try {
                    startingCash = new BigDecimal(startingCashText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(currentFrame, "Starting Cash must be a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Authorize cashier
                if(cashier.isAuthorized(password))
                {
                    register.getCashDrawer().setStartingCash(startingCash);
                    Session session = new Session(cashier, register, store);
                    currentFrame.getContentPane().removeAll();
                    currentFrame.getContentPane().add(new POSSale(currentFrame, store, session, new Sale()));
                    currentFrame.getContentPane().revalidate();
                }
                else
                {
                    lblPasswordIsInvalid.setVisible(true);
                }
            }
        });

        btnLogin.setBounds(200, 445, 97, 25);
        add(btnLogin);
        
        // Cancel Button
        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
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
