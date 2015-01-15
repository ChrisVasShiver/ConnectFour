package strategy;

import main.Board;
import main.Mark;

/**
 * 
 * @author C. Visscher and D. Ye
 * 
 */
public class RandomStrategy implements Strategy{

	@Override
	public String getName() {
		return "Easy";
	}

	@Override
	public int determineMove(Board board, Mark m) {
		// TODO Auto-generated method stub
		return 0;
	}
}
