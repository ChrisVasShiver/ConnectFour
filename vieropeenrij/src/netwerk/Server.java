package netwerk;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import main.Game;
import main.Mark;
import netwerkprotocol.ProtocolConstants;
import netwerkprotocol.ProtocolControl;

public class Server extends Thread implements ProtocolControl,
		ProtocolConstants {

	private int port;
	private Socket serversocket;
	private List<ClientHandler> threads;
	private List<ClientHandler> clientqueue;
	private List<Game> games;

	public Server(int port) {
		this.port = port;
		threads = new ArrayList<ClientHandler>();
		clientqueue = new ArrayList<ClientHandler>();
		
	}

	@Override
	public void run() {
		try {
			ServerSocket serversocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handleCommands(String command, ClientHandler clienthandler) {
		String[] commandSplit = command.split(msgSeperator);
		Game game = games.get(clienthandler.getGameID());
		switch (commandSplit[0]) {

		case getBoard:
			sendBoard(clienthandler.getGameID());
			break;

		case joinRequest:
			clientqueue.add(clienthandler);
			threads.add(clienthandler);
			acceptRequest();
			if (clientqueue.size() == 2) {
				startGame();
			}
			break;

		case doMove:
			if (game.getCurrentPlayerIndex() == 0) {
				game.getBoard().setField(Integer.parseInt(commandSplit[1]),
						Mark.YELLOW);
			}
			if (game.getCurrentPlayerIndex() == 1) {
				game.getBoard().setField(Integer.parseInt(commandSplit[1]),
						Mark.RED);
			}
			if (game.getRules().getGameOver()) {
				endGame(clienthandler);
			} else {
				moveResult(Integer.parseInt(commandSplit[1]), clienthandler);
			}
			break;

		case playerTurn:
			game.getCurrentPlayer();
			break;

		case sendMessage:

		case getLeaderboard:

		}

	}

	public void sendBoard(int gameID) {
		games.get(gameID).getBoard();

	}

	public String acceptRequest() {
		if (clientqueue.size() == 1) {
			return ProtocolConstants.yellow;
		}
		if (clientqueue.size() == 2) {
			return ProtocolConstants.red;
		} else
			return ProtocolConstants.invalidCommand;
	}

	public String startGame() {
		String result = ProtocolControl.startGame
				+ ProtocolConstants.msgSeperator
				+ clientqueue.get(0).toString()
				+ ProtocolConstants.msgSeperator
				+ clientqueue.get(1).toString();
		clientqueue.clear();
		return result;
	}

	public String moveResult(int move, ClientHandler clienthandler) {
		Game game = games.get(clienthandler.getGameID());
		boolean validMove = game.getRules().isValidMove(move)
				&& game.getBoard().isEmptyField(move);
		return move + ProtocolConstants.msgSeperator + game.getCurrentPlayer()
				+ ProtocolConstants.msgSeperator + validMove
				+ ProtocolConstants.msgSeperator + game.getNextPlayer();
	}

	public String turn(ClientHandler clienthandler) {
		return games.get(clienthandler.getGameID()).getCurrentPlayer();

	}

	public String endGame(ClientHandler clienthandler) {
		String result = null;
		String reason = null;
		Game game = games.get(clienthandler.getGameID());
		if (game.getRules().getHasWinner()) {
			result = game.getCurrentPlayer() + "has won.";
			reason = "A player has won";
		}
		if (game.getRules().getGameOver() && !game.getRules().getHasWinner()) {
			result = "Tied game";
			reason = "Board is full";
		}
		return ProtocolControl.endGame + ProtocolConstants.msgSeperator
				+ result + ProtocolConstants.msgSeperator + reason;
	}

	public void sendLeaderboard() {
		
	}
}
