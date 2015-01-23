package GUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class ClientGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public static int SCALE = 4;
	
	public static int WIDTH = 320;
	public static int halfWIDTH = 160;
	public static int HEIGHT = WIDTH / 16 * 9;
	public static int halfHEIGHT = (WIDTH / 16 * 9) / 2;
	public static int quaterHEIGHT = (WIDTH / 16 * 9) / 4;
	
	private MainMenu menu;

	public ClientGUI() {
		menu = new MainMenu(this);
		buildGUI();
	}

	public void buildGUI() {
		Container c = getContentPane();
		
		Dimension dimension = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		
		setResizable(false);
		setLayout(null);
		setTitle("ConnectFour");
		setSize(dimension);
		
		menu.buildMenu();
		
		
		c.setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new ClientGUI();
	}
}
