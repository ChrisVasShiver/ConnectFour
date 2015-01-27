package tests;

import static org.junit.Assert.*;
import main.Board;
import main.Mark;
import main.Rules;

import org.junit.Before;
import org.junit.Test;
/**
 * A test for the rules class
 * @author C. Visscher and D. Ye
 *
 */
public class TestRules {
	private Board board;
	private Rules rules;

	@Before
	public void setUp() {
		board = new Board();
		rules = new Rules(board);
	}
	
	@Test
	public void testIsValidMove() {
		assertEquals("Test if negative field exists", false, rules.isValidMove(-1));
		assertEquals("Test if outofbound field exists", false, rules.isValidMove(42));
		for (int i = 0; i < Board.MAXFIELDS; i++) {
			assertEquals("Test fields exists", true, rules.isValidMove(i));
		}
	}
	
	@Test
	public void testIsBoardFull() {
		assertEquals("Test if an empty board is full", false, rules.isBoardFull());
		for(int i = 0; i < Board.MAXFIELDS; i++) {
			board.setField(i, Mark.RED);
		}
		assertEquals("Test if an full board is full", true, rules.isBoardFull());
	}
	
	@Test
	public void testHorizontalWin(){
		for(int h = 0; h < Board.MAXFIELDS; h++) {
			assertEquals("testHorizontal win is false for a empty field", false, rules.horizontalWin(Mark.RED, h));
		}
		for(int i = 0; i < Board.MAXFIELDS; i++) {
			board.setField(i, Mark.RED);
		}
		for(int j = 0; j < Board.MAXFIELDS; j++) {
			assertEquals("testHorizontal win is true for a full red field", true, rules.horizontalWin(Mark.RED, j));
		}
		for(int k = 0; k < Board.MAXFIELDS; k++) {
			board.setField(k, Mark.YELLOW);
		}
		for(int l = 0; l < Board.MAXFIELDS; l++) {
			assertEquals("testHorizontal win is true for a full yellow field", true, rules.horizontalWin(Mark.YELLOW, l));
		}
	}
	
	@Test
	public void testVerticalWin() {
		for (int h = 0; h < Board.MAXFIELDS; h++) {
			assertEquals("testHorizontal win is false for a empty field", false, rules.verticalWin(Mark.RED, h));
		}
		
		for (int i = 0; i < Board.MAXFIELDS; i++) {
			board.setField(i, Mark.RED);
		}
		for (int j = 0; j < 21; j++) {
			assertEquals("testHorizontal win is true for a full red field", true, rules.verticalWin(Mark.RED, j));
		}
		
		for (int col1 = 0; col1 < Board.WIDTH; col1++) {
			board.setField(board.matrixToIndex(3, col1), Mark.YELLOW);
		}
		for (int j = 0; j < 21; j++) {
			assertEquals("testHorizontal win is false if it is broken 4 in a row", false, rules.verticalWin(Mark.RED, j));
		}
		
		
		for (int k = 0; k < Board.MAXFIELDS; k++) {
			board.setField(k, Mark.YELLOW);
		}
		for (int l = 0; l < 21; l++) {
			assertEquals("testHorizontal win is true for a full yellow field", true, rules.verticalWin(Mark.YELLOW, l));
		}
		
		for (int col2 = 0; col2 < Board.WIDTH; col2++) {
			board.setField(board.matrixToIndex(3, col2), Mark.RED);
		}
		for (int m = 0; m < 21; m++) {
			assertEquals("testHorizontal win is false if it is broken 4 in a row", false, rules.verticalWin(Mark.YELLOW, m));
		}
	}
	
	@Test
	public void testScanDiagonalLeftUp() {
	}
	
	@Test
	public void testScanDiagonalLeftDown() {
		
	}
	
	@Test
	public void testScanDiagonalRightUp() {
		
	}
	
	@Test
	public void testScanDiagonalRightDown() {
		
	}
	
	@Test
	public void testDiagonalWin() {
		
	}
	@Test
	public void testIsWinner() {
		
	}
	
	@Test
	public void testGetHasWinner() {
		
	}
	
	@Test
	public void testGetGameOver() {
		
	}
	
	@Test
	public void testIsGameOVer() {
	}
	
	@Test
	public void testReset() {
		
	}
}
