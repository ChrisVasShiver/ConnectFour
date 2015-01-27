package GUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import netwerk.Client;
import main.Board;
import main.Game;
import main.Mark;
/**
 * The GUI that represents the board
 * @author C. Visscher and D. Ye
 * 
 */
public class BoardGUI {
	private static int messageBoxWIDTH = 400;
	private static int messageBoxHEIGHT = 200;
	private static int scrollBarWIDTH = 4;
	
	private Container c;
	private Client client;
	private Game game;
	private MultiPlayerMenu mpmenu;
	private Board board;
	private JFrame frame;
	
	private JButton[] fields = new JButton[board.MAXFIELDS];
	private JPanel boardPanel;
	private JPanel p2;
	private JTextArea messageBox;
	private JScrollPane scrollMessageBox;
	
	private BoardController controller;
	
	public BoardGUI(JFrame frame, Client client, MultiPlayerMenu mpmenu) {
		this.frame = frame;
		c = frame.getContentPane();
		
		this.client = client;		
		game = client.getGame();
		board = game.getBoard();
		
		this.mpmenu = mpmenu;
		
		controller = new BoardController();
	}
	
	public void buildBoardGUI() {
		Dimension scrollBarSize = new Dimension(scrollBarWIDTH * ClientGUI.SCALE ,messageBoxHEIGHT * ClientGUI.SCALE);
		
		
		boardPanel = new JPanel();
		boardPanel.setBackground(Color.BLACK);
		p2 = new JPanel(); 
		p2.setBackground(Color.DARK_GRAY);
		
		for(int i = 0; i < Board.MAXFIELDS; i++) {
			fields[i] = new JButton(Mark.EMPTY.toString());
			fields[i].setBackground(Color.WHITE);
			fields[i].addActionListener(controller);
			boardPanel.add(fields[i]);
		}
		
		for(int r = 0; r < board.HEIGHT; r++) {
			for(int c = 0; c < board.WIDTH; c++) {
				fields[(r * Board.WIDTH) + c].setBounds(c * 125, r * (710/6 - 3), 125, 710/6 - 3);
			}
		}
		
		messageBox = new JTextArea();
		messageBox.setEditable(false);
		scrollMessageBox = new JScrollPane(messageBox);
		scrollMessageBox.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollMessageBox.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		p2.add(scrollMessageBox);
		
		messageBox.setBounds(0, 0, messageBoxWIDTH, messageBoxHEIGHT);
		scrollMessageBox.setBounds(0, 0, 400, 200);
		
		c.add(boardPanel);
		c.add(p2);
		
		boardPanel.setBounds(0, 0, 875, 700);
		p2.setBounds((int)boardPanel.getBounds().getMaxX(), 0, 405, 710);
		
	}
	
	public class BoardController implements ActionListener, Observer {
		
		public BoardController() {
			game.addObserver(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			for(int i = 0; i < Board.MAXFIELDS; i++) {
				if(event.getSource() == fields[i]) {
					client.doMove(i);
				}
			}
			
		}
		
		public void updateBoard() {
			Board updateBoard = game.getBoard();
			for(int i = 0; i < Board.MAXFIELDS; i++) {
				if (updateBoard.getField(i) == Mark.YELLOW) {
					fields[i].setBackground(Color.YELLOW);
					fields[i].setText(Mark.YELLOW.toString());
				} else if (updateBoard.getField(i) == Mark.RED) {
					fields[i].setBackground(Color.RED);
					fields[i].setText(Mark.RED.toString());
				} else {
					fields[i].setBackground(Color.WHITE);
					fields[i].setText(Mark.EMPTY.toString());
				}
			}
			c.invalidate();
		}

		@Override
		public void update(Observable o, Object arg) {
			String notify = (String)arg;
			switch(notify) {
			case "UPDATE_BOARD" : updateBoard();
			}
		}
	}
}
