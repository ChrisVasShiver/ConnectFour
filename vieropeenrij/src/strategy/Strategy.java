package strategy;

import main.Game;
import main.Mark;

/**
 * 
 * @author C. Visscher en D. Ye
 * @version
 */
public interface Strategy {
	public String getName();
	public int determineMove(Game game, Mark m);
}
