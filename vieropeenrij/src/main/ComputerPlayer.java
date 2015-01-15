package main;

import strategy.Strategy;

/**
 * 
 * @author C. Visscher and D. Ye
 * 
 */
public class ComputerPlayer extends Player {
	
	private Strategy theStrategy;
	private Mark theMark;

	public ComputerPlayer(Mark mark, Strategy strategy) {
		super(strategy.getName(), mark);
		theStrategy = strategy;
		theMark = mark;
		
	}

	@Override
	public int determineMove(Board board) {
		return theStrategy.determineMove(board, theMark);
	}

}
