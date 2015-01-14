package main;

/**
 * 
 * @author C. Visscher and D. Ye
 * 
 */
public class Game {
	
	public static final int MAXPLAYER = 2;
	
	private Board board;
	private Player[] players;
	private Player currentPlayer;
	private Boolean activeGame;
	
	
	public Game(Player p0, Player p1) {
		board = new Board();
		players = new Player[MAXPLAYER];
		players[0] = p0;
		players[1] = p1;
		currentPlayer = players[0];
	}
	
	public Board getBoard() {
		return board;
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
