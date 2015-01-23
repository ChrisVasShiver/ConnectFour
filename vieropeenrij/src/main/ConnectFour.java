	package main;

import strategy.RandomStrategy;

/**
 * 
 * @author C. and D. Ye
 * 
 */
public class ConnectFour {
	public static void main(String[] args) {
		Game game = new Game(new HumanPlayer("Carolijn", Mark.RED), new HumanPlayer("Christiaan" ,Mark.YELLOW));
		game.start();
	}
}
