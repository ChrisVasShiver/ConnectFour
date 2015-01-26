package GUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import netwerk.Client;

public class MultiPlayerMenu implements ActionListener, ItemListener, DocumentListener {
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
	
	private static int scrollBarWIDTH = 4;
	
	private static int messageBoxWIDTH = ClientGUI.WIDTH - (2 * SPACING) - scrollBarWIDTH;
	private static int messageBoxHEIGHT = ClientGUI.HEIGHT - (8 * SPACING) - (3 * labelHEIGHT) - comboBoxHEIGHT;
	

	
	private boolean connectionSucces;
	
	private MainMenu mainMenu;
	private Container c;
	private JFrame frame;
	private BoardGUI boardGUI;
	private Client client;
	
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
	private JScrollPane scrollMessageBox;
	
	
	
	public MultiPlayerMenu(JFrame frame, MainMenu mainMenu) {
		this.mainMenu = mainMenu;
		this.frame = frame;
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
		Dimension scrollBarSize = new Dimension(scrollBarWIDTH * ClientGUI.SCALE ,messageBoxHEIGHT * ClientGUI.SCALE);
		
		
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
		userTField.getDocument().addDocumentListener(this);
		ipTField= new JTextField();
		ipTField.setBackground(Color.LIGHT_GRAY);
		ipTField.getDocument().addDocumentListener(this);
		portTField = new JTextField();
		portTField.setBackground(Color.LIGHT_GRAY);
		portTField.getDocument().addDocumentListener(this);
		
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
		connectButton.setEnabled(false);
		connectButton.setFont(menuFont);
		connectButton.setForeground(Color.RED);
		
		String[] possibleAI = {"Random Strategy"};
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
		scrollMessageBox = new JScrollPane(messageBox);
		scrollMessageBox.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollMessageBox.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
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
		c.add(scrollMessageBox);
		
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
		scrollMessageBox.setBounds((SPACING * ClientGUI.SCALE), (int)messageBox.getBounds().getMinY(), messageBoxSize.width + scrollBarSize.width, scrollBarSize.height);
	}
	
	public void addMessage(String message) {
		messageBox.append(message + "\n");
	}
	
	public void connect() {
		int port = 0;
		String IPadressstr = ipTField.getText();
		InetAddress ipadress;
		String name;
		connectionSucces = true;
		
		try {
			port = Integer.parseInt(portTField.getText());
		} catch (NumberFormatException e) {
			addMessage("<Error: Invalid port:" + port + "!>");
			connectionSucces = false;
			return;
		}
		
		if(AICheckBox.isSelected()) {
			String AIname;
			switch((String)AIComboBox.getSelectedItem()) {
				case "Random Strategy" : AIname = "Random";
					break;
				default:
					AIname = "Random";
				
			}
			name = "AI_" + AIname + "_" + userTField.getText();
		} else {
			name = "Player_" + userTField.getHeight();
		}
		 
		 try {
			 ipadress = InetAddress.getByName(IPadressstr);
			 client = new Client(ipadress, port, name);
			 client.start();
		 } catch(UnknownHostException e) {
			 addMessage("<Error: Making Connection failed!>");
			 connectionSucces = false;
			 return;
		 }
	}
		
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == connectButton) {
			addMessage("<Message: Trying to connect, please wait!>");
			connectButton.setEnabled(false);
			connect();
			if(connectionSucces && client.getClientRunning()) {
 			c.removeAll();
			boardGUI = new BoardGUI(frame, client);
			boardGUI.buildBoardGUI();
			c.repaint();
			} else {
				addMessage("<Error: Making Connection failed!>");
				connectButton.setEnabled(true);
			}
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

	@Override
	public void changedUpdate(DocumentEvent event) {
		updatedTF();
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		updatedTF();
		
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		updatedTF();
	}
	
	public void updatedTF() {
		if(userTField.getText().equals("") || portTField.getText().equals("") || ipTField.getText().equals("")) {
			connectButton.setEnabled(false);
		} else {
			connectButton.setEnabled(true);
		}
	}
}
