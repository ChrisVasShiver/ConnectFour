package main;

import strategy.Strategy;

/**
 * 
 * @author C. Visscher and D. Ye
 * 
 */
public abstract class Player {
	private String theName;
	private Mark theMark;
	/*@ requires name != null;
	 	requires mark != null;
	 	requires mark == Mark.YELLOW || mark == Mark.RED;
	 */
	public Player(String name, Mark mark) {
		theName = name;
		theMark = mark;
	}
	
	/*@
	 	ensures \result != null;
	 	pure;
	 */
	public String getName() {
		return theName;
	}
	
	public Mark getMark() {
		return theMark;
	}
	
	public void setMark(Mark m){
		theMark = m;
	}
	
	public abstract int determineMove(Board board);
	
	public int doMove(Board board) {
		int move = determineMove(board);
		board.setField(move, theMark);
		return move;
	}
}
