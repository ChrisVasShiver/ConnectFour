package main;

/**
 * 
 * @author C. Visscher and D. Ye
 * 
 */
public abstract class Player {
	/*@
	 private invariant theName != null;
	 private invariant theMark == Mark.YELLOW || theMark == Mark.RED;
	 */
	private String theName;
	private Mark theMark;
	
	/**
	 * Creates a new instance of Player.
	 * @param name the name of the player.
	 * @param mark the mark of the player.
	 */
	/*@ requires name != null;
	 	requires mark == Mark.YELLOW || mark == Mark.RED;
	 */
	public Player(String name, Mark mark) {
		assert name != null;
		assert mark == Mark.YELLOW || mark == Mark.RED;
		theName = name;
		theMark = mark;
	}
	
	/**
	 * Get the name of this player.
	 * @return returns the name.
	 */
	/*@
	 	ensures \result != null;
	 	pure;
	 */
	public String getName() {
		return theName;
	}
	
	/**
	 * Get the mark of this player.
	 * @return returns the mark. Returns either Mark.YELLOW or Mark.RED.
	 */
	/*@
	 ensures \result != null;
	 pure;
	 */
	public Mark getMark() {
		return theMark;
	}
	
	/**
	 * Set the mark of the player.
	 * @param m the mark.
	 */
	/*@
	 requires m != null;
	 ensures getMark() == m;
	 */
	public void setMark(Mark m){
		assert m != null;
		theMark = m;
	}
	
	/**
	 * Determines the next move on the given board.
	 * @param board the board where the move has to be placed on.
	 * @return returns the integer of the move.
	 */
	/*@
	 requires game != null;
	 pure;
	 */
	public abstract int determineMove(Game game);
	
	/**
	 * Make a move on the given board and return the move.
	 * @param board the board where the move has to be placed on.
	 * @return returns the integer of the move.
	 */
	/*@
	 requires game != null;
	 ensures \result == determineMove(game);
	 */
	public int doMove(Game game) {
		int move = determineMove(game);
		game.getBoard().setField(move, theMark);
		return move;
	}
}
