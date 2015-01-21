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
 		private invariant (\forall int i; 0 <= i && i < Board.MAXFIELDS; fields[i] == Mark.EMPTY || fields[i] == Mark.RED || fields[i] == Mark.YELLOW);
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
		return (row * WIDTH) + col;
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
	 * Returns the value of a field.
	 * @param i the index you want to retrieve the field from.
	 * @return the mark/value of the field
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
	 * @return true if getField equals Mark.Empty, otherwise false
	 */
	/*@
	 	requires 0 <= i && i < MAXFIELDS;
	 	ensures getField(i) == Mark.EMPTY ==> \result == true || getField(i) != Mark.EMPTY ==> \result == false;
	 	pure;
	 */
	public boolean isEmptyField(int i) {
		if(getField(i) == Mark.EMPTY) {
			return true;
		} else {
		return false;
		}
	}
	
	/**
	 * Check if the given index is an existing field
	 * @param i the index that needs to be checked
	 * @return true if i is a integer between 0 and 41, otherwise false
	 */
	/*@ 
	 	ensures 0 <= i && i < MAXFIELDS ==> \result == true || i < 0 && i >= MAXFIELDS ==> \result == false;
	 	pure;
	 */
	public boolean isExistingField(int i) {
		if(0 <= i && i < MAXFIELDS) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Sets a field to the given mark
	 * @param i the index of the field
	 * @param m the mark the value of the field will be set too
	 */
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
	
	/**
	 * Resets the board by setting all the fields to empty
	 */
	/*@
	 	ensures (\forall int i; 0 <= i && i < Board.MAXFIELDS; this.getField(i) == Mark.EMPTY);
	 */
	public void reset() {
		//@loop_invariant 0 <= i && i < Board.MAXFIELDS;
		for(int i = 0; i < MAXFIELDS; i++) {
			setField(i, Mark.EMPTY);
		}
	}
	
	/**
	 * Checks the lowest available index in the column, when a column is selected. It will drop the Mark into the column if it is free.
	 * @param m the Mark of the move.
	 * @param index the selected column where a Mark is attempted to be placed.
	 * @return returns -1 if selected column does not have any free index. Else returns the lowest free index of the column.
	 */
	 /*@
	  	requires m != null;
	  	requires 0 <= index && index < Board.MAXFIELDS;
	  	ensures isExistingField(index)==> 0 <= \result && \result < 42;
		pure;
	  */
	public int dropMark(Mark m, int index){
		assert m != null;
		assert 0 <= index && index < MAXFIELDS;
		int col = indexToMatrix(index)[1];
		int placement = -1;
		if (isExistingField(index)){
			for (int row = 0; row < HEIGHT; row++){
 				if(isEmptyField(matrixToIndex(row, col))){
					placement = matrixToIndex(row, col);
				}
			}
		}
		return placement;
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