package main;

import strategy.Strategy;

public abstract class Player {
	private String pname;
	private Mark pmark;
	/*@ requires name != null;
	 	requires mark != null;
	 	requires mark == Mark.YELLOW || mark == Mark.RED;
	 */
	public Player(String name, Mark mark) {
		pname = name;
		pmark = mark;
	}
	
	public String getName() {
		return pname;
	}
	
	public Mark getMark() {
		return pmark;
	}
	
	public abstract int doMove(Board board);
}
