	package main;

/**
 * 
 * @author C. and D. Ye
 * 
 */
public class ConnectFour {
	public static void main(String[] args) {
		Game game = new Game(null, null);
		game.update();
		for (int i = 0; i < game.getBoard().MAXFIELDS; i++) {
			game.getBoard().setField(i, Mark.RED);
		}
		game.getBoard().setField(31, Mark.YELLOW);
		System.out.println(game.getBoard().toString2());
	}
}
