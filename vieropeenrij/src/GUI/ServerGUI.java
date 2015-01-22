package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ServerGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	public static int WIDTH = 320;
	public static int HEIGHT = 320 / 16 * 9;
	public static int SCALE = 4;
	
	public ServerGUI() {
		buildGUI();		
	}
	
	public void buildGUI() {
		Dimension dimension = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		setLayout(new GridLayout(3, 3));
		
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel(new BorderLayout());
		
		JButton startServer = new JButton("Start", )
		
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
