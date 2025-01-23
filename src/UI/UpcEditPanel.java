package UI;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import PD.Item;
import  PD.Store;
import  PD.UPC;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class UpcEditPanel extends JPanel
{
	private JTextField textField;

	/**
	 * Create the panel.
	 * @param b 
	 * @param upc 
	 * @param item 
	 * @param currentPanel 
	 */
	public UpcEditPanel(JFrame currentFrame, JPanel currentPanel, Store store, Item item, UPC upc, boolean b)
	{
		setLayout(null);
		
		JLabel lblStoreEdit = new JLabel("UPC Edit");
		lblStoreEdit.setHorizontalAlignment(SwingConstants.CENTER);
		lblStoreEdit.setBounds(12, 53, 776, 16);
		add(lblStoreEdit);
		
		JLabel lblName = new JLabel("Code:");
		lblName.setBounds(102, 132, 56, 16);
		add(lblName);
		
		textField = new JTextField(UPC.getUPC());
		textField.setBounds(144, 129, 196, 25);
		add(textField);
		textField.setColumns(10);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				upc.setUPC(new String(textField.getText()));
				currentFrame.getContentPane().removeAll();
				currentFrame.getContentPane().add(currentPanel);
				currentFrame.getContentPane().repaint();
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
				currentFrame.getContentPane().repaint();
			}
		});
		btnCancel.setBounds(497, 445, 97, 25);
		add(btnCancel);

	}
}
