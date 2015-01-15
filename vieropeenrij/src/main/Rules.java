package main;
/**
 * 
 * @author C. Visscher and D. Ye
 *
 */
public class Rules {
	
	private Board currentBoard;
	
	public Rules(Board board) {
		currentBoard = board;
	}
	
	public boolean isBoardFull() {
		for(int i = 0; i < Board.MAXFIELDS; i++) {
			if(currentBoard.getField(i) == Mark.EMPTY) {
				return false;
			}
		}
		return true;
	}
	
	public boolean horizontalWin(Mark m) {
		// TODO
		return false;
	}
	
	public boolean verticalWin(Mark m) {
		// TODO
		return false;
	}
	
	public boolean diagonalWin(Mark m) {
		// TODO
		return false;
	}
	
	public boolean hasWinner() {
		// TODO
		return false;
	}
	
	public boolean isGameover() {
		return isBoardFull() || hasWinner();
	}
}
