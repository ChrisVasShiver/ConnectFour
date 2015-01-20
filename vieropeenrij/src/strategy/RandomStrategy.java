package strategy;

import java.util.HashSet;
import java.util.Set;

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
		Set<Integer> validmoves = new HashSet<Integer>();
		for (int i = 0; i < 9; i++) {
			if (board.getField(i).equals(Mark.EMPTY)) {
				validmoves.add(i);
			}
		}
		Object[] array = new Object[validmoves.size()];
		array = validmoves.toArray();
		return (int) array[(int) Math.floor(Math.random()*array.length)];
	}
}
