package main;

/**
 * 
 * @author Christiaan Visscher and Dylan Ye
 * 
 */

public class Board {
	
	public static final int WIDTH = 7;
	public static final int HIGH = 6;
	public static final int MAXFIELDS = 42;

	private Mark[] fields;
	
	public Board() {
		fields = new Mark[MAXFIELDS];
		reset();
	}
	
	public Board copyBoard() {
		Board boardCopy = new Board();
		for(int i = 0; i < MAXFIELDS; i++) {
			boardCopy.setField(i, getField(i));
		}
		return boardCopy;
	}
	
	public Mark getField(int i) {
		return fields[i];
	}
	
	public Mark[] getFields() {
		return fields;
	}
	
	public void setField(int i, Mark m) {
		fields[i] = m;
	}
		
	public void reset() {
		for(int i = 0; i < MAXFIELDS; i++) {
			setField(i, Mark.EMPTY);
		}
	}
	
	public String toString() {
		String boardRepresentation = "";
		boardRepresentation = "1	2	3	4	5	6	7";
		for(int i = 0 ; i < MAXFIELDS; i++) {
			if((i % 7) == 0) {
				boardRepresentation = boardRepresentation + "\n";
			}
			boardRepresentation = boardRepresentation + " " + getField(i).toString();
		}
		return boardRepresentation;
	}
}
