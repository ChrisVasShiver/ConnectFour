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
	
	private final static int SPACING = 64;
	
	private final static int buttonWIDTH = 128;
	private final static int buttonHEIGHT = (buttonWIDTH / 16 * 9) / 2;

	private final static int titleWIDTH = 128;
	private final static int titleHEIGHT = (titleWIDTH / 16 * 9) / 2;
	
	private Container c;
	private JFrame frame;
	
	private JLabel title;
	private JButton multiPlayer;
	private JButton credits;
	
	private MultiPlayerMenu mpmenu;
	private CreditsMenu creditsmenu;
	
	public MainMenu(JFrame frame) {
		this.frame = frame;
		c = frame.getContentPane();
		mpmenu = new MultiPlayerMenu(frame, this);
		creditsmenu = new CreditsMenu(frame, this);
	}
	
	public void buildMenu() {
		Dimension buttonSize = new Dimension(buttonWIDTH * ClientGUI.SCALE, buttonHEIGHT * ClientGUI.SCALE);
		Dimension titleSize = new Dimension(titleWIDTH * ClientGUI.SCALE, titleHEIGHT * ClientGUI.SCALE);
		
		Font titleFont = new Font("Ariel", Font.BOLD, 64);
		Font menuFont = new Font("Ariel", Font.BOLD, 32);
		
		title = new JLabel("Connect Four", SwingConstants.CENTER);
		title.setFont(titleFont);
		title.setForeground(Color.red);
		
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
		c.add(multiPlayer);
		c.add(credits);
		
		multiPlayer.setBounds((ClientGUI.halfWIDTH - (buttonWIDTH / 2)) * ClientGUI.SCALE, (ClientGUI.halfHEIGHT - (buttonHEIGHT /2)) * ClientGUI.SCALE, buttonSize.width, buttonSize.height);
		
		credits.setBounds((ClientGUI.halfWIDTH - (buttonWIDTH / 2)) * ClientGUI.SCALE, ((ClientGUI.halfHEIGHT + ClientGUI.quaterHEIGHT) - (buttonHEIGHT / 2)) * ClientGUI.SCALE, buttonSize.width, buttonSize.height);
		
		title.setBounds((ClientGUI.halfWIDTH - (titleWIDTH / 2)) * ClientGUI.SCALE, (ClientGUI.quaterHEIGHT - (titleHEIGHT /2)) * ClientGUI.SCALE, titleSize.width, titleSize.height);
	}
	

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == multiPlayer) {
			c.removeAll();
			mpmenu.buildMPMenu();
			c.repaint();
		} else if (event.getSource() == credits) {
			c.removeAll();
			creditsmenu.buildCreditsMenu();
			c.repaint();
		}
		
	}
}
