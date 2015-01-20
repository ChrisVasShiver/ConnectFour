package main;

import java.util.Scanner;

/**
 * 
 * @author C. Visscher and D. Ye
 * 
 */
public class Game {
	
	public static final int MAXPLAYER = 2;
	public static final String YES = "yes";
	public static final String NO = "no";
	
	private Board board;
	private Rules rules;
	private Player[] players;
	private int currentP;
	private boolean running = false;
	
	
	public Game(Player p0, Player p1) {
		board = new Board();
		rules = new Rules(board);
		players = new Player[MAXPLAYER];
		players[0] = p0;
		players[1] = p1;
		currentP = 0;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public Rules getRules() {
		return rules;
	}
	
	public int getCurrentPlayer() {
		return currentP;
	}
	
	public boolean readBoolean(String message) {
		String input;
		do {
			System.out.println(message);
			Scanner in = new Scanner(System.in);
			input = in.hasNextLine() ? in.nextLine() : null;
		} while(!input.equals(YES) && !input.equals(NO));
		return input.equals(YES);
	}
	
	
	// TODO finish method
	public int readInteger(String message) {
		boolean isInteger = false;
		int value = 0;
		do {
			System.out.println(message);
			Scanner in = new Scanner(System.in);
			String input = in.hasNextLine() ? in.nextLine() : null;
			
			} while (!isInteger);
		return value;
	}
	
	public void start() {
		running = true;
		while (running) {
			reset();
			update();
			gameLoop();
			running = readBoolean("Want to play another game? (yes/no)");
		}
	}
	
	public void stop() {
	}
	
	
	// TODO verander true in isGameOver()
	public void gameLoop() {
		update();
		while(true) {
			players[getCurrentPlayer()].doMove(board);
			currentP = (currentP + 1) % 2;
			update();
		}
		//gameResult();
	}
	
	public void update() {
		System.out.println("current game situation\n" + "It is " + players[getCurrentPlayer()].getMark() + "'s turn\n\n" + board.toString());
	}
	
	public void reset() {
		currentP = 0;
		board.reset();
	}
	
	// TODO implement endGame
	public void endGame() {
		}
}
