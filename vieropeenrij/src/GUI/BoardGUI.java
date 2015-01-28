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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

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
	
	private static int buttonWIDTH = 32;
	private static int buttonHEIGHT = (buttonWIDTH / 16 * 9) / 2;
	
	private static int labelWIDTH = 32;
	private static int labelHEIGHT = (labelWIDTH / 16 * 9) / 2;
	
	private Container c;
	private Client client;
	private Game game;
	private MultiPlayerMenu mpmenu;
	private Board board;
	private JFrame frame;
	
	private JLabel playerName;
	private JButton backButton;
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
		Dimension labelSize = new Dimension(labelWIDTH * ClientGUI.SCALE,
				labelHEIGHT * ClientGUI.SCALE);
		Dimension scrollBarSize = new Dimension(scrollBarWIDTH * ClientGUI.SCALE ,messageBoxHEIGHT * ClientGUI.SCALE);
		Dimension buttonSize = new Dimension(buttonWIDTH * ClientGUI.SCALE,
				buttonHEIGHT * ClientGUI.SCALE);
		
		boardPanel = new JPanel();
		boardPanel.setBackground(Color.BLACK);
		p2 = new JPanel(); 
		p2.setBackground(Color.BLACK);
		
		for(int i = 0; i < Board.MAXFIELDS; i++) {
			fields[i] = new JButton();
			if (board.getField(i) == Mark.YELLOW) {
				fields[i].setBackground(Color.YELLOW);
				fields[i].setText(Mark.YELLOW.toString());
			} else if (board.getField(i) == Mark.RED) {
				fields[i].setBackground(Color.RED);
				fields[i].setText(Mark.RED.toString());
			} else {
				fields[i].setBackground(Color.WHITE);
				fields[i].setText(Mark.EMPTY.toString());
			}
			fields[i].addActionListener(controller);
			boardPanel.add(fields[i]);
		}
		for(int i = 0; i < Board.MAXFIELDS; i++) {
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
		
		backButton = new JButton("Back to Menu");
		backButton.addActionListener(controller);
		backButton.setBorderPainted(false);
		backButton.setForeground(Color.RED);
		
		playerName = new JLabel(client.getClientName();
		playerName.setBackground(Color.BLACK);
		playerName.setForeground(Color.RED);
		
		p2.add(scrollMessageBox);
		p2.add(backButton);
		p2.add(playerName);
		
		
		messageBox.setBounds(0, 0, messageBoxWIDTH, messageBoxHEIGHT);
		scrollMessageBox.setBounds(0, 0, 400, 200);
		backButton.setBounds((405 - buttonSize.width) / 2 , 710 / 2, buttonSize.width, buttonSize.height);
		playerName.setBounds((405 - labelSize.width) /2, (int)backButton.getBounds().getMaxY() + labelSize.height, labelSize.width, labelSize.height);
		
		
		c.add(boardPanel);
		c.add(p2);
		
		boardPanel.setBounds(0, 0, 875, 700);
		p2.setBounds((int)boardPanel.getBounds().getMaxX(), 0, 405, 710);
		
	}
	
	public void addMessage(String message) {
		messageBox.append(message + "\n");
	}
	
	public class BoardController implements ActionListener, Observer {
		
		public BoardController() {
			game.addObserver(this);
			board.addObserver(this);
		}
		
		public void updateBoard() {
            board = game.getBoard();
            boardPanel.removeAll();
            for(int i = 0; i < Board.MAXFIELDS; i++) {
                    fields[i] = new JButton();
                    if (board.getField(i) == Mark.YELLOW) {
                            fields[i].setBackground(Color.YELLOW);
                            fields[i].setText(Mark.YELLOW.toString());
                    } else if (board.getField(i) == Mark.RED) {
                            fields[i].setBackground(Color.RED);
                            fields[i].setText(Mark.RED.toString());
                    } else {
                            fields[i].setBackground(Color.WHITE);
                            fields[i].setText(Mark.EMPTY.toString());
                    }
                    fields[i].addActionListener(controller);
                    boardPanel.add(fields[i]);
            }

            for(int r = 0; r < board.HEIGHT; r++) {
                    for(int c = 0; c < board.WIDTH; c++) {
                            fields[(r * Board.WIDTH) + c].setBounds(c * 125, r * (710/6 - 3), 125, 710/6 - 3);
                    }
            }
            boardPanel.repaint();
    }
		
		@Override
		public void actionPerformed(ActionEvent event) {
			for(int i = 0; i < Board.MAXFIELDS; i++) {
				if(event.getSource() == fields[i]) {
					client.doMove(i);
				}
			}
			if(event.getSource() == backButton) {
				c.removeAll();
				client.closeClient();
				mpmenu.buildMPMenu();
				c.repaint();
			}
			
		}

		@Override
		public void update(Observable o, Object arg) {
			assert o != null;
			assert arg != null;
			String notify = (String)arg;
			switch(notify) {
			case "UPDATE_BOARD" : updateBoard(); break;
			case "NEXT_PLAYER" : addMessage("It is " + game.getCurrentPlayer()  + "'s turn!"); break;
			case "SERVER_MESSAGE" : addMessage(client.getConsoleMessage()); break;
			}
		}
	}
}
