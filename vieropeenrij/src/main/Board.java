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
	
	//@ private invariant fields != null;
	//@ private constraint fields.length == \old(fields.length);
	private Mark[] fields;
	
	public Board() {
		fields = new Mark[MAXFIELDS];
		reset();
	}
	/*@
	 	ensures (\forall int i; 0 <= i && i < MAXFIELDS; \result.getField(i) == this.getField(i));
	 	pure;
	 */
	public Board copyBoard() {
		Board boardCopy = new Board();
		//@ loop_invariant 0 <= i && i < MAXFIELDS;
		for(int i = 0; i < MAXFIELDS; i++) {
			boardCopy.setField(i, getField(i));
		}
		return boardCopy;
	}
	
	/*@ 
	 	requires 0 <= row && row < Board.HEIGHT;
	 	requires 0 <= col && col < Board.WIDTH;
	 	ensures 0 <= \result && \result < Board.MAXFIELDS;
	 	pure;
	 */
	public int matrixToIndex(int row, int col) {
		assert 0 <= row && row < HEIGHT;
		assert 0 <= col && col < WIDTH;
		return HEIGHT * row + col;
	}
	
	/*@
	 	requires 0 <= i && i < Board.MAXFIELDS;
	 	ensures 0 <= \result[0] && \result[0] < HEIGHT && 0 <= \result[1] && \result[1] < WIDTH;
	 	pure;
	 */
	public int[] indexToMatrix(int i) {
		assert 0 < i && i < MAXFIELDS;
		
		int[] resultArray = new int[2];
		//calculate col
		resultArray[1] = (i % WIDTH);
		//calculate row
		resultArray[0] = (i - resultArray[1]) / HEIGHT;
		return resultArray;
	}
	
	/*@ 
		requires 0 <= i && i < Board.MAXFIELDS;
		ensures \result == Mark.EMPTY || \result == Mark.YELLOW || \result == Mark.RED;
		pure;
	 */
	public Mark getField(int i) {
		assert 0 <= i && i < MAXFIELDS;
		return fields[i];
	}
	
	/*@
	 	requires 0 <= i && i < Board.MAXFIELDS;
	 	requires m == Mark.EMPTY || m == Mark.RED || m == Mark.YELLOW;
	 	ensures getField(i) == m;
	 */
	public void setField(int i, Mark m) {
		assert 0 <= i && i < MAXFIELDS;
		assert m == Mark.EMPTY || m == Mark.RED || m == Mark.YELLOW;
		fields[i] = m;
	}
	
	/*@
	 	ensures (\forall int i; 0 <= i && i < Board.MAXFIELDS; this.getField(i) == Mark.EMPTY);
	 */
	public void reset() {
		//@loop_invariant 0 <= i && i < Board.MAXFIELDS;
		for(int i = 0; i < MAXFIELDS; i++) {
			setField(i, Mark.EMPTY);
		}
	}
	
	//TODO improve representation
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