package main;

/**
 * 
 * @author C. Visscher and D. Ye
 *
 */
public class Rules {

	private Board currentBoard;
	public static final int WINNERSBLOCK = 4;

	public Rules(Board board) {
		currentBoard = board;
	}
	
	public boolean isValidMove(int i) {
		return currentBoard.isEmptyField(i);
	}
	
	/**
	 * Checks if the board is filled with either Mark.RED or Mark.Yellow.
	 * @return returns true if board is full. Returns false if board has atleast one Mark.EMPTY spot.
	 */
	public boolean isBoardFull() {
		for (int i = 0; i < Board.MAXFIELDS; i++) {
			if (currentBoard.getField(i) == Mark.EMPTY) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Determines if the move with the given Mark will lead to horizontal win.
	 * @param m the Mark of the move.
	 * @param index the place where the Mark is placed
	 * @return returns true when there is a horizontal four in a row. Returns false when there is no four in a row.
	 */
	/*@
		requires m != null;
		requires 0 <= index && index < 42;
		//ensures countMark == WINNERSBLOCK ==> \result == true;
		//ensures countMark != WINNERSBLOCK ==> \result == false;
		pure;
	 */
	public boolean horizontalWin(Mark m, int index) {
		assert m != null;
		assert 0 <= index && index < currentBoard.MAXFIELDS;
		int col = currentBoard.indexToMatrix(index)[1];
		//@ loop_invariant 0 <= row && row < WINNERSBLOCK;
		for (int row = 0; row < WINNERSBLOCK; row++) {
			int countMark = 0;
			if (currentBoard.getField(currentBoard.matrixToIndex(col, row))
					.equals(m)) {
				countMark++;
			}
			if (countMark == WINNERSBLOCK) {
				return true;

			}
		}
		return false;
	}
	
	
	/**
	 * Determines if the move with the given Mark will lead to vertical win.
	 * @param m the Mark of the move.
	 * @param index the place where the Mark is placed
	 * @return returns true when there is a vertical four in a row. Returns false when there is no four in a row.
	 */
	/*@
 		requires m != null;
 		requires 0 <= index && index < 42;

		//ensures countMark == WINNERSBLOCK ==> \result == true;
		//ensures countMark != WINNERSBLOCK ==> \result == false;
		pure;
	 */
	public boolean verticalWin(Mark m, int index) {
		assert m != null;
		assert 0 <= index && index < currentBoard.MAXFIELDS;
		int row = currentBoard.indexToMatrix(index)[1];
		//@ loop_invariant 0 <= col && col < WINNERSBLOCK;
		for (int col = 0; col < WINNERSBLOCK; col++) {
			int countMark = 0;
			if (currentBoard.getField(currentBoard.matrixToIndex(col, row))
					.equals(m)) {
				countMark++;
			}
			if (countMark == WINNERSBLOCK) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Determines if the move with the given Mark will lead to diagonal win.
	 * @param m the Mark of the move.
	 * @param index the place where the Mark is placed
	 * @return returns true when there is a diagonal four in a row. Returns false when there is no four in a row.
	 */
	/*@
	 	requires m != null;
	 	requires 0 <= index && index < 42;
	 	ensures \result ==> (scanDiagonalLeftUp(m,index) + scanDiagonalRightDown(m, index) == 3 || scanDiagonalLeftDown(m, index) + scanDiagonalRightUp(m, index) == 3) ==> true;
	 	pure;
	 */
	public boolean diagonalWin(Mark m, int index) {
		assert m != null;
		assert 0 <= index && index < currentBoard.MAXFIELDS;
		//loop_invariant 0 <= i && i < currentBoard.MAXFIEDS;
		for (int i = 0; i < currentBoard.MAXFIELDS; i++) {
			if ((scanDiagonalLeftUp(m, index) + scanDiagonalRightDown(m, index) == 3)
					|| (scanDiagonalLeftDown(m, index)
							+ scanDiagonalRightUp(m, index) == 3)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Scan the indices left up of the Mark placement. Returns a value representing the connecting Marks in the direction.
	 * @param m the Mark of the move.
	 * @param index the place where the Mark is placed
	 * @return return an integer between 0 and 3. Based on the amount of equal Marks beneath index. Return 3 means four Marks are connected.
	 */
	
	/*@
	 requires m != null;
	 requires 0 < index && index < 42;
	 ensures 0 <= \result && \result <= WINNERSBLOCK;
	 pure;
	 */
	public int scanDiagonalLeftUp(Mark m, int index) {
		assert m != null;
		assert 0 < index && index < currentBoard.MAXFIELDS;
		int points = 0;
		for (int i = index; i > 0; i -= 8) {
			if (i == 0 || i == 7 || i == 14 || i == 21 || i == 28 || i == 35
					|| i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6) {
				break;
			} else {
				if (currentBoard.getField(i).equals(m) && points != 3) {
					points++;
				} else
					break;
			}
		}
		return points;
	}

	/**
	 * Scan the indices left down of the Mark placement. Returns a value representing the connecting Marks in the direction.
	 * @param m the Mark of the move.
	 * @param index the place where the Mark is placed
	 * @return return an integer between 0 and 3. Based on the amount of equal Marks beneath index. Return 3 means four Marks are connected.
	 */
	/*@
	 requires m != null;
	 requires 0 < index && index < 42;
	 ensures 0 <= \result && \result <= WINNERSBLOCK;
	 pure
	 */
	public int scanDiagonalLeftDown(Mark m, int index) {
		assert m != null;
		assert 0 < index && index < currentBoard.MAXFIELDS;
		int points = 0;
		for (int i = index; i > 0; i += 6) {
			if (i == 0 || i == 7 || i == 14 || i == 21 || i == 28 || i == 35
					|| i == 36 || i == 37 || i == 38 || i == 39 || i == 40
					|| i == 41) {
				break;
			} else {
				if (currentBoard.getField(i).equals(m) && points != 3) {
					points++;
				} else
					break;
			}
		}
		return points;
	}

	/**
	 * Scan the indices right up of the Mark placement. Returns a value representing the connecting Marks in the direction.
	 * @param m the Mark of the move.
	 * @param index the place where the Mark is placed
	 * @return return an integer between 0 and 3. Based on the amount of equal Marks beneath index. Return 3 means four Marks are connected.
	 */
	/*@
	 requires m != null;
	 requires 0 < index && index < 42;
	 ensures 0 <= \result && \result <= WINNERSBLOCK;
	 pure;
	 */
	public int scanDiagonalRightUp(Mark m, int index) {
		assert m != null;
		assert 0 < index && index < currentBoard.MAXFIELDS;
		int points = 0;
		for (int i = index; i > 0; i -= 6) {
			if (i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 5
					|| i == 6 || i == 13 || i == 20 || i == 27 || i == 34
					|| i == 41) {
				break;
			} else {
				if (currentBoard.getField(i).equals(m) && points != 3) {
					points++;
				} else
					break;
			}
		}
		return points;
	}

	/**
	 * Scan the indices right down of the Mark placement. Returns a value representing the connecting Marks in the direction.
	 * @param m the Mark of the move.
	 * @param index the place where the Mark is placed
	 * @return return an integer between 0 and 3. Based on the amount of equal Marks beneath index. Return 3 means four Marks are connected.
	 */
	/*@
	 requires m != null;
	 requires 0 < index && index < 42;
	 ensures 0 <= \result && \result <= WINNERSBLOCK;
	 pure;
	 */
	public int scanDiagonalRightDown(Mark m, int index) {
		assert m != null;
		assert 0 < index && index < currentBoard.MAXFIELDS;
		int points = 0;
		for (int i = index; i > 0; i += 8) {
			if (i == 6 || i == 13 || i == 20 || i == 27 || i == 34 || i == 35
					|| i == 36 || i == 37 || i == 38 || i == 39 || i == 40
					|| i == 41) {
				break;
			} else {
				System.out.println(currentBoard.getField(i).equals(m));
				if (currentBoard.getField(i).equals(m) && points != 3) {
					points++;
				} else
					break;
			}
		}
		 return points;
	 }

	 public boolean isWinner(Mark m, int index) {
		 return horizontalWin(m, index) || verticalWin(m, index) || diagonalWin(m, index);
	 }
	 
	 public boolean isGameover(Mark m, int index) {
		 return isBoardFull() || isWinner(m, index);
	 }
	 
	/**
	 * Checks the lowest available index in the column, when a column is selected. It will drop the Mark into the column if it is free.
	 * @param m the Mark of the move.
	 * @param index the selected column where a Mark is attempted to be placed.
	 * @return returns -1 if selected column does not have any free index. Else returns the lowest free index of the column.
	 */
	 /*@
	  	requires m != null;
	  	requires 0 <= index && index < 42;
	  	ensures \result  -1 <= \result && \result < 42;
		pure;
	  */
	 public int dropMark(Mark m, int index){
		 assert m != null;
		 assert 0 <= index && index < currentBoard.MAXFIELDS;
		 int col = currentBoard.indexToMatrix(index)[1];
		 int placement = -1;
		 for (int i = 0; i < currentBoard.HEIGHT; i++){
			 if(currentBoard.getField(col).equals(Mark.EMPTY)){
				 placement = col;
			 }
			 col = col + 7;
		 }
		 return placement;
	 }

	 public static void main(String[] args) {
		 Board board = new Board();
		 Rules rules = new Rules(board);

		 for (int i = 33; i > 25; i -= 8) {
			 board.setField(i, Mark.RED);
		 }
		 board.setField(41, Mark.RED);
		 System.out.println(board.toString());
		 System.out.println(rules.scanDiagonalLeftUp(Mark.RED, 41));
		 System.out.println(rules.scanDiagonalLeftUp(Mark.RED, 19));
	 }

}
