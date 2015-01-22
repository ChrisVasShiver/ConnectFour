package netwerk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

import main.Board;
import main.Game;
import main.HumanPlayer;
import main.Mark;
import main.Player;
import netwerkprotocol.ProtocolConstants;
import netwerkprotocol.ProtocolControl;

public class Client extends Thread implements ProtocolControl,
		ProtocolConstants {

	private int port;
	private String name;
	private BufferedReader in;
	private BufferedWriter out;
	private Socket socket;
	private Game game;
	private Player thisplayer;

	public Client(InetAddress InetAddress, int port, String name) {
		this.port = port;
		this.name = name;
		try {
			socket = new Socket(InetAddress, port);
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream()));
		} catch (IOException e) {
			System.out.println("ERROR: could not create client on "
					+ InetAddress + " and port " + port);
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				if (in == null) {
					// TODO
				}

				if (in.ready()) {
					String command = in.readLine();
					handleCommands(command);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void handleCommands(String command) {
		String[] commandSplit = command.split(msgSeperator);

		switch (commandSplit[0]) {

		case sendBoard:
			Mark mark = null;
			for (int i = 1; 1 <= i && i < 42; i++) {
				if (commandSplit[i].equals(ProtocolConstants.yellow)) {
					mark = Mark.YELLOW;
				}
				if (commandSplit[i].equals(ProtocolConstants.red)) {
					mark = Mark.RED;
				}
				if (commandSplit[i].equals(ProtocolConstants.empty)) {
					mark = Mark.EMPTY;
				}
				game.getBoard().setField(i - 1, mark);
			}

		case acceptRequest:
			if (commandSplit[1].equals(ProtocolConstants.yellow)) {
				thisplayer.setMark(Mark.YELLOW);
			}
			if (commandSplit[1].equals(ProtocolConstants.red)) {
				thisplayer.setMark(Mark.RED);
			}
			break;

		case startGame:
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
			break;

		case turn:
			game.getCurrentPlayer();
			break;

		case endGame:
			game.endGame();
			break;

		// TODO
		// case sendLeaderboard:
		// break;

		case ProtocolConstants.invalidUsername:
			System.out.println("ERROR: Username is invalid.");
			break;

		case ProtocolConstants.invalidMove:
			System.out.println("ERROR: Move is invalid.");
			break;

		case ProtocolConstants.invalidCommand:
			System.out.println("ERROR: Command is invalid.");
			break;

		case ProtocolConstants.usernameInUse:
			System.out
					.println("ERROR: Username is in use, please use a different username.");
			break;

		case ProtocolConstants.invalidUserTurn:
			System.out.println("ERROR: Please wait your turn.");
			break;
		}
	}

	/*
	 * getBoard moet opgevraagd worden na elke zet en aan het begin van het spel
	 */
	public Board getBoard() {
		return game.getBoard();

	}

	public void joinRequest(String clientname) {

	}

	/*
	 * De client die aan de beurt is stuurt een “doMove” command gevolgd door
	 * een spatie en dan de index waar hij zijn zet wil doen naar de server.
	 */
	public String doMove(int move) {
		String doMove = Integer.toString(move);
		return (ProtocolControl.doMove + ProtocolConstants.msgSeperator + doMove);
	}

	/*
	 * opvragen wie aan de beurt is. Returned de naam van de client die aan de
	 * beurt is.
	 */
	public String playerTurn() {
		return game.getCurrentPlayer();
	}

	public void sendMessage(String msg) {

	}

	public void getLeaderBoard() {

	}

	public void sendLeaderBoard() {

	}
}
