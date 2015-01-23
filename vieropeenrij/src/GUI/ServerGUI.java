package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class ServerGUI extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;

	private static int SCALE = 4;
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
	
	private JLabel ipLabel;
	private JLabel portLabel;
	private JTextField ipTField;
	private JTextField portTField;
	private JButton startButton;
	private JButton stopButton;
	private JTextArea messageBox;
	
	public ServerGUI() {
		buildGUI();		
	}
	
	public void buildGUI() {
		Dimension dimension = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		Dimension labelSize = new Dimension(labelWIDTH * SCALE, labelHEIGHT * SCALE);
		Dimension textFieldSize = new Dimension(textFieldWIDTH * SCALE, textFieldHEIGHT * SCALE);
		Dimension buttonSize = new Dimension(buttonWIDTH * SCALE, buttonHEIGHT * SCALE);
		Dimension messageBoxSize = new Dimension(messageBoxWIDTH * SCALE, messageBoxHEIGHT * SCALE);

		setResizable(false);
		setLayout(null);
		setTitle("ConnectFour Server");
		setSize(dimension);
		
		ipLabel = new JLabel("Internet Adress:  ");
		portLabel = new JLabel("Port: ");
		
		ipTField= new JTextField(getIPAddress());
		ipTField.setEditable(false);
		portTField = new JTextField();
		
		startButton = new JButton("Start server");
		startButton.addActionListener(this);
		stopButton = new JButton("Stop Server");
		stopButton.setEnabled(false);
		stopButton.addActionListener(this);
		
		messageBox = new JTextArea();
		messageBox.setEditable(false);
		
		add(ipLabel);
		add(portLabel);
		add(ipTField);
		add(portTField);
		add(startButton);
		add(stopButton);
		add(messageBox);
		
		ipTField.setBounds((halfWIDTH - (textFieldWIDTH / 2)) * SCALE, SPACING * SCALE, textFieldSize.width, textFieldSize.height);
		ipLabel.setBounds((int)ipTField.getBounds().getMinX() - SPACING * SCALE - (labelWIDTH * SCALE), SPACING * SCALE, labelSize.width, labelSize.height);
		startButton.setBounds((int)ipTField.getBounds().getMaxX() + SPACING * SCALE, SPACING * SCALE, buttonSize.width, buttonSize.height);
		
		portTField.setBounds((halfWIDTH - (textFieldWIDTH / 2)) * SCALE, (int)ipTField.getBounds().getMaxY() + SPACING * SCALE, textFieldSize.width, textFieldSize.height);
		portLabel.setBounds((int)portTField.getBounds().getMinX() - SPACING * SCALE - (labelWIDTH * SCALE), (int)ipLabel.getBounds().getMaxY() + SPACING * SCALE, labelSize.width, labelSize.height);
		stopButton.setBounds((int)portTField.getBounds().getMaxX() + SPACING * SCALE, (int)startButton.getBounds().getMaxY() + SPACING * SCALE, buttonSize.width, buttonSize.height);
		
		messageBox.setBounds(SPACING * SCALE, (int)portTField.getBounds().getMaxY() + SPACING * SCALE, messageBoxSize.width, messageBoxSize.height);
		getContentPane().setBackground(new Color(183, 105, 211));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private String getIPAddress() {
		try  {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return "unkown";
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == startButton) {
			startButton.setEnabled(false);
			stopButton.setEnabled(true);
		}
		
		if(event.getSource() == stopButton) {
			startButton.setEnabled(true);
			stopButton.setEnabled(false);
		}
	}
	
	public static void main(String[] args) {
		new ServerGUI();
	}


}
