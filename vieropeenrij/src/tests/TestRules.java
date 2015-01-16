package tests;

import static org.junit.Assert.*;
import main.Board;
import main.Mark;
import main.Rules;

import org.junit.Before;
import org.junit.Test;

public class TestRules {
	private Board board;
	private Rules rules;

	@Before
	public void setUp() {
		board = new Board();
		rules = new Rules(board);
		for (int i = 33; i > 0; i -= 8) {
			board.setField(i, Mark.RED);
		}
		board.setField(41, Mark.RED);
		board.setField(19, Mark.RED);
//		System.out.println(board.toString());
//		System.out.println(rules.scanDiagonalLeftUp(Mark.RED, 41));
	}

	@Test
	public void testDiagonalLeftUp() {
		assertEquals(3, rules.scanDiagonalLeftUp(Mark.RED, 41));
//		assertEquals(3, rules.scanDiagonalLeftUp(Mark.RED, 19));
	}
}
