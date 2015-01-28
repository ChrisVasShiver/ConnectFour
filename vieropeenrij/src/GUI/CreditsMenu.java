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
/**
 * GUI for the Credits Menu;
 * @author C. Visscher and D. Ye
 * @version 25-01-2015
 */
public class CreditsMenu implements ActionListener {
	private final static int SPACING = 4;
	
	private final static int labelWIDTH = 128;
	private final static int labelHEIGHT = labelWIDTH / 16 * 9;
	
	private final static int buttonWIDTH = 32;
	private final static int buttonHEIGHT = (buttonWIDTH / 16 * 9) / 2; 
	
	private Container c;
	private MainMenu menu;
	
	private JButton backButton;
	private JLabel madeBy;
	private JLabel person1;
	private JLabel person2;
	
	public CreditsMenu(JFrame frame, MainMenu menu) {
		c = frame.getContentPane();
		this.menu = menu;		
	}
	
	public void buildCreditsMenu() {
		Dimension buttonSize = new Dimension(buttonWIDTH * ClientGUI.SCALE, buttonHEIGHT * ClientGUI.SCALE);
		Dimension labelSize = new Dimension(labelWIDTH * ClientGUI.SCALE, labelHEIGHT * ClientGUI.SCALE);
		
		Font menuFont = new Font("Ariel", Font.BOLD, 16);
		Font nameFont = new Font("Ariel", Font.BOLD + Font.ITALIC, 32);
		Font titleFont = new Font("Ariel", Font.BOLD, 64);
		
		backButton = new JButton("Back to Menu");
		backButton.addActionListener(this);
		backButton.setBorderPainted(false);
		backButton.setFont(menuFont);
		backButton.setForeground(Color.RED);
		
		madeBy = new JLabel("Made By:", SwingConstants.CENTER);
		madeBy.setForeground(Color.RED);
		madeBy.setFont(titleFont);
		
		person1 = new JLabel("C. Visscher", SwingConstants.CENTER);
		person1.setForeground(Color.RED);
		person1.setFont(nameFont);
		
		person2 = new JLabel("D. Ye", SwingConstants.CENTER);
		person2.setForeground(Color.RED);
		person2.setFont(nameFont);
		
		c.add(backButton);
		c.add(madeBy);
		c.add(person1);
		c.add(person2);
		
		backButton.setBounds(10, 10, buttonSize.width + 10, buttonSize.height);
		madeBy.setBounds((ClientGUI.halfWIDTH - (labelWIDTH / 2)) * ClientGUI.SCALE, (ClientGUI.quaterHEIGHT - (labelHEIGHT /2)) * ClientGUI.SCALE, labelSize.width, labelSize.height);
		person1.setBounds((ClientGUI.halfWIDTH - (labelWIDTH / 2)) * ClientGUI.SCALE, ClientGUI.halfHEIGHT + ClientGUI.SCALE * 4, labelSize.width, labelSize.height);
		person2.setBounds((ClientGUI.halfWIDTH - (labelWIDTH / 2)) * ClientGUI.SCALE, (int)person1.getBounds().getMinY() + (labelHEIGHT/2) , labelSize.width, labelSize.height);
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
