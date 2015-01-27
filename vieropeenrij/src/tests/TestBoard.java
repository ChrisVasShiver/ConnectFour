package tests;

import static org.junit.Assert.*;
import main.Board;
import main.Mark;
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
		for(int i = 0; i < Board.MAXFIELDS; i++) {
		assertEquals("All field<" + i + "> is empty", Mark.EMPTY, board.getField(i));
		}
	}

	@Test
	public void testCopyBoard() {
		Board testBoard = board.copyBoard();
		for (int i = 0; i < Board.MAXFIELDS; i++) {
			assertEquals("She if the copied board is the same", true, board.getField(i) == testBoard.getField(i));
		}
	}
	
	@Test
	public void testMatrixToIndex() {
		for(int row = 0; row < Board.HEIGHT; row++) {
			for (int col = 0; col < Board.WIDTH; col++) {
				assertEquals("Test Matrix to index", (row * Board.WIDTH) + col, board.matrixToIndex(row, col));
			}
		}
	}
	
	@Test
	public void testIndexToMatrix() {
		for(int i = 0; i < Board.MAXFIELDS; i++) {
			int[] testArray = board.indexToMatrix(i);
			assertEquals("Test index to matrix col", (i % Board.WIDTH) , testArray[1]);
			assertEquals("Test index to matrix row", (i - testArray[1]) % Board.HEIGHT,testArray[0]);
		}
	}
	@Test
	public void testSetField() {
		for (int i = 0; i < Board.MAXFIELDS; i++) {
			board.setField(i, Mark.RED);
			assertEquals("Test setField", Mark.RED, board.getField(i));
			board.setField(i, Mark.YELLOW);
			assertEquals("Test setField", Mark.YELLOW, board.getField(i));
			board.setField(i, Mark.EMPTY);
			assertEquals("Test setField", Mark.EMPTY, board.getField(i));
		}
	}
	
	@Test
	public void testIsEmptyField() {
		for (int i = 0; i < Board.MAXFIELDS; i++) {
			assertEquals("Test is empty field", true, board.isEmptyField(i));
		}
		for (int i = 0; i < Board.MAXFIELDS; i++) {
			board.setField(i, Mark.RED);
			assertEquals("Test if empty field", false, board.isEmptyField(i));
			board.setField(i, Mark.YELLOW);
			assertEquals("Test if empty field", false, board.isEmptyField(i));
		}
	}
	
	@Test
	public void testIsExistingFIeld() {
		assertEquals("Test if negative field exists", false, board.isExistingField(-1));
		assertEquals("Test if outofbound field exists", false, board.isExistingField(42));
		for (int i = 0; i < Board.MAXFIELDS; i++) {
			assertEquals("Test fields exists", true, board.isExistingField(i));
		}
	}
	
	@Test
	public void testReset() {
		for (int i = 0; i < Board.MAXFIELDS; i++) {
			board.setField(i, Mark.RED);
		}
		board.reset();
		for(int j = 0; j < Board.MAXFIELDS; j++) {
			assertEquals("Test board.Reset()", Mark.EMPTY, board.getField(j));
		}
	}
	
	@Test
	public void testDropMark() {
		assertEquals("Test DropMark within empty Board", 35, board.dropMark(Mark.RED, 0));
		board.setField(35, Mark.RED);
		board.setField(28, Mark.YELLOW);
		assertEquals("Test DropMark with some fields not empty", 21, board.dropMark(Mark.RED, 0));
		assertEquals("Test DropMark with some fields not empty", 21, board.dropMark(Mark.RED, 35));
	}
}
