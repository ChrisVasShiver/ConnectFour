package main;

import strategy.Strategy;

/**
 * 
 * @author C. Visscher and D. Ye
 * 
 */
public class ComputerPlayer extends Player {
	
	/*@
	 private invariant theStrategy != null;
	 private invariant theMark == Mark.YELLOW || theMark == Mark.RED;
	 */
	private Strategy theStrategy;
	private Mark theMark;

	/**
	 * Creates a ComputerPlayer with the given mark and the given strategy.
	 * @param mark the given mark.
	 * @param strategy the given strategy.
	 */
	/*@
	 requires mark == Mark.YELLOW || mark == Mark.RED;
	 requires strategy != null;
	 ensures getMark() == mark;
	 ensures getStrategy() == strategy;
	 */
	public ComputerPlayer(Mark mark, Strategy strategy) {
		super(strategy.getName(), mark);
		assert mark != null;
		assert strategy != null;
		theStrategy = strategy;
		theMark = mark;
		
	}
	
	/**
	 * Get the strategy of this ComputerPlayer.
	 * @return returns the name of the strategy.
	 */
	/*@
	 ensures \result == getStrategy();
	 pure;
	 */
	public Strategy getStrategy(){
		return theStrategy;
	}

	@Override
	public int determineMove(Board board) {
		return theStrategy.determineMove(board, theMark);
	}

}
