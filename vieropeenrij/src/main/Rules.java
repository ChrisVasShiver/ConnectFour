package main;

/**
 * 
 * @author C. Visscher and D. Ye
 *
 */
public class Rules {
	
	public static final int WINNERSBLOCK = 4;
	
	private Board currentBoard;
	private boolean gameover = false;
	private boolean hasWinner = false;
	
	public Rules(Board board) {
		currentBoard = board;
	}
	
	/**
	 * Checks if the move is valid.
	 * @param i the entered move.
	 * @return return true if i >= 0 && i < 42. Return false if it is something else.
	 */
	/*@
	 	requires 0 <= i && i < Board.MAXFIELDS;
//		ensures (\forall int i; 0 <= i && i < Board.MAXFIELDS; Board.isExistingField(i) == true) \result == true;
	 	pure;
	 */
	public boolean isValidMove(int i) {
		return currentBoard.isExistingField(i);
	}
	
	/**
	 * Checks if the board is full. 
	 * @return returns true if board is full. Returns false if board has atleast one Mark.EMPTY spot.
	 */
	/*@
//	 	ensures (\forall int i; 0 <= i && i < Board.MAXFIELDS; Board.isEmptyField(i) == true) ==> \result == true;
	 	pure
	 */
	public boolean isBoardFull() {
		//@ loop_invariant 0 <= i && i < Board.MAXFIELDS;
		for (int i = 0; i < Board.MAXFIELDS; i++) {
			if (!currentBoard.isEmptyField(i)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Determines if the move with the given Mark will lead to vertical win.
	 * @param m the Mark of the move.
	 * @param index the place where the Mark is placed
	 * @return returns true when there is a vertical four in a row. Returns false when there is no four in a row.
	 */
	/*@
		requires m != null;
		requires 0 <= index && index < Board.MAXFIELDS;
		ensures \result == true || false;
		pure;
	 */
	public boolean verticalWin(Mark m, int index) {
		assert m != null;
		assert 0 <= index && index < currentBoard.MAXFIELDS;
		int col = currentBoard.indexToMatrix(index)[1];
		//@ loop_invariant 0 <= row && row < WINNERSBLOCK;
		for (int row = 0; row < WINNERSBLOCK; row++) {
			int countMark = 0;
			if (currentBoard.getField(currentBoard.matrixToIndex(row,col))
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
	 * Determines if the move with the given Mark will lead to horizontal win.
	 * @param m the Mark of the move.
	 * @param index the place where the Mark is placed
	 * @return returns true when there is a horizontal four in a row. Returns false when there is no four in a row.
	 */
	/*@
 		requires m != null;
 		requires 0 <= index && index < Board.MAXFIELDS;
		ensures \result == true || false;
		pure;
	 */
	public boolean horizontalWin(Mark m, int index) {
		assert m != null;
		assert 0 <= index && index < currentBoard.MAXFIELDS;
		int row = currentBoard.indexToMatrix(index)[0];
		//@ loop_invariant 0 <= col && col < WINNERSBLOCK;
		for (int col = 0; col < WINNERSBLOCK; col++) {
			int countMark = 0;
			if (currentBoard.getField(currentBoard.matrixToIndex(row,col))
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
	 	requires 0 <= index && index < Board.MAXFIELDS;
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
	 requires 0 < index && index < Board.MAXFIELDS;
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
	 requires 0 < index && index < Board.MAXFIELDS;
	 ensures 0 <= \result && \result <= WINNERSBLOCK;
	 pure;
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
	 requires 0 < index && index < Board.MAXFIELDS;
	 ensures 0 <= \result && \result <= WINNERSBLOCK;
	 pure;
	 */
	public int scanDiagonalRightUp(Mark m, int index) {
		assert m != null;
		assert 0 <= index && index < currentBoard.MAXFIELDS;
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
	 requires 0 <= index && index < Board.MAXFIELDS;
	 ensures 0 <= \result && \result <= WINNERSBLOCK;
	 pure;
	 */
	public int scanDiagonalRightDown(Mark m, int index) {
		assert m != null;
		assert 0  <= index && index < currentBoard.MAXFIELDS;
		int points = 0;
		for (int i = index; i > 0; i += 8) {
			if (i == 6 || i == 13 || i == 20 || i == 27 || i == 34 || i == 35
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
	 * Checks if there is a winner after a move is done.
	 * @param m the Mark of the move.
	 * @param index the place where the Mark is placed
	 * @return true if there is a winner. False if there is not a winner.
	 */
	/*@
	 	requires m != null;
		requires 0 <= index && index < Board.MAXFIELDS;
		ensures (horizontalWin(m, index) == true || verticalWin(m,index) == true || diagonalWin(m, index) == true) ==> \result == true && getGameOver() == true;
		ensures (horizontalWin(m, index) == false || verticalWin(m,index) == false || diagonalWin(m, index) == false) ==> \result == false && getGameOver() == false;
	 	pure
	 */
	public boolean isWinner(Mark m, int index) {
		assert m != null;
		assert 0 <= index && index < Board.MAXFIELDS;
		if (horizontalWin(m, index) || verticalWin(m, index) || diagonalWin(m, index)){
			hasWinner = true;
			return true;
		} else {
			return false;
		}
	 }
	 
	/**
	 * Checks if there is a winner. This method exists to differentiate, when the board is full, if there is a winner or a full board.
	 * @return true if there is a winner. False if there is not a winner.
	 */
	/*@
	 	ensures \result == getHasWinner();
	 	pure;
	 */
	public boolean getHasWinner() {
		return hasWinner;
	}
	
	/**
	 * Checks if the game is over because there is a winner and/or the board is full.
	 * @return true if there is a winner or the board is full. Return false if there is no winner or the board is not full.
	 */
	/*@
	 	ensures \result == getGameOver();
	 	pure;
	 */
	public boolean getGameOver() {
		return gameover;
	}
	 
	/**
	 * 
	 * @param m
	 * @param index
	 */
	/*@
	 	requires m != null;
		requires 0 <= index && index < Board.MAXFIELDS;
		ensures (isWinner(m, index) == true || isBoardFull() == true) ==> getGameOver() == true;
		ensures (isWinner(m, index) == false || isBoardFull() == false) ==> getGameOver() == false;
	 */
	public void isGameOver(Mark m, int index) {
		if (isWinner(m, index) || isBoardFull()) {
			gameover = true;
		}
	}
	 
	/**
	 * Resets the winner and gameover variables to false.
	 */
	/*@
	 	ensures getGameOver() == false;
	 	ensures getHasWinner() == false;
	 */
	 public void reset() {
		 gameover = false;
		 hasWinner = false;
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
		 System.out.println(rules.scanDiagonalLeftUp(Mark.RED, 19));}

}
