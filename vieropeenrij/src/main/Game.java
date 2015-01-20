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
	
	public void start() {
		running = true;
		while (running) {
			reset();
			gameLoop();
		}
	}
	
	public void stop() {
	}
	
	public void gameLoop() {
		update();
		while(true) {
			players[getCurrentPlayer()].doMove(board);
			currentP = (currentP + 1) % 2;
			update();
		}
	}
	
	public void update() {
		System.out.println("current game situation\n" + "It is " + players[getCurrentPlayer()].getMark() + "'s turn\n\n" + board.toString());
	}
	
	public void reset() {
		currentP = 0;
		board.reset();
	}
}
