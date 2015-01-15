package main;

/**
 * 
 * @author C. Visscher and D. Ye
 * 
 */
public enum Mark {
	YELLOW, RED, EMPTY;
	
	public Mark opponent() {
		if(this == Mark.YELLOW) {
			return Mark.RED;
		} else if (this == Mark.RED) {
			return Mark.YELLOW;
		} else {
			return Mark.EMPTY;
		}	
	}
}
