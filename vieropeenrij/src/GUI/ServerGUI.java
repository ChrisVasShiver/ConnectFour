package GUI;

import java.awt.Dimension;
import java.awt.Insets;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class ServerGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	public static int SCALE = 4;
	public static int SPACING = 4;
	
	public static int WIDTH = 320;
	public static int halfWIDTH = 160;
	public static int HEIGHT = WIDTH / 16 * 9;

	public static int labelWIDTH = 32;
	public static int labelHEIGHT = (labelWIDTH / 16 * 9) / 2;
	
	public static int textFieldWIDTH = 32;
	public static int textFieldHEIGHT = (textFieldWIDTH / 16 * 9) / 2;
	
	public static int buttonWIDTH = 32;
	public static int buttonHEIGHT = (buttonWIDTH / 16 * 9) / 2; 
	
	public static int messageBoxWIDTH = 320 - (2 * SPACING);
	public static int messageBoxHEIGHT = 140;
	
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
		
		JLabel ipLabel = new JLabel("Internet Adress:  ");
		JLabel portLabel = new JLabel("Port: ");
		
		JTextField ipTField= new JTextField(getIPAddress());
		ipTField.setEditable(false);
		JTextField portTField = new JTextField();
		
		JButton startButton = new JButton("Start server");	
		JButton stopButton = new JButton("Stop Server");
		
		JTextArea messageBox = new JTextArea();
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
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private String getIPAddress() {
		try  {
			InetAddress ipadress = InetAddress.getLocalHost();
			return ipadress.getHostAddress();
		} catch (UnknownHostException e) {
			return "unkown";
		}
	}
	
	public static void main(String[] args) {
		new ServerGUI();
	}
}
