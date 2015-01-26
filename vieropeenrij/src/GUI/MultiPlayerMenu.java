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
import javax.swing.text.DefaultCaret;

public class MultiPlayerMenu implements ActionListener, ItemListener {
	private static int SPACING = 4;
	
	private static int WIDTH = 320;
	private static int halfWIDTH = 160;
	private static int HEIGHT = WIDTH / 16 * 9;
	
	private static int labelWIDTH = 32;
	private static int labelHEIGHT = (labelWIDTH / 16 * 9) / 2;
	private static int widerLabelWIDTH = 48;
	
	private static int textFieldWIDTH = 32;
	private static int textFieldHEIGHT = (textFieldWIDTH / 16 * 9) / 2;
	
	private static int buttonWIDTH = 32;
	private static int buttonHEIGHT = (buttonWIDTH / 16 * 9) / 2; 
	
	private static int checkBoxWIDTH = 32;
	private static int checkBoxHEIGHT = (checkBoxWIDTH / 16 * 9) / 2; 
	
	private static int comboBoxWIDTH = 32;
	private static int comboBoxHEIGHT = (labelWIDTH / 16 * 9) / 2;
	
	private static int messageBoxWIDTH = ClientGUI.WIDTH - (2 * SPACING);
	private static int messageBoxHEIGHT = ClientGUI.HEIGHT - (8 * SPACING) - (3 * labelHEIGHT) - comboBoxHEIGHT;
	
	
	
	private MainMenu mainMenu;
	private Container c;
	private JFrame frame
	private BoardGUI boardGUI;
	
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
		Dimension widerLabelSize = new Dimension(widerLabelWIDTH * ClientGUI.SCALE, labelHEIGHT * ClientGUI.SCALE);
		Dimension textFieldSize = new Dimension(textFieldWIDTH * ClientGUI.SCALE, textFieldHEIGHT * ClientGUI.SCALE);
		Dimension buttonSize = new Dimension(buttonWIDTH * ClientGUI.SCALE, buttonHEIGHT * ClientGUI.SCALE);
		Dimension messageBoxSize = new Dimension(messageBoxWIDTH * ClientGUI.SCALE, messageBoxHEIGHT * ClientGUI.SCALE);
		Dimension checkBoxSize = new Dimension (checkBoxWIDTH * ClientGUI.SCALE, checkBoxHEIGHT * ClientGUI.SCALE);
		Dimension comboBoxSize = new Dimension (comboBoxWIDTH * ClientGUI.SCALE, comboBoxHEIGHT * ClientGUI.SCALE);
		
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
		AILabel = new JLabel("Computer Player:", SwingConstants.RIGHT);
		AILabel.setFont(menuFont);
		AILabel.setForeground(Color.RED);
		
		
		userTField = new JTextField();
		userTField.setBackground(Color.LIGHT_GRAY);
		ipTField= new JTextField();
		ipTField.setBackground(Color.LIGHT_GRAY);
		portTField = new JTextField();
		portTField.setBackground(Color.LIGHT_GRAY);
		
		AICheckBox = new JCheckBox("Use");
		AICheckBox.setBorderPainted(false);
		AICheckBox.setFont(menuFont);
		AICheckBox.setForeground(Color.RED);
		AICheckBox.setBackground(Color.BLACK);
		AICheckBox.addActionListener(this);
		AICheckBox.addItemListener(this);
		
		connectButton = new JButton("Connect");
		connectButton.addActionListener(this);
		connectButton.setBorderPainted(false);
		connectButton.setFont(menuFont);
		connectButton.setForeground(Color.RED);
		
		String[] possibleAI = {"Random Strategy", "Smart Strategy"};
		AIComboBox = new JComboBox<String>(possibleAI);
		Font comboBoxFont = new Font("Ariel", Font.BOLD, AIComboBox.getFont().getSize() + 1);
		AIComboBox.setEnabled(false);
		AIComboBox.setFont(comboBoxFont);
		AIComboBox.setBackground(Color.LIGHT_GRAY);
		AIComboBox.setForeground(Color.RED);
		AIComboBox.addActionListener(this);

		
		messageBox = new JTextArea();
		messageBox.setEditable(false);
		messageBox.setFont(menuFont);
		messageBox.setForeground(Color.RED);
		messageBox.setBackground(Color.LIGHT_GRAY);
		DefaultCaret caret = (DefaultCaret)messageBox.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
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
		
		AIComboBox.setBounds((halfWIDTH - (textFieldWIDTH / 2)) * ClientGUI.SCALE, (int)portTField.getBounds().getMaxY() + SPACING * ClientGUI.SCALE, comboBoxSize.width, comboBoxSize.height);
		AILabel.setBounds((int) AIComboBox.getBounds().getMinX() - SPACING * ClientGUI.SCALE - (widerLabelWIDTH * ClientGUI.SCALE), (int)portLabel.getBounds().getMaxY() + SPACING * ClientGUI.SCALE, widerLabelSize.width, widerLabelSize.height);
		AICheckBox.setBounds((int)AIComboBox.getBounds().getMaxX() + SPACING * ClientGUI.SCALE, (int)portLabel.getBounds().getMaxY() + SPACING * ClientGUI.SCALE, checkBoxSize.width, checkBoxSize.height);
		
		messageBox.setBounds(SPACING * ClientGUI.SCALE, (int)AIComboBox.getBounds().getMaxY() + SPACING * ClientGUI.SCALE, messageBoxSize.width, messageBoxSize.height);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == connectButton) {
			c.removeAll();
			c.repaint();
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
