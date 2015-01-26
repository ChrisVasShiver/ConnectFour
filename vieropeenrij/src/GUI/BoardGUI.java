package GUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.Board;
import main.Game;

public class BoardGUI implements ActionListener {
	
	private Container c;
	private Game game;
//	private MultiPlayerMenu mpmenu;
	private Board board;
	private JFrame frame;
	
	private JButton[] fields = new JButton[board.MAXFIELDS];
	private JPanel boardPanel;
	private JPanel p2;

	
	public BoardGUI(JFrame frame, Game game) {
		this.frame = frame;
		c = frame.getContentPane();
		this.game = game;
		board = game.getBoard();
	}
	
	public void buildBoardGUI() {
		
		boardPanel = new JPanel(new GridLayout(board.HEIGHT, board.WIDTH));
		boardPanel.setBackground(Color.BLACK);
		p2 = new JPanel(new FlowLayout()); 
		p2.setBackground(Color.DARK_GRAY);
		
		for(int i = 0; i < Board.MAXFIELDS; i++) {
			fields[i] = new JButton();
			fields[i].setBackground(Color.WHITE);
			boardPanel.add(fields[i]);
		}
		
		c.add(boardPanel);
		c.add(p2);
		
		boardPanel.setBounds(0, 0, 960, 700);
		p2.setBounds((int)boardPanel.getBounds().getMaxX(), 0, 320, 710);
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
