package main;

import java.util.Observable;
import java.util.Scanner;

/**
 * The Game Class for the game, it manages the Connect Four game.
 * @author C. Visscher and D. Ye
 * 
 */
public class Game extends Observable {

	/*@
	 invariant MAXPLAYER == 2; invariant YES == "yes"; invariant NO == "no";
	 */
	public static final int MAXPLAYER = 2;
	public static final String YES = "yes";
	public static final String NO = "no";

	/*@
	 private invariant board != null; private invariant rules != null;
	 private invariant players.length == 2; private invariant currentP == 0 || currentP == 1;
	 */
	private Board board;
	private Rules rules;
	private Player[] players;
	private int currentP;
	private boolean running = false;
	private String winner;

	/**
	 * Creates a new Game class. It also creates a new instance of board and
	 * rules.
	 * 
	 * @param p0
	 *            the first player to be inserted into the game.
	 * @param p1
	 *            the second player to be inserted into the game.
	 */
	/*@ 
	 requires p0 != null; 
	 requires p1 != null; ensures getBoard() == new Board();
	 ensures getRules() == new Rules(getBoard()); 
	 ensures getPlayers().length == 2;
	 ensures getPlayers()[0] == p0;
	 ensures getPlayers()[1]== p1;
	 ensures getCurrentPlayerIndex() == 0;
	 */
	public Game(Player p0, Player p1) {
		assert p0 != null;
		assert p1 != null;
		board = new Board();
		rules = new Rules(board);
		players = new Player[MAXPLAYER];
		players[0] = p0;
		players[1] = p1;
		currentP = 0;
	}

	/**
	 * Returns the board.
	 * 
	 * @return returns the board
	 */
	/*@
	ensures \result != null; pure;
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * Returns the rules of the game.
	 * 
	 * @return returns the rules
	 */
	/*@
	 ensures \result != null; pure;
	 */
	public Rules getRules() {
		return rules;
	}

	/**
	 * Returns the index of the currentplayer from the players array.
	 * 
	 * @return returns an integer of the current player. Returns either 0 or 1.
	 */
	/*@
	 ensures \result == 0 || \result == 1; pure;
	 */
	public int getCurrentPlayerIndex() {
		return currentP;
	}

	/**
	 * Get the String of the currentplayers name.
	 * 
	 * @return returns the currentplayers name.
	 */
	/*@
	 ensures \result != null;
	 ensures \result == getPlayers()[getCurrentPlayerIndex()].getName();
	 */
	public String getCurrentPlayer() {
		return players[currentP].getName();

	}

	/**
	 * Get the String of the player whose turn is next.
	 * 
	 * @return returns the name of the player of the next turn.
	 */
	/*@ 
	 ensures \result != null;
	 ensures \result == getPlayers()[getCurrentPlayerIndex()+1].getName();
	 */
	public String getNextPlayer() {
		int temp = (currentP + 1) % 2;
		return players[temp].getName();
	}
	
	/**
	 * Get the winner of the game.
	 * @return the winner.
	 */
	public String getWinner(){
		return winner;
	}
	
	/**
	 * Sets the String of the winner.
	 * @param winner the winner
	 */
	/*@
	 requires winner != null;
	 ensures getWinner() == winner;
	 */
	public void setWinner(String winner){
		assert winner != null;
		this.winner = winner;
	}

	/**
	 * Change the currentplayer to the given name, only if the given name in the
	 * players array.
	 * 
	 * @param name
	 *            the name of the player you want to be the currentplayer.
	 */
	/*@
	 requires name != null;
	 ensures (getPlayers()[0].equals(name)) ==> getCurrentPlayerIndex() == 0;
	 ensures (getPlayers()[1].equals(name)) ==> getCurrentPlayerIndex() == 1;
	 */
	public void setCurrentPlayer(String name) {
		assert name != null;
		if (players[0].equals(name)) {
			currentP = 0;
		}
		if (players[1].equals(name)) {
			currentP = 1;
		}
	}
	public void setNextPlayer(){
		setChanged();
		currentP = (currentP + 1) % MAXPLAYER;
		notifyObservers("NEXT_PLAYER");
	}

	/**
	 * Get all the players in the game.
	 * 
	 * @return returns an array of Player.
	 */
	/*@
	 ensures \result != null;
	 pure;
	 */
	public Player[] getPlayers() {
		return players;
	}

	/**
	 * This method is used to read the input from the TUI. This method not used
	 * in the final project ConnectFour game. The method is only used when the
	 * TUI is implemented instead of the GUI.
	 * 
	 * @param message
	 *            the input from the player. Input has to be an index where the
	 *            move is wanted. Or the input is a yes or no when the game is
	 *            over and asked if you want to play another game.
	 * @return returns true if input equals yes.
	 */
	/*@
	 requires message != null;
	 ensures (message.equals("yes")) ==> \result == true;
	 */
	public boolean readBoolean(String message) {
		assert message != null;
		String input;
		do {
			System.out.println(message);
			Scanner in = new Scanner(System.in);
			input = in.hasNextLine() ? in.nextLine() : null;
		} while (!input.equals(YES) && !input.equals(NO));
		return input.equals(YES);
	}

	/**
	 * This method will run when a game instance is created.
	 */
	public void start() {
		running = true;
		while (running) {
			reset();
			update();
			gameLoop();
			running = readBoolean("Want to play another game? (yes/no)");
		}
	}

	/**
	 * Keeps checking if the game is over or not. The game is over when there is
	 * a winner or there is a winner and/or fullboard.
	 */
	public void gameLoop() {
		while (!rules.getGameOver()) {
			rules.isGameOver(players[getCurrentPlayerIndex()].getMark(),
					players[getCurrentPlayerIndex()].doMove(board));
			currentP = (currentP + 1) % MAXPLAYER;
			update();
		}
		endGame();
	}

	/**
	 * Tells the current game situation and whose turn it is in the TUI.
	 */
	public void update() {
		System.out.println("current game situation\n" + "It is "
				+ players[getCurrentPlayerIndex()].getMark() + "'s turn\n\n"
				+ board.toString());
	}

	/**
	 * Resets the board and rules.
	 */
	/*@
	 	ensures getCurrentPlayerIndex() == 0;
	 	ensures (\forall int i; 0 <= i && i < Board.MAXFIELDS; getBoard().getField(i).equals(Mark.EMPTY));
		ensures getRules().getGameOver() == false;
		ensures getRules().getHasWinner() == false;
	 */
	public void reset() {
		currentP = 0;
		board.reset();
		rules.reset();
	}

	/**
	 * Prints out the result of the game when the game has ended.
	 */
	public void endGame() {
		if (rules.getHasWinner()) {
			int winner = Math.abs((currentP - 1));
			System.out.println(winner);
			System.out.println("The Winner is: " + players[winner].getName()
					+ "!!!\n");
		} else {
			System.out.println("It is a Draw!\n");
		}
	}
}
