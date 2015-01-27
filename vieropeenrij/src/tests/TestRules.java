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
	public void testInitialState() {
		assertEquals("getBoard", true, rules.getCurrentBoard().equals(board));
		assertEquals("Initial hasWinner", false, rules.getHasWinner());
		assertEquals("Test initial gameOver", false, rules.getGameOver());
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
			assertEquals("test vertial win is false for a empty field", false, rules.verticalWin(Mark.RED, h));
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
			assertEquals("test vetical win is false if it is broken 4 in a row", false, rules.verticalWin(Mark.RED, j));
		}
		
		
		for (int k = 0; k < Board.MAXFIELDS; k++) {
			board.setField(k, Mark.YELLOW);
		}
		for (int l = 0; l < 21; l++) {
			assertEquals("test vertical win is true for a full yellow field", true, rules.verticalWin(Mark.YELLOW, l));
		}
		
		for (int col2 = 0; col2 < Board.WIDTH; col2++) {
			board.setField(board.matrixToIndex(3, col2), Mark.RED);
		}
		for (int m = 0; m < 21; m++) {
			assertEquals("test vertical win is false if it is broken 4 in a row", false, rules.verticalWin(Mark.YELLOW, m));
		}
	}
	
	@Test
	public void testScanDiagonalLeftUp() {
		for(int i = 0; i < Board.MAXFIELDS; i++) {
			assertEquals("Test empty field", 0, rules.scanDiagonalLeftUp(Mark.RED, i));
		}
		
		for(int j = 0; j < Board.MAXFIELDS; j = j + 8) {
			board.setField(j, Mark.RED);
		}
		assertEquals("Test diagonal", 4, rules.scanDiagonalLeftUp(Mark.RED, 40));
		board.setField(24, Mark.YELLOW);
		assertEquals("Test diagonaal met 1 opponent ertussen", 2, rules.scanDiagonalLeftUp(Mark.RED, 40));
	}
	
	@Test
	public void testScanDiagonalLeftDown() {
		for(int i = 0; i < Board.MAXFIELDS; i++) {
			assertEquals("Test empty field", 0, rules.scanDiagonalLeftDown(Mark.RED, i));
		}
		
		for(int j = 6; j < Board.MAXFIELDS; j = j + 6) {
			board.setField(j, Mark.RED);
		}
		assertEquals("Test diagonaal", 4, rules.scanDiagonalLeftDown(Mark.RED, 6));
		 	
		board.setField(24, Mark.YELLOW);
		assertEquals("Test diaganaal met een opponet ertussen", 3, rules.scanDiagonalLeftDown(Mark.RED, 6));
	}
	
	@Test
	public void testScanDiagonalRightUp() {
		for(int i = 0; i < Board.MAXFIELDS; i++) {
			assertEquals("Test empty field", 0, rules.scanDiagonalRightUp(Mark.RED, i));
		}
		
		for(int j = 6; j < Board.MAXFIELDS; j = j + 6) {
			board.setField(j, Mark.RED);
		}
		assertEquals("Test diagonal", 4, rules.scanDiagonalRightUp(Mark.RED, 36)); 	
		board.setField(24, Mark.YELLOW);
		assertEquals("Test diaganaal met een opponet ertussen", 2, rules.scanDiagonalRightUp(Mark.RED, 36));
	}
	
	@Test
	public void testScanDiagonalRightDown() {
		for(int i = 0; i < Board.MAXFIELDS; i++) {
		assertEquals("Test empty field", 0, rules.scanDiagonalRightDown(Mark.RED, i));
	}
		
		for(int j = 0; j < Board.MAXFIELDS; j = j + 8) {
			board.setField(j, Mark.RED);
		}
		assertEquals("Test diagonal", 4, rules.scanDiagonalRightDown(Mark.RED, 0));
		board.setField(24, Mark.YELLOW);
		assertEquals("Test diagonaal met 1 opponent ertussen", 3, rules.scanDiagonalRightDown(Mark.RED, 0));
	}
	
	@Test
	public void testDiagonalWin() {
		for (int i = 0; i < Board.MAXFIELDS; i++) {
		assertEquals("Test winner diagonal win on empty field", false, rules.diagonalWin(Mark.RED, i));
		}
		
		for (int j = 0; j < 25; j = j + 8) {
			board.setField(j, Mark.RED);
		}
		
		assertEquals("Test winner diagonal win LRpartitial", true, rules.diagonalWin(Mark.RED, 16));
		
		for (int k = 32; k < Board.MAXFIELDS; k = k + 8) {
			board.setField(k, Mark.RED);
		}
		assertEquals("Test winner diagonal win LRfull", true, rules.diagonalWin(Mark.RED, 24));
		
		board.reset();
		
		for (int l = 6; l < 25; l = l + 6) {
			board.setField(l, Mark.RED);
		}
		
		assertEquals("Test winner diagonal win RLpartitial", true, rules.diagonalWin(Mark.RED, 18));
		
		for(int m = 30; m < Board.MAXFIELDS; m = m + 6) {
			board.setField(m, Mark.RED);
		}
		
		assertEquals("Test winner diagonal win RLfull", true, rules.diagonalWin(Mark.RED, 24));
	}
	
	@Test
	public void testIsWinner() {
		for(int i = 0; i < Board.MAXFIELDS; i++) {
		assertEquals("Test isWinner on emptyBoard red", false, rules.isWinner(Mark.RED, i));
		assertEquals("Test isWinner on emptyBoard yellow", false, rules.isWinner(Mark.YELLOW, i));
		}
		
		for(int j = 14; j < 18; j++) {
		board.setField(j, Mark.RED);
		}
		assertEquals("Test isWinner with horizontalWin true", true, rules.isWinner(Mark.RED, 15));
		
		board.reset();
		
		for(int k = 15; k < Board.MAXFIELDS; k = k + 7) {
			board.setField(k, Mark.RED);
		}
		assertEquals("Test isWinner with verticalWin true", true, rules.isWinner(Mark.RED, 15));
		
		board.reset();
		
		for(int m = 15; m < Board.MAXFIELDS; m = m + 8) {
			board.setField(m, Mark.RED);
		}
		assertEquals("Test isWinner with DiagaonalWin true", true, rules.isWinner(Mark.RED, 23));
	}
	
	@Test
	public void testGetHasWinner() {
		rules.isWinner(Mark.RED, 15);
		assertEquals("Test getHasWinner with isWinnerfalse", false, rules.getHasWinner());
		
		for(int j = 14; j < 18; j++) {
		board.setField(j, Mark.RED);
		}
		rules.isWinner(Mark.RED, 15);
		assertEquals("Test getHasWinner with isWinnerTrue", true, rules.getHasWinner());
		
	}
	
	@Test
	public void testIsGameOVer() {		
		for (int i = 0; i < Board.MAXFIELDS; i ++) {
		rules.isGameOver(Mark.RED, i);
		assertEquals("Test empty board", false, rules.getGameOver());
		}
		
		for (int j = 0; j < Board.MAXFIELDS; j++) {
			board.setField(j, Mark.RED);
		}
		rules.isGameOver(Mark.RED, 0);
		assertEquals("TestFullBoard and there is a winner", true, rules.getGameOver());
	}
	
	
	@Test
	public void testReset() {
		for (int j = 0; j < Board.MAXFIELDS; j++) {
			board.setField(j, Mark.RED);
		}
		rules.isGameOver(Mark.RED, 0);
		rules.reset();
		assertEquals("reset the board", false, rules.getGameOver() && rules.getHasWinner());
	}
}
