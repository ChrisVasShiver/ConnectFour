package main;

import java.util.Scanner;

/**
 * 
 * @author C. Visscher and D. Ye
 * 
 */
public class Game {
	
	/*@
	 	invariant MAXPLAYER == 2;
	 	invariant YES == "yes";
	 	invariant NO == "no";
	 */
	public static final int MAXPLAYER = 2;
	public static final String YES = "yes";
	public static final String NO = "no";
	
	/*@
	 	private invariant board != null;
	 	private invariant rules != null;
	 	private invariant players.length == 2;
	 	private invariant currentP == 0 || currentP == 1;
	 */
	private Board board;
	private Rules rules;
	private Player[] players;
	private int currentP;
	private boolean running = false;
	
	
	/**
	 * Creates a new Game class. It also creates a new instance of board and rules.
	 * @param p0 the first player to be inserted into the game.
	 * @param p1 the second player to be inserted into the game.
	 */
	/*@
	 	requires p0 != null;
	 	requires p1 != null;
	 	ensures getBoard() == new Board();
	 	ensures getRules() == new Rules(getBoard());
	 	ensures getPlayers().length == 2;
	 	ensures getPlayers()[0] == p0;
	 	ensures getPlayers()[1]== p1;
	 	ensures getCurrentPlayerIndex() == 0;
	 */
	public Game(Player p0, Player p1) {
		board = new Board();
		rules = new Rules(board);
		players = new Player[MAXPLAYER];
		players[0] = p0;
		players[1] = p1;
		currentP = 0;
	}
	
	/**
	 * Returns the board.
	 * @return the board
	 */
	/*@
	 	ensures \result != null;
	 	pure;
	 */
	public Board getBoard() {
		return board;
	}
	
	/**
	 * Returns the rules of the game.
	 * @return the rules
	 */
	/*@
	 	ensures \result != null;
	 	pure;
	 */
	public Rules getRules() {
		return rules;
	}
	
	/**
	 * Returns the index of the currentplayer from the players array.
	 * @return an integer of the current player. Returns either 0 or 1.
	 */
	/*@
	 	ensures \result == 0 || \result == 1;
	 	pure;
	 */
	 public int getCurrentPlayerIndex() {
	 	return currentP;
	 }

	 /**
	  * Get the String of the currentplayers name.
	  * @return returns the currentplayers name.
	  */
	 /*@
	  	ensures \result != null;
	  	ensures \result == getPlayers()[getCurrentPlayerIndex()].getName();
	  */
	public String getCurrentPlayer(){
		return players[currentP].getName();

	}
	
	/**
	 * Get the String of the player whose turn is next.
	 * @return returns the name of the player of the next turn.
	 */
	/*@
	 	ensures \result != null;
	 	ensures \result == getPlayers()[getCurrentPlayerIndex()+1].getName();
	 */
	public String getNextPlayer(){
		int temp = currentP + 1 % 2;
		return players[temp].getName();
	}

	public void setCurrentPlayer(String name){
		if (players[0].equals(name)){
			currentP = 0;
		}
		if (players[1].equals(name)){
			currentP = 1;
		}
	}
	
	/*@
	 	pure;
	 */
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
			int winner = Math.abs((currentP - 1));
			System.out.println(winner);
			System.out.println("The Winner is: " + players[winner].getName() + "!!!\n");
		} else {
			System.out.println("It is a Draw!\n");
		}
	}
}
