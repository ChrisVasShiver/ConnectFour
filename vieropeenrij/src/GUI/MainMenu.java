package GUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

class MainMenu implements ActionListener {
	
	private static int SPACING = 64;
	
	private static int buttonWIDTH = 128;
	private static int buttonHEIGHT = (buttonWIDTH / 16 * 9) / 2;

	private static int titleWIDTH = 128;
	private static int titleHEIGHT = (titleWIDTH / 16 * 9) / 2;
	
	private Container c;
	private JFrame frame;
	
	private JLabel title;
	private JButton singlePlayer;
	private JButton multiPlayer;
	private JButton credits;
	
	public MainMenu(JFrame frame) {
		this.frame = frame;
		c = frame.getContentPane();
	}
	
	public void buildMenu() {
		Dimension buttonSize = new Dimension(buttonWIDTH * ClientGUI.SCALE, buttonHEIGHT * ClientGUI.SCALE);
		Dimension titleSize = new Dimension(titleWIDTH * ClientGUI.SCALE, titleHEIGHT * ClientGUI.SCALE);
		Font titleFont = new Font("Ariel", Font.BOLD, 64);
		Font menuFont = new Font("Ariel", Font.BOLD, 32);
		
		title = new JLabel("Connect Four", SwingConstants.CENTER);
		title.setFont(titleFont);
		title.setForeground(Color.red);
		
		singlePlayer = new JButton("Singleplayer");
		singlePlayer.addActionListener(this);
		singlePlayer.setFont(menuFont);
		singlePlayer.setForeground(Color.RED);
		singlePlayer.setBorderPainted(false);
		
		multiPlayer = new JButton("Multiplayer");
		multiPlayer.addActionListener(this);
		multiPlayer.setFont(menuFont);
		multiPlayer.setForeground(Color.RED);
		multiPlayer.setBorderPainted(false);
		
		credits = new JButton("Credits");
		credits.addActionListener(this);
		credits.setFont(menuFont);
		credits.setForeground(Color.red);
		credits.setBorderPainted(false);
		
		c.add(title);
		c.add(singlePlayer);
		c.add(multiPlayer);
		c.add(credits);
		
		
		singlePlayer.setBounds((ClientGUI.halfWIDTH - (buttonWIDTH / 2)) * ClientGUI.SCALE, (ClientGUI.quaterHEIGHT - (buttonHEIGHT /2)) * ClientGUI.SCALE, buttonSize.width, buttonSize.height);
		
		multiPlayer.setBounds((ClientGUI.halfWIDTH - (buttonWIDTH / 2)) * ClientGUI.SCALE, (ClientGUI.halfHEIGHT - (buttonHEIGHT /2)) * ClientGUI.SCALE, buttonSize.width, buttonSize.height);
		
		credits.setBounds((ClientGUI.halfWIDTH - (buttonWIDTH / 2)) * ClientGUI.SCALE, ((ClientGUI.halfHEIGHT + ClientGUI.quaterHEIGHT) - (buttonHEIGHT / 2)) * ClientGUI.SCALE, buttonSize.width, buttonSize.height);
		
		title.setBounds((ClientGUI.halfWIDTH - (titleWIDTH / 2)) * ClientGUI.SCALE, (int)singlePlayer.getBounds().getMinY() - buttonSize.height, titleSize.width, titleSize.height);
	}
	

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == singlePlayer) {
			c.removeAll();
			c.repaint();
		} else if (event.getSource() == multiPlayer) {
			c.removeAll();
			c.repaint();
		} else if (event.getSource() == credits) {
			c.removeAll();
			c.repaint();
		}
		
	}
}
