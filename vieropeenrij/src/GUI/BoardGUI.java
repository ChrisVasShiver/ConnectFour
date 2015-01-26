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
import main.Mark;

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
		boardPanel = new JPanel();
		boardPanel.setBackground(Color.BLACK);
		p2 = new JPanel(); 
		p2.setBackground(Color.DARK_GRAY);
		
		for(int i = 0; i < Board.MAXFIELDS; i++) {
			fields[i] = new JButton(Mark.EMPTY.toString());
			fields[i].setBackground(Color.WHITE);
			fields[i].addActionListener(this);
			boardPanel.add(fields[i]);
		}
		
		c.add(boardPanel);
		c.add(p2);
		
		boardPanel.setBounds(0, 0, 875, 700);
		p2.setBounds((int)boardPanel.getBounds().getMaxX(), 0, 405, 710);
		
		for(int r = 0; r < board.HEIGHT; r++) {
			for(int c = 0; c < board.WIDTH; c++) {
				fields[board.matrixToIndex(r, c)].setBounds(c * 125, r * (710/6 - 3), 125, 710/6 - 3);
			}
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent event) {	
	}
}
