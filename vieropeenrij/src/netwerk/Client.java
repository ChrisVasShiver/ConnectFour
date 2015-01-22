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

	}

	public void handleCommands(String command){
		String[] commandSplit = command.split(msgSeperator);

		switch(commandSplit[0]){

			case sendBoard:
			Mark mark = null;
			for (int i = 1; 1 <= i && i < 42; i++){
				if(commandSplit[i].equals(ProtocolConstants.yellow)){
					mark = Mark.YELLOW;
				}
				if(commandSplit[i].equals(ProtocolConstants.red)){
					mark = Mark.RED;
				}
				if(commandSplit[i].equals(ProtocolConstants.empty)){
					mark = Mark.EMPTY;
				}
				game.getBoard().setField(i-1, mark);
			}

			case acceptRequest:
			if (commandSplit[1].equals(ProtocolConstants.yellow)){
				new 
			}
			if (commandSplit[1].equals(ProtocolConstants.red)){
			}


			case startGame:
			Player thisplayer = null;
			Player opponent = null;
			if (commandSplit[1].equals(this.name)){
				thisplayer = new HumanPlayer(commandSplit[1], Mark.YELLOW);
				opponent = new Player(commandSplit[2], Mark.RED);
				game = new Game(thisplayer, opponent);

			}
			else {
				thisplayer = new HumanPlayer(commandSplit[2], Mark.RED);
				opponent = new Player(commandSplit[1], Mark.YELLOW);
				game = new Game(opponent, thisplayer);
			}
			

			case turn:
			game.getCurrentPlayer();

			case endGame:
			game.endGame();

			//TODO
			// case sendLeaderboard:

		}
	}

	/*
	 * getBoard moet opgevraagd worden na elke zet en aan het begin van het spel
	 */
	public void getBoard() {
		return game.getBoard();

	}

	public void joinRequest(String clientname) {

	}

	/*
	 * De client die aan de beurt is stuurt een “doMove” command gevolgd door
	 * een spatie en dan de index waar hij zijn zet wil doen naar de server.
	 */
	public void doMove(int move) {

	}

	/*
	 * opvragen wie aan de beurt is. Returned de naam van de client die aan de
	 * beurt is.
	 */
	public void playerTurn() {

	}

	public void sendMessage(String msg) {

	}

	public void getLeaderBoard() {

	}

	public void sendLeaderBoard() {

	}
}
