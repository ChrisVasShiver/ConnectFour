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
		for (int i = 0; i < currentBoard.MAXFIELDS; i++){
			if ((scanDiagonalLeftUp(m) + scanDiagonalRightDown(m) == 3) || (scanDiagonalLeftDown(m) + scanDiagonalRightUp(m))){
				return true;
			}
		}
		return false;
	}

	public int scanDiagonalLeftUp(Mark m) {
		for (int i = 0; i < currentBoard.MAXFIELDS; i-8) {
			int points = 0;
			if (i == 0 || i == 7 || i == 14 || i == 21 || i == 28 || i == 35
					|| i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6) {
				break;
			}
			else {
				if (currentBoard.getField(i).equals(m)){
					points++;
					scanDiagonalLeftUp(m);
					
				}
				
			}
		}
	}

	public int scanDiagonalLeftDown(Mark m) {

	}

	public int scanDiagonalRightUp(Mark m) {

	}

	public int scanDiagonalRightDown(Mark m) {

	}

	public boolean isWinner(Mark m) {
		return horizontalWin(m) || verticalWin(m) || diagonalWin(m);
	}

	public boolean hasWinner() {
		return isWinner(Mark.YELLOW) || isWinner(Mark.RED);
	}

	public boolean isGameover() {
		return isBoardFull() || hasWinner();
	}
}
