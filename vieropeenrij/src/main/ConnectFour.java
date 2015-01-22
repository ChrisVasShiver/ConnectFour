	package main;

import strategy.RandomStrategy;

/**
 * 
 * @author C. and D. Ye
 * 
 */
public class ConnectFour {
	public static void main(String[] args) {
		Game game = new Game(new HumanPlayer("Henk", Mark.RED), new ComputerPlayer(Mark.YELLOW, new RandomStrategy()));
		game.start();
	}
}
