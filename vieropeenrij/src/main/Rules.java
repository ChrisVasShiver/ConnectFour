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

	public boolean horizontalWin(Mark m) {
		for (int col = 0; col < currentBoard.HEIGHT; col++) {
			for (int row = 0; row < WINNERSBLOCK; row++) {
				int countMark = 0;
				for (int i = 0; i < WINNERSBLOCK; i++) {
					if (currentBoard.getField(
							currentBoard.matrixToIndex(col + i, row)).equals(m)) {
						countMark++;
					}
					if (countMark == WINNERSBLOCK) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean verticalWin(Mark m) {
		for (int row = 0; row < currentBoard.WIDTH; row++) {
			for (int col = 0; col < WINNERSBLOCK; col++) {
				int countMark = 0;
				for (int i = 0; i < WINNERSBLOCK; i++) {
					if (currentBoard.getField(
							currentBoard.matrixToIndex(col, i * 7)).equals(m)) {
						countMark++;
					}
					if (countMark == WINNERSBLOCK) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean diagonalWin(Mark m) {
		// TODO
		return false;
	}

	public boolean hasWinner() {
		return horizontalWin() || verticalWin() || diagonalWin();
	}

	public boolean isGameover() {
		return isBoardFull() || hasWinner();
	}
}
