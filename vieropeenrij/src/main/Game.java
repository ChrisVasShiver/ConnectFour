package main;

/**
 * 
 * @author C. Visscher and D. Ye
 * 
 */
public class Game {
	
	public static final int MAXPLAYER = 2;
	
	private Board board;
	private Rules rules;
	private Player[] players;
	private Player currentPlayer;
	private Boolean running;
	
	
	public Game(Player p0, Player p1) {
		board = new Board();
		rules = new Rules(board);
		players = new Player[MAXPLAYER];
		players[0] = p0;
		players[1] = p1;
		currentPlayer = players[0];
	}
	
	public Board getBoard() {
		return board;
	}
	
	public Rules getRules() {
		return rules;
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public void start() {
	}
	
	public void update() {
		System.out.println(board.toString());
	}
	
	public void reset() {
		currentPlayer = players[0];
		board.reset();
	}
}
