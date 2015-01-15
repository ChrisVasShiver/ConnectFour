package main;

/**
 * 
 * @author C. Visscher and D. Ye
 * 
 */
public class Board {
	
	public static final int WIDTH = 7;
	public static final int HEIGHT = 6;
	public static final int MAXFIELDS = 42;

	private Mark[] fields;
	
	public Board() {
		fields = new Mark[MAXFIELDS];
		reset();
	}
	
	public Board copyBoard() {
		Board boardCopy = new Board();
		for(int i = 0; i < MAXFIELDS; i++) {
			boardCopy.setField(i, getField(i));
		}
		return boardCopy;
	}
	
	/*@ 
	 	requires 0 <= row && row < HEIGHT;
	 	requires 0 <= col && col < WIDTH;
	 	ensures 0 < \result && \result < MAXFIELDS;
	 	pure;
	 */
	public int matrixToIndex(int row, int col) {
		assert 0 <= row && row < HEIGHT;
		assert 0 <= col && col < WIDTH;
		return HEIGHT * row + col;
	}
	
	/*@ 
		requires 0 <= i && i < MAXFIELDS;
		ensures \result == Mark.EMPTY || \result == Mark.YELLOW || \result == Mark.RED;
		pure;
	 */
	public Mark getField(int i) {
		assert 0 <= i && i < MAXFIELDS;
		return fields[i];
	}
	
	public Mark[] getFields() {
		return fields;
	}
	
	/*@
	 	requires 0 <= i && i < MAXFIELDS;
	 	requires m == Mark.EMPTY || m == Mark.RED || m == Mark.YELLOW;
	 	ensures getField(i) == m;
	 */
	public void setField(int i, Mark m) {
		assert 0 <= i && i < MAXFIELDS;
		assert m == Mark.EMPTY || m == Mark.RED || m == Mark.YELLOW;
		fields[i] = m;
	}
	
	public void reset() {
		for(int i = 0; i < MAXFIELDS; i++) {
			setField(i, Mark.EMPTY);
		}
	}
	
	public String toString() {
		String boardRepresentation = "";
		for(int i = 0 ; i < MAXFIELDS; i++) {
			if((i % 7) == 0) {
				boardRepresentation = boardRepresentation + "\n";
			}
			boardRepresentation = boardRepresentation + " " + getField(i).toString();
		}
		return boardRepresentation;
	}
}