	package main;

/**
 * 
 * @author C. and D. Ye
 * 
 */
public class ConnectFour {
	public static void main(String[] args) {
		Game game = new Game(new HumanPlayer("Henk", Mark.RED), new HumanPlayer("Jan", Mark.YELLOW));
		game.start();
	}
}
