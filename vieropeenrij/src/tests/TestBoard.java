package tests;

import static org.junit.Assert.*;
import main.Board;
import main.Rules;

import org.junit.Before;
import org.junit.Test;

public class TestBoard {
	private Board board;
	private Rules rules;
	@Before
	public void setUp() {
		board = new Board();
		rules = new Rules(board);
	}

	@Test
	public void testInitialBoard() {
		fail("Not yet implemented");
	}
	
	@Test
	public void test() {
		
	}
}
