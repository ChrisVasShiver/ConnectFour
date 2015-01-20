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
	
	/*@ private invariant fields != null;
 		private invariant (\forall int i; 0 <= i && i < MAXFIELDS; fields[i] == Mark.EMPTY || fields[i] == Mark.RED || fields[i] == Mark.YELLOW);
		private constraint fields.length == \old(fields.length);
	*/ 
	private Mark[] fields;
	
	public Board() {
		fields = new Mark[MAXFIELDS];
		reset();
	}
	
	/**
	 * Make a copy of the board.
	 * @return a new board with exact the same values as this board.
	 */
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
	
	/**
	 * Converts a matrix into an index
	 * @param row the row number of the matrix
	 * @param col the colum number of the matrix
	 * @return the index number between 0 and 63
	 */
	/*@ 
	 	requires 0 <= row && row < Board.HEIGHT;
	 	requires 0 <= col && col < Board.WIDTH;
	 	ensures 0 <= \result && \result < Board.MAXFIELDS;
	 	pure;
	 */
	public int matrixToIndex(int row, int col) {
		assert 0 <= row && row < HEIGHT;
		assert 0 <= col && col < WIDTH;
		return WIDTH * row + col;
	}
	
	/**
	 * Converts a index into a matrix
	 * @param i the index number of the field
	 * @return an array of integers of length 2 with the first integer beeing the row of the maxtrix and the second integer the colum of the matrix.
	 */
	/*@
	 	requires 0 <= i && i < MAXFIELDS;
	 	ensures 0 <= \result[0] && \result[0] < HEIGHT && 0 <= \result[1] && \result[1] < WIDTH;
	 	ensures \result.length == 2;
	 	pure;
	 */
	public int[] indexToMatrix(int i) {
		assert 0 < i && i < MAXFIELDS;
		
		int[] resultArray = new int[2];
		//calculate col
		resultArray[1] = (i % WIDTH);
		//calculate row
		resultArray[0] = (i - resultArray[1]) % HEIGHT;
		return resultArray;
	}
	
	/**
	 * 
	 * @param i
	 * @return
	 */
	/*@ 
		requires 0 <= i && i < MAXFIELDS;
		ensures \result == Mark.EMPTY || \result == Mark.YELLOW || \result == Mark.RED;
		pure;
	 */
	public Mark getField(int i) {
		assert 0 <= i && i < MAXFIELDS;
		return fields[i];
	}
	
	/**
	 * Checks if the field value is empty 
	 * @param i the index of the field
	 * @return true if getField equals Mark.Empty else return false
	 */
	public boolean isEmptyField(int i) {
		if(getField(i) != Mark.EMPTY) {
			return false;
		} else {
		return true;
		}
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
	
	//@ ensures \result != null;
	@Override
	public String toString() {
		String seperator = "-----------------------------------------------------------------------\n";
		String board = String.format("%-1s %-7s %-1s %-7s %-1s %-7s %-1s %-7s %-1s %-7s %-1s %-7s %-1s %-7s %-1s\n", "|", 0, "|", 1, "|", 2, "|", 3, "|", 4, "|", 5, "|", 6, "|") + seperator;
		Mark[] rowArray = new Mark[WIDTH];
		//@loop_invariant 0 <= row && row < HEIGHT;
		for(int row = 0; row < HEIGHT; row++) {
			//@loop_invariant 0 <= col && col < WIDTH;
			for(int col = 0; col < WIDTH; col++) { 
				rowArray[col] = getField(matrixToIndex(row, col));
			}
			board = board
					+ String.format(
							"%-1s %-7s %-1s %-7s %-1s %-7s %-1s %-7s %-1s %-7s %-1s %-7s %-1s %-7s %-1s\n",
							"|", rowArray[0].toString(),"|",rowArray[1].toString(), "|",
							rowArray[2].toString(), "|", rowArray[3].toString(), "|",
							rowArray[4].toString(), "|", rowArray[5].toString(), "|",
							rowArray[6].toString(),"|") + seperator;
		}
		return board;
	}
}