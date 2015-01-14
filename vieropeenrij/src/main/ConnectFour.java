package main;

public class ConnectFour {
	public static void main(String[] args) {
		Game game = new Game(null, null);
		game.getBoard().setField(30, Mark.RED);
		game.update();
		game.getBoard().setField(2, Mark.YELLOW);
		game.update();
	}
}
