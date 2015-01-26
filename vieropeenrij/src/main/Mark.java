package main;

/**
 * 
 * @author C. Visscher and D. Ye
 * 
 */
public enum Mark {
	YELLOW, RED, EMPTY;

	/**
	 * Returns the opponent.
	 * 
	 * @return returns Mark.RED when this == Mark.YELLOW. Returns Mark.YELLOW
	 *         when this == Mark.RED. Else returns Mark.EMPTY.
	 */
	/*@
	 ensures this == Mark.YELLOW ==> \result == Mark.RED || this == Mark.RED ==> \result == Mark.YELLOW || this != Mark.YELLOW && this != Mark.RED ==> \result == Mark.EMPTY;
	 */
	public Mark opponent() {
		if (this == Mark.YELLOW) {
			return Mark.RED;
		} else if (this == Mark.RED) {
			return Mark.YELLOW;
		} else {
			return Mark.EMPTY;
		}
	}
}
