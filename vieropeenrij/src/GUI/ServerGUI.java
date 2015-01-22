package GUI;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class ServerGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 240;
	public static final int HEIGHT = WIDTH / 16 * 9;
	public static final int SCALE = 3;
	
	public ServerGUI() {
		
		Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		setSize(size);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new ServerGUI();

	}
}
