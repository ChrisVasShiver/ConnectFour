package netwerk;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import main.Board;
import main.Game;
import main.Mark;
import netwerkprotocol.ProtocolConstants;
import netwerkprotocol.ProtocolControl;

public class Server extends Thread implements ProtocolControl,
		ProtocolConstants {

	private int port;
	private ServerSocket serversocket;
	private List<ClientHandler> threads;
	private List<ClientHandler> clientqueue;
	private List<Game> games;
	private boolean serverRunning = false;

	public Server(int port) {
		this.port = port;
		threads = new ArrayList<ClientHandler>();
		clientqueue = new ArrayList<ClientHandler>();

	}

	@Override
	public void run() {
		// Start the server
		try {
			serversocket = new ServerSocket(port);
			serverRunning = true;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			serverRunning = false;
			}

		while (serverRunning) {
			try {
				Socket clientsocket = serversocket.accept();
				ClientHandler clienthandler = new ClientHandler(clientsocket,
						this);
				clienthandler.setGameID(games.size()+1);
				threads.add(clienthandler);
			}  catch (SocketException e) {
				break;
			}  catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	// Handle all the incoming commands from the client.
	public void handleCommands(String command, ClientHandler clienthandler) {
		String[] commandSplit = command.split(msgSeperator);
		Game game = games.get(clienthandler.getGameID());
		switch (commandSplit[0]) {

		case getBoard:
			clienthandler.sendToClient(sendBoard(game));
			break;

		case joinRequest:
			if (clienthandler.getName() != null
					&& clienthandler.getName().matches(charRegex)
					&& !clienthandler.getName().contains(" ")) {
				addHandler(clienthandler);
				if (clientqueue.size() == 2) {
					clienthandler.sendToClient(startGame());
				}
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
			if (!game.getRules().getGameOver()) {
				clienthandler.sendToClient(moveResult(
						Integer.parseInt(commandSplit[1]), clienthandler));
			}
			if (game.getRules().getGameOver()) {
				endGame(clienthandler.getGameID());
			}
			break;

		case playerTurn:
			clienthandler.sendToClient(game.getCurrentPlayer());
			break;

		case sendMessage:

		case getLeaderboard:

		}

	}

	// Add a clienthandler to the list of threads.
	public void addHandler(ClientHandler clienthandler) {
		clientqueue.add(clienthandler);
		threads.add(clienthandler);
		clienthandler.sendToClient(acceptRequest());
	}

	// Sends a String to the client, if requested, with all the positions of the
	// board.
	public String sendBoard(Game game) {
		String boardstring = null;
		for (int i = 0; i < Board.MAXFIELDS; i++) {
			if (game.getBoard().getField(i).equals(Mark.EMPTY)) {
				boardstring += msgSeperator + empty;
			}
			if (game.getBoard().getField(i).equals(Mark.YELLOW)) {
				boardstring += msgSeperator + yellow;
			}
			if (game.getBoard().getField(i).equals(Mark.RED)) {
				boardstring += msgSeperator + red;
			}
		}
		return boardstring;
	}

	// Accept a new client and sends a message that the client is yellow or red,
	// depending on the size of the queue.
	public String acceptRequest() {
		if (clientqueue.size() == 1) {
			return acceptRequest + msgSeperator + yellow;
		}
		if (clientqueue.size() == 2) {
			return acceptRequest + msgSeperator + red;
		} else
			return invalidCommand;
	}

	// Start a game when there are 2 clients in the queue.
	public String startGame() {
		String result = startGame + msgSeperator
				+ clientqueue.get(0).toString() + msgSeperator
				+ clientqueue.get(1).toString();
		clientqueue.clear();
		return result;
	}

	/*
	 * Bij een validmove zal de server zijn versie van Game, de volgende speler
	 * de beurt geven. Deze speler zal meegestuurd worden in de string.
	 */
	public String moveResult(int move, ClientHandler clienthandler) {
		Game game = games.get(clienthandler.getGameID());
		boolean validMove = game.getRules().isValidMove(move)
				&& game.getBoard().isEmptyField(move);
		if (validMove) {
			game.setCurrentPlayer(game.getNextPlayer());
		}
		return moveResult + msgSeperator + move + msgSeperator
				+ game.getCurrentPlayer() + msgSeperator + validMove
				+ msgSeperator + game.getCurrentPlayer();
	}

	public String turn(ClientHandler clienthandler) {
		return games.get(clienthandler.getGameID()).getCurrentPlayer();

	}

	// Send a message to the client with the reason why the game is over.
	public String gameIsOver(ClientHandler clienthandler) {
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
		return endGame + msgSeperator + result + msgSeperator + reason;
	}

	public void endGame(int gameID) {
		// Send message to the clients playing in the game.
		for (ClientHandler clienthandler : threads) {
			if (clienthandler.getGameID() == gameID) {
				clienthandler.sendToClient(gameIsOver(clienthandler));
				// Removes the clienthandler from the list of connected threads
				threads.remove(clienthandler);
			}
		}
		// Remove the game from the list of games.
		games.remove(gameID);
	}

	public void sendLeaderboard() {

	}

	// Closes the socket of the server, not allowing new connections.
	public void stopConnections() {
		try {
			serverRunning = false;
			serversocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
