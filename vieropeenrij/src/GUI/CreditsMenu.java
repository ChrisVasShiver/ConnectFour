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

public class CreditsMenu implements ActionListener {
	private static int SPACING = 4;
	
	private static int labelWIDTH = 128;
	private static int labelHEIGHT = labelWIDTH / 16 * 9;
	
	private static int buttonWIDTH = 32;
	private static int buttonHEIGHT = (buttonWIDTH / 16 * 9) / 2; 
	
	private Container c;
	private MainMenu menu;
	
	private JButton backButton;
	private JLabel madeBy;
	public CreditsMenu(JFrame frame, MainMenu menu) {
		c = frame.getContentPane();
		this.menu = menu;		
	}
	
	public void buildCreditsMenu() {
		Dimension buttonSize = new Dimension(buttonWIDTH * ClientGUI.SCALE, buttonHEIGHT * ClientGUI.SCALE);
		Dimension labelSize = new Dimension(labelWIDTH * ClientGUI.SCALE, labelHEIGHT * ClientGUI.SCALE);
		
		Font menuFont = new Font("Ariel", Font.BOLD, 16);
		Font creditsFont = new Font("Ariel", Font.BOLD + Font.ITALIC, 32);
		
		backButton = new JButton("Back to Menu");
		backButton.addActionListener(this);
		backButton.setBorderPainted(false);
		backButton.setFont(menuFont);
		backButton.setForeground(Color.RED);
		
		madeBy = new JLabel("Made By:", SwingConstants.CENTER);
		madeBy.setForeground(Color.RED);
		madeBy.setFont(creditsFont);
		
		c.add(backButton);
		c.add(madeBy);
		
		backButton.setBounds(10, 10, buttonSize.width + 10, buttonSize.height);
		madeBy.setBounds((ClientGUI.halfWIDTH - (labelWIDTH / 2)) * ClientGUI.SCALE, SPACING * ClientGUI.SCALE, labelSize.width, labelSize.height);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == backButton) {
			c.removeAll();
			menu.buildMenu();
			c.repaint();
		}
		
	}
}
