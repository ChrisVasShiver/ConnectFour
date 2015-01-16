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

	public boolean isBoardFull() {
		for (int i = 0; i < Board.MAXFIELDS; i++) {
			if (currentBoard.getField(i) == Mark.EMPTY) {
				return false;
			}
		}
		return true;
	}

	public boolean horizontalWin(Mark m, int index) {
		int col = currentBoard.indexToMatrix(index)[1];
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

	public boolean verticalWin(Mark m, int index) {
		int row = currentBoard.indexToMatrix(index)[1];
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

	public boolean diagonalWin(Mark m, int index) {
		for (int i = 0; i < currentBoard.MAXFIELDS; i++) {
			if ((scanDiagonalLeftUp(m, index) + scanDiagonalRightDown(m, index) == 3)
					|| (scanDiagonalLeftDown(m, index)
							+ scanDiagonalRightUp(m, index) == 3)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * @ requires m != null; requires 0 < index && index < 42; ensures 0 <=
	 * \result && \result => 3;
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

	/*
	 * @ requires m != null; requires 0 < index && index < 42; ensures 0 <=
	 * \result && \result => 3;
	 */
	public int scanDiagonalLeftDown(Mark m, int index) {
		assert m != null;
		assert 0 < index && index < 42;
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

	/*
	 * @ requires m != null; requires 0 < index && index < 42; ensures 0 <=
	 * \result && \result => 3;
	 */
	public int scanDiagonalRightUp(Mark m, int index) {
		assert m != null;
		assert 0 < index && index < 42;
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

	/*
	 * @ requires m != null; requires 0 < index && index < 42; ensures 0 <=
	 * \result && \result => 3;
	 */public int scanDiagonalRightDown(Mark m, int index) {
		assert m != null;
		assert 0 < index && index < 42;
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
