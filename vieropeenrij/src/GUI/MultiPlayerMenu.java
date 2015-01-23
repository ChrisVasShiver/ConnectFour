package GUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MultiPlayerMenu implements ActionListener, ItemListener {
	private static int SPACING = 4;
	
	private static int WIDTH = 320;
	private static int halfWIDTH = 160;
	private static int HEIGHT = WIDTH / 16 * 9;

	private static int labelWIDTH = 32;
	private static int labelHEIGHT = (labelWIDTH / 16 * 9) / 2;
	
	private static int textFieldWIDTH = 32;
	private static int textFieldHEIGHT = (textFieldWIDTH / 16 * 9) / 2;
	
	private static int buttonWIDTH = 32;
	private static int buttonHEIGHT = (buttonWIDTH / 16 * 9) / 2; 
	
	private static int messageBoxWIDTH = 320 - (2 * SPACING);
	private static int messageBoxHEIGHT = 140;
	
	private MainMenu mainMenu;
	private Container c;
	
	private JButton connectButton;
	private JButton backButton;
	private JCheckBox AICheckBox;
	private JComboBox<String> AIComboBox;
	private JLabel userLabel;
	private JLabel ipLabel;
	private JLabel portLabel;
	private JLabel AILabel;
	private JTextField userTField;
	private JTextField ipTField;
	private JTextField portTField;


	private JTextArea messageBox;
	
	public MultiPlayerMenu(JFrame frame, MainMenu mainMenu) {
		this.mainMenu = mainMenu;
		c = frame.getContentPane();
	}
	
	public void  buildMPMenu() {
		Dimension labelSize = new Dimension(labelWIDTH * ClientGUI.SCALE, labelHEIGHT * ClientGUI.SCALE);
		Dimension textFieldSize = new Dimension(textFieldWIDTH * ClientGUI.SCALE, textFieldHEIGHT * ClientGUI.SCALE);
		Dimension buttonSize = new Dimension(buttonWIDTH * ClientGUI.SCALE, buttonHEIGHT * ClientGUI.SCALE);
		Dimension messageBoxSize = new Dimension(messageBoxWIDTH * ClientGUI.SCALE, messageBoxHEIGHT * ClientGUI.SCALE);
		
		Font menuFont = new Font("Ariel", Font.BOLD, 16);
		
		userLabel = new JLabel("Username:", SwingConstants.RIGHT);
		userLabel.setFont(menuFont);
		userLabel.setForeground(Color.red);
		ipLabel = new JLabel("Internet Adress:", SwingConstants.RIGHT);
		ipLabel.setFont(menuFont);
		ipLabel.setForeground(Color.RED);
		portLabel = new JLabel("Port:", SwingConstants.RIGHT);
		portLabel.setFont(menuFont);;
		portLabel.setForeground(Color.RED);
		AILabel = new JLabel("Arifical Intelligence:", SwingConstants.RIGHT);
		AILabel.setFont(menuFont);
		AILabel.setForeground(Color.RED);
		
		
		userTField = new JTextField();
		userTField.setBackground(Color.GRAY);
		ipTField= new JTextField();
		ipTField.setBackground(Color.GRAY);
		portTField = new JTextField();
		portTField.setBackground(Color.GRAY);
		
		AICheckBox = new JCheckBox("Use");
		AICheckBox.setBorderPainted(false);
		AICheckBox.setFont(menuFont);
		AICheckBox.setForeground(Color.RED);
		AICheckBox.setBackground(Color.GRAY);
		AICheckBox.addActionListener(this);
		
		connectButton = new JButton("Connect");
		connectButton.addActionListener(this);
		connectButton.setBorderPainted(false);
		connectButton.setFont(menuFont);
		connectButton.setForeground(Color.RED);
		
		AIComboBox = new JComboBox<String>();
		AIComboBox.setEnabled(false);
		AIComboBox.setBackground(Color.GRAY);
		AIComboBox.setForeground(Color.RED);
		AIComboBox.addActionListener(this);
		
		messageBox = new JTextArea();
		messageBox.setEditable(false);
		messageBox.setFont(menuFont);
		messageBox.setForeground(Color.RED);
		messageBox.setBackground(Color.GRAY);
		
		backButton = new JButton("Back to Menu");
		backButton.addActionListener(this);
		backButton.setBorderPainted(false);
		backButton.setFont(menuFont);
		backButton.setForeground(Color.RED);
		
		c.add(userLabel);
		c.add(ipLabel);
		c.add(portLabel);
		c.add(AILabel);
		c.add(userTField);
		c.add(ipTField);
		c.add(portTField);
		c.add(AICheckBox);
		c.add(AIComboBox);
		c.add(connectButton);
		c.add(backButton);
		c.add(messageBox);
		
		backButton.setBounds(10, 10, buttonSize.width + 10, buttonSize.height);
		userTField.setBounds((halfWIDTH - (textFieldWIDTH / 2)) * ClientGUI.SCALE, SPACING * ClientGUI.SCALE, textFieldSize.width, textFieldSize.height);
		userLabel.setBounds((int)userTField.getBounds().getMinX() - SPACING * ClientGUI.SCALE - (labelWIDTH * ClientGUI.SCALE), SPACING * ClientGUI.SCALE, labelSize.width, labelSize.height);

		ipTField.setBounds((halfWIDTH - (textFieldWIDTH / 2)) * ClientGUI.SCALE, (int)userTField.getBounds().getMaxY() + SPACING * ClientGUI.SCALE, textFieldSize.width, textFieldSize.height);
		ipLabel.setBounds((int)ipTField.getBounds().getMinX() - SPACING * ClientGUI.SCALE - (labelWIDTH * ClientGUI.SCALE), (int)userLabel.getBounds().getMaxY() + SPACING * ClientGUI.SCALE, labelSize.width, labelSize.height);
		
		connectButton.setBounds((int)ipTField.getBounds().getMaxX() + SPACING * ClientGUI.SCALE, (int)userLabel.getBounds().getMaxY() + SPACING * ClientGUI.SCALE, buttonSize.width, buttonSize.height);
		
		portTField.setBounds((halfWIDTH - (textFieldWIDTH / 2)) * ClientGUI.SCALE, (int)ipTField.getBounds().getMaxY() + SPACING * ClientGUI.SCALE, textFieldSize.width, textFieldSize.height);
		portLabel.setBounds((int)portTField.getBounds().getMinX() - SPACING * ClientGUI.SCALE - (labelWIDTH * ClientGUI.SCALE), (int)ipLabel.getBounds().getMaxY() + SPACING * ClientGUI.SCALE, labelSize.width, labelSize.height);
		
	//	messageBox.setBounds(SPACING * ClientGUI.SCALE, (int)portTField.getBounds().getMaxY() + SPACING * ClientGUI.SCALE, messageBoxSize.width, messageBoxSize.height);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == connectButton) {
			
		} else if(event.getSource() == backButton) {
			c.removeAll();
			mainMenu.buildMenu();
			c.repaint();
		}		
	}

	@Override
	public void itemStateChanged(ItemEvent event) {
		if (event.getSource() == AICheckBox) {
			if(AICheckBox.isSelected()) {
				AIComboBox.setEnabled(true);
			} else {
				AIComboBox.setEnabled(false);
			}
		}
		
	}
}
