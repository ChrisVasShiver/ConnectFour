package main;

import java.util.Scanner;

/**
 * 
 * @author C. Visscher and D. Ye
 * 
 */
public class HumanPlayer extends Player {

	/**
	 * Creates an instance of HumanPlayer.
	 * @param name the given name of the player.
	 * @param mark the given mark of the player.
	 */
	/*@
	 requires name != null;
	 requires mark == Mark.YELLOW || mark == Mark.RED;
	 ensures getName() == name;
	 ensures getMark() == mark;
	 */
	public HumanPlayer(String name, Mark mark) {
		super(name, mark);
		assert name != null;
		assert mark == Mark.YELLOW || mark == Mark.RED;
	}

	/*
	 * @ (non-Javadoc)
	 * 
	 * @see main.Player#determineMove(main.Board)
	 */
	@Override
	public int determineMove(Board board) {
		String message = "Enter your move";
		int value = readInteger(message);
		boolean isExistingField = false;
		isExistingField = board.isExistingField(value);
		while(!isExistingField) {
			System.out.println("You enterd a wrong move");
			value = determineMove(board);
			isExistingField = board.isExistingField(value);
		}
		value = board.dropMark(getMark(), value);
		if(value == -1) {
			System.out.println("the colum is already full, try another move");
			value = determineMove(board);
		}
		return value;
	}
	
	/**
	 * Reads a move from the input. Only used in the TUI.
	 * @param message the input.
	 * @return the move.
	 */
	/*@
	 requires message != null;
	 */
	public int readInteger(String message) {
		boolean isInteger = false;
		int value = 0;
		do {
			System.out.println(message);
			Scanner in = new Scanner(System.in);
			if(in.hasNextInt()) {
				value = in.nextInt();
				isInteger = true;
			} else {
				isInteger = false;
			}
		} while (!isInteger);
		return value;
	}
}
