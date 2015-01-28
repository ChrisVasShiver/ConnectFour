package strategy;

import java.util.HashSet;
import java.util.Set;

import main.Board;
import main.Game;
import main.Mark;

public class SmartStrategy implements Strategy{

	@Override
	public String getName() { 
		return "Smart";
	}

	@Override
	public int determineMove(Game game, Mark m) {
		Board testMoveBoard = game.getBoard().copyBoard();
		for (int i = 0; i < Board.MAXFIELDS; i++) {
			if (testMoveBoard.getField(i) == Mark.EMPTY) {
				testMoveBoard.setField(i, m);
			}
			if (game.getRules().getHasWinner()) {
				return i;
			}
		}
		
		Set<Integer> validmoves = new HashSet<Integer>();
		for (int i = 0; i < game.getBoard().MAXFIELDS; i++) {
			if (game.getBoard().isEmptyField(i)) {
				validmoves.add(i);
			}
		}
		Object[] array = new Object[validmoves.size()];
		array = validmoves.toArray();
		return (int) array[(int) Math.floor(Math.random()*array.length)];
	}
}
