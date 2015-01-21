package main;

import java.util.Scanner;

/**
 * 
 * @author C. Visscher and D. Ye
 * 
 */
public class HumanPlayer extends Player {

	public HumanPlayer(String name, Mark mark) {
		super(name, mark);
	}

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
		return value;
	}
	
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
