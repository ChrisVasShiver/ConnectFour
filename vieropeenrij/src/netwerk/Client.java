package netwerk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;

import main.Game;
import main.HumanPlayer;
import main.Mark;
import main.Player;
import netwerkprotocol.ProtocolConstants;
import netwerkprotocol.ProtocolControl;

public class Client extends Thread implements ProtocolControl,
		ProtocolConstants {

	/*@
	 private invariant name.matches(charRegex);
	 private invariant in != null;
	 private invariant out != null;
	 private invariant socket != null;
	 private invariant game != null;
	 private invariant thisplayer != null;
	 */
	private String name;
	private BufferedReader in;
	private BufferedWriter out;
	private Socket socket;
	private Game game;
	private Player thisplayer;
	private boolean clientRunning;
	private String consoleMessage = null;
	private boolean gameRunning = false;
	
	/**
	 * Creates a new instance of the Client.
	 * @param InetAddress the given InetAddress for the Client.
	 * @param port the given port for the Client
	 * @param name the given name for the Client.
	 */
	/*@
	 requires InetAddress != null;
	 requires port > 0 && port <= 65535;
	 requires name != null;
	 */
	public Client(InetAddress InetAddress, int port, String name) {
		super("client");
		assert InetAddress != null;
		assert port > 0 && port <= 65535;
		assert name != null;
		this.name = name;
		try {
			socket = new Socket(InetAddress, port);
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream()));
			clientRunning = true;
		} catch (ConnectException e) {
			System.out.println("ERROR: could not create client on "
					+ InetAddress + " and port " + port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Read the commands from the clienthandler from the server.
	 */
	@Override
	public void run() {
		while (clientRunning) {
			try {
				if (in == null) {
					closeClient();
				}

				if (in.ready()) {
					String command = in.readLine();
					System.out.println("Client, run method, " + command);
					handleCommands(command);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Handle all the commands incoming from the clienthandler from the server. Depending on the beginning of the String, a different method will be called.
	 * @param command the string originating from the clienthandler/server. 
	 */
	/*@
	 requires command != null;
	 */
	public void handleCommands(String command) {
		String[] commandSplit = command.split(msgSeperator);

		switch (commandSplit[0]) {

		/**
		 * Receives the board from the server. Adjust the board to the given String of marks.
		 */
		case sendBoard:
			Mark mark = null;
			for (int i = 1; 1 <= i && i < 42; i++) {
				if (commandSplit[i].equals(yellow)) {
					mark = Mark.YELLOW;
				}
				if (commandSplit[i].equals(red)) {
					mark = Mark.RED;
				}
				if (commandSplit[i].equals(empty)) {
					mark = Mark.EMPTY;
				}
				game.getBoard().setField(i - 1, mark);
			}

			/**
			 * Receive an acceptrequest from the server. Set the player's mark to the given mark.
			 */
		case acceptRequest:
			if (commandSplit[1].equals(yellow)) {
				thisplayer = new HumanPlayer(name, Mark.YELLOW);
			}
			if (commandSplit[1].equals(red)) {
				thisplayer = new HumanPlayer(name, Mark.RED);
			}
			break;

			/**
			 * Starts the game.
			 */
		case startGame:
			System.out.println("Client, startgame case reached.");
			Player opponent = null;
			if (commandSplit[1].equals(this.name)) {
				thisplayer = new HumanPlayer(commandSplit[1], Mark.YELLOW);
				opponent = new HumanPlayer(commandSplit[2], Mark.RED);
				game = new Game(thisplayer, opponent);

			} else {
				thisplayer = new HumanPlayer(commandSplit[2], Mark.RED);
				opponent = new HumanPlayer(commandSplit[1], Mark.YELLOW);
				game = new Game(opponent, thisplayer);
			}
			game.setCurrentPlayer(commandSplit[0]);
			gameRunning = true;
			break;

			/**
			 * Receive the result from the Server when a move is done by either this player or the opponent.
			 */
		case moveResult:
			game.getBoard().setField(Integer.parseInt(commandSplit[1]),
					game.getPlayers()[game.getCurrentPlayerIndex()].getMark());

			/**
			 * Receive the player whose turn is next and set it to the currentplayer.
			 */
		case turn:
			if (game.getNextPlayer().equals(commandSplit[1])) {
				game.setCurrentPlayer(commandSplit[1]);
			}
			// return
			// System.out.println("stuur naar de console dat dit niet werkt");
			break;
			
			/**
			 * End the game.
			 */
		case endGame:
			game.endGame();
			// verstuur nog naar console wie er heeft gewonnen
			break;

			/**
			 * Print in the client's console an error.
			 */
		case invalidUsername:
			consoleMessage = invalidUsername;
			break;

			/**
			 * Print in the client's console an error.
			 */
		case invalidMove:
			consoleMessage = invalidMove;
			break;

			/**
			 * Print in the client's console an error.
			 */
		case invalidCommand:
			consoleMessage = invalidCommand;
			break;

			/**
			 * Print in the client's console an error.
			 */
		case usernameInUse:
			consoleMessage = usernameInUse;
			break;

			/**
			 * Print in the client's console an error.
			 */
		case invalidUserTurn:
			consoleMessage = invalidUserTurn;
			break;
		}
	}

	/**
	 * Send a request to the server to join.
	 * @param clientname the name of the client.
	 */
	/*@
	 requires clientname != null;
	 */
	public void joinRequest(String clientname) {
		assert clientname != null;
		try {
			out.write(joinRequest + msgSeperator + clientname);
			out.newLine();
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * The client sends its move to the server.
	 * @param move the move of the client.
	 */
	/*@
	 requires move >= 0 && move <= 41;
	 */
	public void doMove(int move) {
		assert move >= 0 && move <= 41;
		String doMove = Integer.toString(move);
		try {
			out.write(ProtocolControl.doMove + msgSeperator + doMove);
			out.newLine();
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Ask the server whose turn it is.
	 */
	public void playerTurn() {
		try {
			out.write(playerTurn);
			out.newLine();
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();

		}

	}
	
	/**
	 * Closes the client.
	 */
	/*@
	 ensures getClientRunning() == false;
	 */
	public void closeClient() {
		try {
			socket.close();
			clientRunning = false;
			gameRunning = false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Get the status of the client. If it is initialized without any errors.
	 * @return returns true if client is still running. Returns false if client is stopped.
	 */
	/*@
	 ensures \result == true || \result == false;
	 pure;
	 */
	public boolean getClientRunning() {
		return clientRunning;
	}

	/**
	 * Request the board from the server.
	 */
	public void getGameBoard() {
		try {
			out.write(getBoard);
			out.newLine();
			out.flush();
		} catch (IOException e) {

		}
	}

	/**
	 * Get the game of the client.
	 * @return returns the game.
	 */
	/*@
	 ensures \result != null;
	 */
	public Game getGame() {
		return game;
	}
	
	public boolean getGameRunning(){
		return gameRunning;
	}
	/**
	 * Get the console message for the client. And reset the consoleMessag variable to null.
	 * @return returns the console message.
	 */
	public String getConsoleMessage(){
		String message = consoleMessage;
		consoleMessage = null;
		return message;
	}

	/**
	 * Send a chatmessage to the server.
	 * @param msg the message
	 */
	/*@
	 requires msg != null;
	 */
	public void sendMessage(String msg) {

	}

	public void getLeaderBoard() {

	}

	public void sendLeaderBoard() {

	}
}
