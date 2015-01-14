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
	
	public boolean hasWinner() {
		// TODO make body/make checks for diagonal/horizontal/vertical
		return false;
	}
	
	public boolean isGameover() {
		// TODO add winner check
		return isBoardFull();
	}
}
