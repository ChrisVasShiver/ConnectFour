package netwerk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

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
	private boolean clientRunning;

	public Client(InetAddress InetAddress, int port, String name) {
		this.port = port;
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

	@Override
	public void run() {
		while (clientRunning) {
			try {
				if (in == null) {
					closeClient();
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

		case acceptRequest:
			if (commandSplit[1].equals(yellow)) {
				thisplayer.setMark(Mark.YELLOW);
			}
			if (commandSplit[1].equals(red)) {
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

		case moveResult:
			game.getBoard().setField(Integer.parseInt(commandSplit[1]),
					game.getPlayers()[game.getCurrentPlayerIndex()].getMark());

		case turn:
			if (game.getNextPlayer().equals(commandSplit[1])) {
				game.setCurrentPlayer(commandSplit[1]);
			}
			// return
			// System.out.println("stuur naar de console dat dit niet werkt");
			break;

		case endGame:
			game.endGame();
			// verstuur nog naar console wie er heeft gewonnen
			break;

		// TODO
		// case sendLeaderboard:
		// break;

		case invalidUsername:
			System.out.println("ERROR: Username is invalid.");
			break;

		case invalidMove:
			System.out.println("ERROR: Move is invalid.");
			break;

		case invalidCommand:
			System.out.println("ERROR: Command is invalid.");
			break;

		case usernameInUse:
			System.out
					.println("ERROR: Username is in use, please use a different username.");
			break;

		case invalidUserTurn:
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
		try {
			out.write(joinRequest + msgSeperator + clientname);
			out.newLine();
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * De client die aan de beurt is stuurt een “doMove” command gevolgd door
	 * een spatie en dan de index waar hij zijn zet wil doen naar de server.
	 */
	public void doMove(int move) {
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

	/*
	 * opvragen wie aan de beurt is. Returned de naam van de client die aan de
	 * beurt is.
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
	

	public void getGameBoard() {
		try {
			out.write(getBoard);
			out.newLine();
			out.flush();
		} catch (IOException e) {

		}
	}

	public void closeClient() {
		try {
			socket.close();
			clientRunning = false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendMessage(String msg) {

	}

	public void getLeaderBoard() {

	}

	public void sendLeaderBoard() {

	}
}
