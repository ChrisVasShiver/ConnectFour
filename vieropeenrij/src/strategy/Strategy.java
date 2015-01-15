package strategy;

import main.Board;
import main.Mark;

/**
 * 
 * @author C. Visscher en D. Ye
 * @version
 */
public interface Strategy {
	public String getName();
	public int determineMove(Board board, Mark m);
}
