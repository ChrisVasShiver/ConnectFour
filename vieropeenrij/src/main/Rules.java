package main;

/**
 * 
 * @author C. Visscher and D. Ye
 *
 */
public class Rules {

	/*@
	 	invariant WINNERSBLOCK == 4;
	 */
	public static final int WINNERSBLOCK = 4;

	/*@
	 	private invariant currentBoard != null;
	 */
	private Board currentBoard;
	private boolean gameover = false;
	private boolean hasWinner = false;

	/*@
	 	requires board != null;
	 	ensures getCurrentBoard() == board;
	 */
	public Rules(Board board) {
		assert board != null;
		currentBoard = board;
	}

	/**
	 * Checks if the move is valid.
	 * @param i the entered move.
	 * @return return true if i >= 0 && i < 42. Return false if it is something else.
	 */
	/*@
	 	requires 0 <= i && i < Board.MAXFIELDS;
		ensures (\forall int j; 0 <= j && j < Board.MAXFIELDS ==> \result == true);
	 */
	public boolean isValidMove(int i) {
		return currentBoard.isExistingField(i);
	}

	/**
	 * Checks if the board is full. 
	 * @return returns true if board is full. Returns false if board has atleast one Mark.EMPTY spot.
	 */
	/*@
	 	ensures (\forall int j; 0 <= j && j < Board.MAXFIELDS; getCurrentBoard().isEmptyField(j) == true) ==> \result == true;
	 	ensures (\forall int j; 0 > j && j > Board.MAXFIELDS; getCurrentBoard().isEmptyField(j) == false) ==> \result == false;
	 	pure;
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
	 * Determines if the move with the given Mark will lead to a horizontal win.
	 * @param m the Mark of the move.
	 * @param index the place where the Mark is placed
	 * @return returns true when there is a horizontal four in a row. Returns false when there is no four in a row.
	 */
	/*@
	 	requires m != null;
		requires 0 <= index && index < Board.MAXFIELDS;
		ensures getHasWinner() == true ==> \result == true;
		ensures getHasWinner() == false ==> \result == false;
		pure;
	 */
	public boolean horizontalWin(Mark m, int index) {
		assert m != null;
		assert 0 <= index && index < Board.MAXFIELDS;
		int row = currentBoard.indexToMatrix(index)[0];
		int startingCol = currentBoard.indexToMatrix(index)[1];
		int countL = 0;
		int countR = 0;
		for (int colL = startingCol ; colL >= 0; colL--) {
			if(currentBoard.getField(currentBoard.matrixToIndex(row, colL)).equals(m)) {
				countL++;
			} else {
				break;
			}
		}
		for (int colR = startingCol; colR < Board.WIDTH; colR++) {
			if(currentBoard.getField(currentBoard.matrixToIndex(row, colR)).equals(m)) {
				countR++;
			} else {
				break;
			}
		}
		if(countL + countR - 1 >= WINNERSBLOCK) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Determines if the move with the given Mark will lead to a vertical win.
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
		assert 0 <= index && index < Board.MAXFIELDS;
		int col = currentBoard.indexToMatrix(index)[1];
		int countMark = 0;
		for (int row = 0; 0 <= row && row < Board.MAXFIELDS; row += 7) {
			if (currentBoard.getField(currentBoard.matrixToIndex(row, col))
					.equals(m) && countMark != WINNERSBLOCK) {
				countMark++;
			}
		}
		if (countMark == WINNERSBLOCK) {
			return true;
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
		assert 0 <= index && index < Board.MAXFIELDS;
		//loop_invariant 0 <= i && i < currentBoard.MAXFIEDS;
		for (int i = 0; i < Board.MAXFIELDS; i++) {
			if ((scanDiagonalLeftUp(m, index) + scanDiagonalRightDown(m, index) >= WINNERSBLOCK)
					|| (scanDiagonalLeftDown(m, index)
							+ scanDiagonalRightUp(m, index) >= WINNERSBLOCK)) {
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
		assert 0 < index && index < Board.MAXFIELDS;
		int points = 0;
		for (int i = index; i > 0; i -= 8) {
			if (i % 7 == 0 || i >= 1 && i <= 7) {
				break;
			} else {
				if (currentBoard.getField(i).equals(m) && points != 4) {
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
		assert 0 < index && index < Board.MAXFIELDS;
		int points = 0;
		for (int i = index; i > 0; i += 6) {
			if (i % 7 == 0 || i >= 35 && i <= 41) {
				break;
			} else {
				if (currentBoard.getField(i).equals(m) && points != 4) {
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
		assert 0 <= index && index < Board.MAXFIELDS;
		int points = 0;
		for (int i = index; i > 0; i -= 6) {
			if (i >= 0 && i <= 6 || i == 13 || i == 20 || i == 27 ||  i == 34 || i == 41){
				break;
			} else {
				if (currentBoard.getField(i).equals(m) && points != 4) {
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
		assert 0  <= index && index < Board.MAXFIELDS;
		int points = 0;
		for (int i = index; i > 0; i += 8) {
			if (i >= 34 && i <= 41 || i == 6 || i == 13 || i == 20 || i == 27){
				break;
			} else {
				if (currentBoard.getField(i).equals(m) && points != 4) {
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
	 * This method only exists to help with JML in Rules() and isBoardFull().
	 * @return the currentBoard variable.
	 */
	/*@
	 	ensures \result == getCurrentBoard();
	 	pure;
	 */
	public Board getCurrentBoard(){
		return currentBoard;
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

		for (int i = 33; i > 0; i -= 8) {
			board.setField(i, Mark.RED);
		}
		for(int i = 35; i > 0 ; i-=6){
			board.setField(i, Mark.YELLOW);
		}
		for(int i = 36; i < 42; i++){
			board.setField(i, Mark.RED);
		}
		for(int i = 0; i < 42; i+=7){
			board.setField(i, Mark.YELLOW);
		}
		board.setField(41, Mark.RED);
		System.out.println(board.toString());
		System.out.println(rules.scanDiagonalLeftUp(Mark.RED, 41));
		System.out.println(rules.scanDiagonalLeftDown(Mark.YELLOW, 5));
		System.out.println("Scan horizontal " + rules.horizontalWin(Mark.RED, 38));
		System.out.println(board.matrixToIndex(8, 0));
		System.out.println("Scan vertical " + rules.verticalWin(Mark.YELLOW, 0));

	}

}
