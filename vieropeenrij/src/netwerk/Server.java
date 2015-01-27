package netwerk;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import main.Board;
import main.Game;
import main.HumanPlayer;
import main.Mark;
import main.Player;
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
		super("server");
		this.port = port;
		threads = new ArrayList<ClientHandler>();
		clientqueue = new ArrayList<ClientHandler>();
		games = new ArrayList<Game>();
	}

	@Override
	public void run() {
		// Create a new server socket.
		try {
			serversocket = new ServerSocket(port);
			serverRunning = true;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			serverRunning = false;
		}

		// Allow new connections if serverRunning == true.
		while (serverRunning) {
			try {
				// Create niet clienthandler.
				Socket clientsocket = serversocket.accept();
				ClientHandler clienthandler = new ClientHandler(clientsocket,
						this);
				clienthandler.setGameID(games.size());
			} catch (SocketException e) {
				break;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	// Handle all the incoming commands from the client.
	public void handleCommands(String command, ClientHandler clienthandler) {
		String[] commandSplit = command.split(msgSeperator);
		System.out.println("Server, CH gameID = " + clienthandler.getGameID());

		switch (commandSplit[0]) {

		case getBoard:
			clienthandler.sendToClient(sendBoard(games.get(clienthandler
					.getGameID())));
			break;

		case joinRequest:
			clienthandler.setClientName(commandSplit[1]);
			if (clienthandler.getClientName() != null
					&& clienthandler.getClientName().matches(charRegex)
					&& !clienthandler.getClientName().contains(" ")) {
				addHandler(clienthandler);
				if (clientqueue.size() == 2) {
					for (ClientHandler handlers : clientqueue) {
						handlers.sendToClient(startGame());
					}
					clientqueue.clear();
				}
			}
			break;

		case doMove:
			Game game = games.get(clienthandler.getGameID());
			int move = Integer.parseInt(commandSplit[1]);
			try{
			if (game.getCurrentPlayerIndex() == 0 && game.getCurrentPlayer().equals(clienthandler.getClientName()) && game.getBoard().isEmptyField(move)) {
				game.getBoard().setField(move, Mark.YELLOW);
				for (ClientHandler clienthandlers : threads){
					if (clienthandler.getGameID() == clienthandlers.getGameID()){
						clienthandlers.sendToClient(moveResult(move, clienthandler));
						}
					}
			}
			if (game.getCurrentPlayerIndex() == 1 && game.getCurrentPlayer().equals(clienthandler.getClientName()) && game.getBoard().isEmptyField(move)) {
				game.getBoard().setField(move, Mark.RED);
				for (ClientHandler clienthandlers : threads){
					if (clienthandler.getGameID() == clienthandlers.getGameID()){
						clienthandlers.sendToClient(moveResult(move, clienthandler));
							}
						}
					}
			if (games.get(clienthandler.getGameID()).getRules().getGameOver()) {
				endGame(clienthandler.getGameID());
					}
			game.setNextPlayer();

				}
			catch (Exception e) {
				clienthandler.sendToClient(invalidMove);
			}
			break;

		case playerTurn:
			clienthandler.sendToClient(games.get(clienthandler.getGameID())
					.getCurrentPlayer());
			break;

		case sendMessage:

		case getLeaderboard:

		}

	}

	// Add a clienthandler to the list of threads.
	public void addHandler(ClientHandler clienthandler) {
		clientqueue.add(clienthandler);
		threads.add(clienthandler);
		clienthandler.start();
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
		System.out.println("startgame" + clientqueue.get(0).getClientName());
		System.out.println("startgame" + clientqueue.get(1).getClientName());

		Player p1 = new HumanPlayer(clientqueue.get(0).getClientName(),
				Mark.YELLOW);
		Player p2 = new HumanPlayer(clientqueue.get(1).getClientName(),
				Mark.RED);
		String result = startGame + msgSeperator
				+ clientqueue.get(0).getClientName() + msgSeperator
				+ clientqueue.get(1).getClientName();
		games.add(new Game(p1, p2));

		return result;
	}

	/*
	 * Bij een validmove zal de server zijn versie van Game, de volgende speler
	 * de beurt geven. Deze speler zal meegestuurd worden in de string.
	 */
	public String moveResult(int move, ClientHandler clienthandler) {
		Game game = games.get(clienthandler.getGameID());
		System.out.println(game.getCurrentPlayerIndex());
		System.out.println(game.getNextPlayer());
		System.out.println(game.getCurrentPlayerIndex());
		return moveResult + msgSeperator + move + msgSeperator
				+ game.getCurrentPlayer() + msgSeperator + true + msgSeperator
				+ game.getNextPlayer();
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
