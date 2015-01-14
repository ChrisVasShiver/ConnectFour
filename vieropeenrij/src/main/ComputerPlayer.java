package main;

import strategy.Strategy;

/**
 * 
 * @author C. Visscher and D. Ye
 * 
 */
public class ComputerPlayer extends Player {

	public ComputerPlayer(Mark mark, Strategy strategy) {
		super(strategy.getName(), mark);
	}

	@Override
	public int doMove(Board board) {
		// TODO Auto-generated method stub
		return 0;
	}

}
