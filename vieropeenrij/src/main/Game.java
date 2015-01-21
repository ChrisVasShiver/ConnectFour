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
	
	public int getCurrentPlayerIndex() {
		return currentP;
	}
	
	public Player[] getPlayers() {
		return players;
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
	
	public void start() {
		running = true;
		while (running) {
			reset();
			update();
			System.out.println("currentplayer" + currentP);
			gameLoop();
			running = readBoolean("Want to play another game? (yes/no)");
		}
	}
	
	public void gameLoop() {
		while(!rules.getGameOver()) {
			rules.isGameOver(players[getCurrentPlayerIndex()].getMark(), players[getCurrentPlayerIndex()].doMove(board));
			currentP = (currentP + 1) % MAXPLAYER;
			update();
		}
		endGame(); 
	}
	
	public void update() {
		System.out.println("current game situation\n" + "It is " + players[getCurrentPlayerIndex()].getMark() + "'s turn\n\n" + board.toString());
	}
	
	public void reset() {
		currentP = 0;
		board.reset();
		rules.reset();
	}
	
	public void endGame() {
		if(rules.getHasWinner()) { 
			System.out.println(currentP);
			int winner = Math.abs((currentP - 1));
			System.out.println(winner);
			System.out.println("The Winner is: " + players[winner].getName() + "!!!\n");
		} else {
			System.out.println("It is a Draw!\n");
		}
	}
}
