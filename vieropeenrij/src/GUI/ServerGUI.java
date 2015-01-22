package GUI;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ServerGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	public static int WIDTH = 320;
	public static int HEIGHT = WIDTH / 16 * 9;
	public static int SCALE = 4;
	public static int buttonWIDTH = 15;
	public static int buttonHEIGHT = buttonWIDTH / 16 * 9; 
	
	public ServerGUI() {
		buildGUI();		
	}
	
	public void buildGUI() {
		Dimension dimension = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		setLayout(null);
		
		JButton startButton = new JButton("Start");		
				
		add(startButton);
		startButton.setBounds(25, 25, startButton.getSize().width, startButton.getSize().height);
		setResizable(false);
		setTitle("ConnectFour Server");
		setSize(dimension);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new ServerGUI();
	}
}
