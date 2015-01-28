package GUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
/**
 * The Window(JFrame) from the client;
 * @author C. Visscher and D. Ye
 * @version 25-01-2015
 */
public class ClientGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public final static int SCALE = 4;
	
	public final static int WIDTH = 320;
	public final static int halfWIDTH = 160;
	public final static int HEIGHT = WIDTH / 16 * 9;
	public final static int halfHEIGHT = (WIDTH / 16 * 9) / 2;
	public final static int quaterHEIGHT = (WIDTH / 16 * 9) / 4;
	
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
