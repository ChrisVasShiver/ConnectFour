package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;

import netwerk.Server;

public class ServerGUI extends JFrame{
	private static final long serialVersionUID = 1L;

	private final static int SCALE = 4;
	private final static int SPACING = 4;
	
	private final static int WIDTH = 320;
	private final static int halfWIDTH = 160;
	private final static int HEIGHT = WIDTH / 16 * 9;

	private final static int labelWIDTH = 32;
	private final static int labelHEIGHT = (labelWIDTH / 16 * 9) / 2;
	
	private final static int textFieldWIDTH = 32;
	private final static int textFieldHEIGHT = (textFieldWIDTH / 16 * 9) / 2;
	
	private final static int buttonWIDTH = 32;
	private final static int buttonHEIGHT = (buttonWIDTH / 16 * 9) / 2; 
	
	private final static int scrollBarWIDTH = 4;
	
	private final static int messageBoxWIDTH = WIDTH - (2 * SPACING) - scrollBarWIDTH;
	private final static int messageBoxHEIGHT = 140;
	
	private JLabel ipLabel;
	private JLabel portLabel;
	private JTextField ipTField;
	private JTextField portTField;
	private JButton startButton;
	private JButton stopButton;
	private JTextArea messageBox;
	private JScrollPane scrollMessageBox;
	
	private Server server;
	private ServerController controller;
	
	private int port;

	
	public ServerGUI() {
		controller = new ServerController();
		buildGUI();		
		port = 0;
	}
	
	public void buildGUI() {
		Dimension dimension = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		Dimension labelSize = new Dimension(labelWIDTH * SCALE, labelHEIGHT * SCALE);
		Dimension textFieldSize = new Dimension(textFieldWIDTH * SCALE, textFieldHEIGHT * SCALE);
		Dimension buttonSize = new Dimension(buttonWIDTH * SCALE, buttonHEIGHT * SCALE);
		Dimension messageBoxSize = new Dimension(messageBoxWIDTH * SCALE, messageBoxHEIGHT * SCALE);
		Dimension scrollBarSize = new Dimension(scrollBarWIDTH * ClientGUI.SCALE ,messageBoxHEIGHT * SCALE);
		
		setResizable(false);
		setLayout(null);
		setTitle("ConnectFour Server");
		setSize(dimension);
		
		ipLabel = new JLabel("Host Adress:  ");
		portLabel = new JLabel("Port: ");
		
		ipTField= new JTextField(getIPAddress());
		ipTField.setEditable(false);
		portTField = new JTextField();
		
		startButton = new JButton("Start server");
		startButton.addActionListener(controller);
		stopButton = new JButton("Stop Server");
		stopButton.setEnabled(false);
		stopButton.addActionListener(controller);
		
		messageBox = new JTextArea();
		messageBox.setEditable(false);
		scrollMessageBox = new JScrollPane(messageBox);
		scrollMessageBox.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollMessageBox.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		add(ipLabel);
		add(portLabel);
		add(ipTField);
		add(portTField);
		add(startButton);
		add(stopButton);
		add(scrollMessageBox);

		
		ipTField.setBounds((halfWIDTH - (textFieldWIDTH / 2)) * SCALE, SPACING * SCALE, textFieldSize.width, textFieldSize.height);
		ipLabel.setBounds((int)ipTField.getBounds().getMinX() - SPACING * SCALE - (labelWIDTH * SCALE), SPACING * SCALE, labelSize.width, labelSize.height);
		startButton.setBounds((int)ipTField.getBounds().getMaxX() + SPACING * SCALE, SPACING * SCALE, buttonSize.width, buttonSize.height);
		
		portTField.setBounds((halfWIDTH - (textFieldWIDTH / 2)) * SCALE, (int)ipTField.getBounds().getMaxY() + SPACING * SCALE, textFieldSize.width, textFieldSize.height);
		portLabel.setBounds((int)portTField.getBounds().getMinX() - SPACING * SCALE - (labelWIDTH * SCALE), (int)ipLabel.getBounds().getMaxY() + SPACING * SCALE, labelSize.width, labelSize.height);
		stopButton.setBounds((int)portTField.getBounds().getMaxX() + SPACING * SCALE, (int)startButton.getBounds().getMaxY() + SPACING * SCALE, buttonSize.width, buttonSize.height);
		
		messageBox.setBounds(SPACING * SCALE, (int)portTField.getBounds().getMaxY() + SPACING * SCALE, messageBoxSize.width, messageBoxSize.height);
		scrollMessageBox.setBounds((SPACING * ClientGUI.SCALE), (int)messageBox.getBounds().getMinY(), messageBoxSize.width + scrollBarSize.width, scrollBarSize.height);
		
		getContentPane().setBackground(Color.GREEN);
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
	
	public void addMessage(String message) {
		messageBox.append(message + "\n");
	}
	
	public void startServer() {
		try {
			port = Integer.parseInt(portTField.getText());
		} catch (NumberFormatException e) {
			addMessage("<Error: Invalid Port!>");
			startButton.setEnabled(true);
			stopButton.setEnabled(false);
			portTField.setEnabled(true);
			return;
		}
		
		server = new Server(port);
		server.addObserver(controller);
		new Thread(server).start();
	}
	
	
	public static void main(String[] args) {
		new ServerGUI();
	}
	
	public class ServerController implements Observer, ActionListener {
		public ServerController() {
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getSource() == startButton) {
				startButton.setEnabled(false);
				portTField.setEnabled(false);
				stopButton.setEnabled(true);
				startServer();

			}
			
			if(event.getSource() == stopButton) {
				startButton.setEnabled(true);
				stopButton.setEnabled(false);
				portTField.setEnabled(true);
				server.stopConnections();
				addMessage("<Server has been shutdown>");
			}
		}
		@Override
		public void update(Observable o, Object arg) {
			assert o != null;
			assert arg != null;
			String notify = (String)arg;
			switch(notify) {
			case "SERVER_CREATED" : addMessage("<Listening on port: " + port + ">"); break;
			case "PORT_IN_USE" : addMessage("<ERROR: Port is already in use!>"); break;
			case "UNKOWN_SERVER_ERROR" : addMessage("<ERROR: Unkown Server error!>"); break;
			}
		}
			
	}
}
