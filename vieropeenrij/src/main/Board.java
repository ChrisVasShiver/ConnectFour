package main;

/**
 * 
 * @author C. Visscher and D. Ye
 * 
 */
public class Board {
	
	public static final int WIDTH = 7;
	public static final int HIGH = 6;
	public static final int MAXFIELDS = 42;

	private Mark[] fields;
	private Rules rules;
	
	public Board() {
		fields = new Mark[MAXFIELDS];
		rules = new Rules(this);
		reset();
	}
	
	public Board copyBoard() {
		Board boardCopy = new Board();
		for(int i = 0; i < MAXFIELDS; i++) {
			boardCopy.setField(i, getField(i));
		}
		return boardCopy;
	}
	
	public Rules getRules() {
		return rules;
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
		for(int i = 0 ; i < MAXFIELDS; i++) {
			if((i % 7) == 0) {
				boardRepresentation = boardRepresentation + "\n";
			}
			boardRepresentation = boardRepresentation + " " + getField(i).toString();
		}
		return boardRepresentation;
	}
}
