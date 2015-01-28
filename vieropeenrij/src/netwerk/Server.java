package netwerk;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import main.Board;
import main.Game;
import main.HumanPlayer;
import main.Mark;
import main.Player;
import netwerkprotocol.ProtocolConstants;
import netwerkprotocol.ProtocolControl;

public class Server extends Observable implements ProtocolControl,
ProtocolConstants, Runnable {

	/*@
	 private invariant port > 0 && port <= 65535;
	 private invariant serversocket != null;
	 */
	private int port;
	private ServerSocket serversocket;
	private List<ClientHandler> threads;
	private List<ClientHandler> clientqueue;
	private List<Game> games;
	private boolean serverRunning = false;
	private Map<Integer, ArrayList<String>> rematchMap = new HashMap<Integer, ArrayList<String>>();

	/**
	 * Creates the server with the given port.
	 * @param port the port for the server.
	 */
	/*@
	 requires port > 0 && port <= 65535;
	 */
	public Server(int port) {
		assert port > 0 && port <= 65535;
		this.port = port;
		threads = new ArrayList<ClientHandler>();
		clientqueue = new ArrayList<ClientHandler>();
		games = new ArrayList<Game>();
	}

	/**
	 * Run the server, and try to create a serversocket with the given port.
	 */
	@Override
	public void run() {
		// Create a new server socket.
		try {
			setChanged();
			serversocket = new ServerSocket(port);
			serverRunning = true;
			notifyObservers("SERVER_CREATED");
		} catch (BindException e) {
			setChanged();
			serverRunning = false;
			notifyObservers("PORT_IN_USE");
		} catch (IOException e1) {
			setChanged();
			serverRunning = false;
			notifyObservers("UNKNOW_SERVER_ERROR");
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
				setChanged();
				notifyObservers("SOCKET_SERVER_ERROR");
				stopConnections();
			} catch (IOException e) {
				setChanged();
				notifyObservers("UNKNOWN_SERVER_ERROR");
				stopConnections();
			}
		}

	}

	// Handle all the incoming commands from the client.
	/**
	 * Handle all the incoming commands from the client.
	 * @param command the String received from the client, through the clienthandler.
	 * @param clienthandler the clientehandler.
	 */
	/*@
	 requires command != null;
	 requires clienthandler != null;
	 */
	public void handleCommands(String command, ClientHandler clienthandler) {
		assert command != null;
		assert clienthandler != null;
		String[] commandSplit = command.split(msgSeperator);
		switch (commandSplit[0]) {
		// See protocol for the cases.
		case getBoard:
			clienthandler.sendToClient(sendBoard(games.get(clienthandler
					.getGameID())));
			break;
		case joinRequest:
			clienthandler.setClientName(commandSplit[1]);
			boolean error = false;
			// Throw exception if the client's name is already in use.
			try {
				for (ClientHandler clienthandlers : threads) {
					if (clienthandlers.getClientName().equals(
							clienthandler.getClientName())) {
						error = true;
						throw new Exception();
					}
				}
			} catch (Exception e) {
				clienthandler.sendToClient(usernameInUse);
			}
			// Throw exception when the username is invalid.
			try {
				if (!clienthandler.getClientName().matches(charRegex)
						|| clienthandler.getClientName().contains(" ")) {
					error = true;
					throw new Exception();
				}
			} catch (Exception e) {
				clienthandler.sendToClient(invalidUsername);
			}
			// If the clientqueue == 2 then start a game with the clients in the game and clear the queue.
			if (clienthandler.getClientName() != null && !error) {
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
			boolean validmove = false;
			System.out.println("doMove in server reached");
			if (game.getRules().getGameOver()){
			}
			else {
				// Check if the move is a valid move.
				if (game.getRules().isValidMove((move)) && game.getBoard().isEmptyField(move)) {
					validmove = true;
				}
				// Return invalidMove when the move is invalid to the client.
				if (!validmove && game.getCurrentPlayer().equals(clienthandler.getClientName())){
					System.out.println("invalidmove");
					clienthandler.sendToClient(invalidMove);
				}
				// Return invalidUserTurn to the client who is trying to play, when it's not its turn.
				if (!game.getCurrentPlayer().equals(clienthandler.getClientName())){
					clienthandler.sendToClient(invalidUserTurn);
				}
				// Do a move when it's client's turn and it's a valid move.
				if (validmove && game.getCurrentPlayer().equals(clienthandler.getClientName())){
					if (game.getCurrentPlayerIndex() == 0) {
						move = game.getBoard().dropMark(Mark.YELLOW, move);
						game.getBoard().setField(move, Mark.YELLOW);
						game.getRules().isGameOver(Mark.YELLOW, move);
					} else {
						move = game.getBoard().dropMark(Mark.RED, move);
						game.getBoard().setField(move, Mark.RED);
						game.getRules().isGameOver(Mark.RED, move);
					}
					// Send the result of the move to all the clients with the same gameID.
					for (ClientHandler clienthandlers : threads){
						if (clienthandler.getGameID() == clienthandlers.getGameID()) {
							clienthandlers.sendToClient(moveResult(move,clienthandler));
						}
					}
					game.setNextPlayer();
				}
				// Check if the game is over after the move.
				if (game.getRules().getGameOver()){
					game.setWinner(clienthandler.getClientName());
					endGame(clienthandler.getGameID());
				}
			}
			break;

		case playerTurn:
			clienthandler.sendToClient(games.get(clienthandler.getGameID())
					.getCurrentPlayer());
			break;

		// Removes the client when it loses connection.
		case "Stop":
			removeClient(clienthandler);

		case rematch:
			// Reset the game where the client is part of.
			for (ClientHandler clienthandlers : threads){
				games.get(clienthandlers.getGameID()).reset();
			}
			// Add the client to a rematch map.
			addRematch(clienthandler);
		}

	}

	// Add a clienthandler to the list of threads.
	/**
	 * Add a clienthandler to the list of threads, when a client has connected.
	 * @param clienthandler the clienthandler of the client.
	 */
	/*@
	 requires clienthandler != null;
	 */
	public void addHandler(ClientHandler clienthandler) {
		assert clienthandler != null;
		clientqueue.add(clienthandler);
		threads.add(clienthandler);
		clienthandler.start();
		clienthandler.sendToClient(acceptRequest());
	}

	/**
	 * Sends a String to the client, if requested, with all the positions of the board from the game, held by the server.
	 * @param game the game where the client is a part of.
	 * @return a String with all the positions of the board.
	 */
	/*@
	 requires game != null;
	 ensures \result != null;
	 */
	public String sendBoard(Game game) {
		assert game != null;
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


	/**
	 * Accept a new client and sends a message that the client is yellow or red, depending on the size of the queue.
	 * @return returns the color of the client.
	 */
	/*@
	 ensures \result != null;
	 */
	public String acceptRequest() {
		if (clientqueue.size() == 1) {
			return acceptRequest + msgSeperator + yellow;
		}
		if (clientqueue.size() == 2) {
			return acceptRequest + msgSeperator + red;
		} else
			return invalidCommand;
	}
	
	/**
	 * Start a game when there are 2 clients in the queue.
	 * @return returns a command for the client. Contains the two players in the same game, serverside.
	 */
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

	/**
	 * Bij een validmove zal de server zijn versie van Game, de volgende speler de beurt geven. Deze speler zal meegestuurd worden in de string.
	 * @param move the move.
	 * @param clienthandler the client who made the move.
	 * @return returns the result of the move.
	 */
	/*@
	 requires move >= 0 && move <= 41;
	 requires clienthandler != null;
	 */
	public String moveResult(int move, ClientHandler clienthandler) {
		assert move >= 0 && move <= 41;
		assert clienthandler != null;
		Game game = games.get(clienthandler.getGameID());
		return moveResult + msgSeperator + move + msgSeperator
				+ game.getCurrentPlayer() + msgSeperator + true + msgSeperator
				+ game.getNextPlayer();
	}

	/**
	 * Send to the client, whose turn it currently is.
	 * @param clienthandler the client 
	 * @return returns the currentplayer.
	 */
	/*@
	 requires clienthandler != null;
	 ensures \result != null;
	 */
	public String turn(ClientHandler clienthandler) {
		assert clienthandler != null;
		return games.get(clienthandler.getGameID()).getCurrentPlayer();
	}

	/**
	 * Send a message to the client with the reason why the game is over.
	 * @param clienthandler the client
	 * @return returns the command to end the game at the clientside.
	 */
	/*@
	 requires clienthandler != null;
	 ensures \result != null;
	 */
	public String gameIsOver(ClientHandler clienthandler) {
		assert clienthandler != null;
		String result = null;
		String reason = null;
		Game game = games.get(clienthandler.getGameID());
		if (game.getRules().getHasWinner()) {
			result = "Player" + game.getCurrentPlayer() + " has won.";
			reason = "A player has won";
		}
		if (game.getRules().getGameOver() && !game.getRules().getHasWinner()) {
			result = "Tied game";
			reason = "Board is full";
			clienthandler.sendToClient(draw);
		}
		return endGame + msgSeperator + result + msgSeperator + reason;
	}

	/**
	 * Send to the client, the result of the game.
	 * @param gameID the game.
	 */
	public void endGame(int gameID) {
		// Send message to the clients playing in the game.
		for (ClientHandler clienthandler : threads) {
			if (clienthandler.getGameID() == gameID) {
				clienthandler.sendToClient(gameIsOver(clienthandler));
				if (games.get(clienthandler.getGameID()).getWinner()
						.equals(clienthandler.getClientName())) {
					setChanged();
					clienthandler.sendToClient(winner);
					notifyObservers("SERVER_MESSAGE");
				}
			}
		}
	}

	/**
	 * Remove a client
	 * @param clienthandler removes this clienthandler.
	 */
	/*@
	 requires clienthandler != null;
	 */
	public void removeClient(ClientHandler clienthandler) {
		assert clienthandler != null;
		games.remove(clienthandler.getGameID());
		threads.remove(clienthandler);
	}

	/**
	 * Closes the socket of the server, not allowing new connections.
	 */
	public void stopConnections() {
		try {
			serverRunning = false;
			serversocket.close();
		} catch (IOException e) {
			setChanged();
			notifyObservers("UNKNOWN_SERVER_ERROR");
		}
	}

	/**
	 * Add a client to the map of rematches. If the arraylist of a unique gameID has reached size 2, meaning both clients want a rematch. Start a rematch.
	 * @param clienthandler the clienthandler wanting a rematch.
	 */
	/*@
	 requires clienthandler != null;
	 */
	public void addRematch(ClientHandler clienthandler){
		assert clienthandler != null;
		ArrayList<String> ar = new ArrayList<String>();
		if (rematchMap.get(clienthandler.getGameID()) != null){
			ar = rematchMap.get(clienthandler.getGameID());
		}	
		ar.add(clienthandler.getClientName());
		rematchMap.put(clienthandler.getGameID(), ar);

		for(int i = 0; i < rematchMap.size(); i++){
			if (rematchMap.get(i).size() == 2){
				startRematch(i);
			}
		}
	}

	/**
	 * Starts a rematch when both clients of the same game wants a rematch. Send a rematchConfirm command to both clients.
	 * @param gameID the game.
	 */
	public void startRematch(int gameID){
		for (ClientHandler clienthandlers : threads){
			if (clienthandlers.getGameID() == gameID){
				clienthandlers.sendToClient(rematchConfirm);
			}
		}
	}
}
