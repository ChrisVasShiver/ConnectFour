package strategy;

import java.util.HashSet;
import java.util.Set;

import main.Board;
import main.Game;
import main.Mark;
import main.Rules;

public class SmartStrategy implements Strategy{

	@Override
	public String getName() { 
		return "Smart";
	}

	@Override
	public int determineMove(Game game, Mark m) {
		Board testMoveBoard = game.getBoard().copyBoard();
		Rules testRules = new Rules(testMoveBoard);
		
		for (int i = 0; i < Board.MAXFIELDS; i++) {
			testMoveBoard = game.getBoard().copyBoard();
			testRules = new Rules(testMoveBoard);
			if (testMoveBoard.getField(i) == Mark.EMPTY) {
				testMoveBoard.setField(i, m);
			}
			if (testRules.getHasWinner()) {
				return i;
			}
		}
		
		for (int j = 0; j < Board.MAXFIELDS; j++) {
			testMoveBoard = game.getBoard().copyBoard();
			testRules = new Rules(testMoveBoard);
			if (testMoveBoard.getField(j) == Mark.EMPTY) {
				testMoveBoard.setField(j, m.opponent());
			}
			if (testRules.getHasWinner()) {
				return j;
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
