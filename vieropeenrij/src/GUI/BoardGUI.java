package GUI;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.Board;
import main.Game;
import main.HumanPlayer;
import main.Mark;

public class BoardGUI extends JFrame implements ActionListener {
	
	private Container c;
//	private Game game;
//	private MultiPlayerMenu mpmenu;
	private Board board;
	
	public JButton[] fields = new JButton[board.MAXFIELDS];
	public JPanel boardPanel;
	public JPanel p2;

	
	public BoardGUI(Game game) {
		c = getContentPane();
		board = game.getBoard();
		buildBoardGUI();
	}
	
	public void buildBoardGUI() {
		
		boardPanel = new JPanel(new GridLayout(board.HEIGHT, board.WIDTH));
		p2 = new JPanel(new FlowLayout());
		
		for(int i = 0; i < board.MAXFIELDS; i++) {
			fields[i] = new JButton(Integer.toString(i));
			fields[i].addActionListener(this);
			boardPanel.add(fields[i]);
			
		}
		
		c.add(boardPanel);
		c.add(p2);
		
		
		Insets insets = getInsets();
		boardPanel.setBounds(0, 0, 960, 700);
		p2.setBounds((int)boardPanel.getBounds().getMaxX(), 0, 320, 710);
		
		setSize(1280, 720);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		new BoardGUI(new Game(new HumanPlayer("Carolijn", Mark.RED), new HumanPlayer("Christiaan" ,Mark.YELLOW)));
	}
}
