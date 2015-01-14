package main;

public class Game {
	
	public static final int MAXPLAYER = 2;
	
	private Board board;
	private Player[] players;
	private int current;
	
	
	public Game(Player p0, Player p1) {
		board = new Board();
		players = new Player[MAXPLAYER];
		players[0] = p0;
		players[1] = p1;
		current = 0;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public void start() {
	}
	
	public void update() {
		System.out.println(board.toString());
	}
	
	public void reset() {
		current = 0;
		board.reset();
	}
}
