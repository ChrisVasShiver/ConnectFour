package netwerk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;

import netwerkprotocol.ProtocolConstants;

public class ClientHandler extends Thread {

	/*@
	 private invariant socket != null;
	 private invariant in != null;
	 private invariant out != null;
	 private invariant server != null;
	 private invariant clientname != null;
	 */
	private int gameID;
	private Socket socket;
	private BufferedReader in;
	private BufferedWriter out;
	private Server server;
	private String clientname;

	/**
	 * Creates a ClientHandler on the given socket and server.
	 * @param socket the socket.
	 * @param server the server
	 * @throws IOException
	 */
	/*@
	 requires socket != null;
	 requires server != null;
	 */
	public ClientHandler(Socket socket, Server server) throws IOException {
		super("clienthandler");
		assert socket != null;
		assert server != null;
		this.socket = socket;
		this.server = server;
		Thread clienthandler = new Thread(this);
		clienthandler.start();
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new BufferedWriter(new OutputStreamWriter(
				socket.getOutputStream()));
	}

	// Send to server
	public void run() {
		while (true) {
			try {
				/* Lees het bericht vanaf de input stream */
				String msg = in.readLine();
				System.out.println("CH msg to server " + msg);

				if (msg == null || msg.equals(null)) {
					msg = "Stop";
				}

				/* Handle de commands op de server */
				server.handleCommands(msg, this);

				if (msg.equals("Stop")) {
					break;
				}

			} catch (NullPointerException e){
				server.handleCommands("ConnectionLost", this);

			}
			catch (Exception e) {

				/* SocketException = spel afgelopen! */
				// server.broadcast(spelAfgelopen
				// + msgDelim
				// + server.getGame(this.GAMEID)
				// .getWinnaar(server.getGame(GAMEID))
				// .getPlayerName() + msgDelim
				// + server.getGame(this.GAMEID).aanDeBeurt());
			}
		}
	}

	// Send to client
	public void sendToClient(String msg) {
		try {
			System.out.println("CH reached sendToClient, " + msg);
			out.write(msg);
			out.newLine();
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Get the gameID
	 * @return the gameID of the ClientHandler
	 */
	/*@
	 ensures \result > 0;
	 pure
	 */
	public int getGameID() {
		return gameID;
	}

	/**
	 * Set the gameID for the ClientHandler
	 * @param gameID the gameId
	 */
	/*@
	 ensures getGameID() == gameID;
	 */
	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	/**
	 * Get the ClientName of the ClientHandler
	 * @return the clientname
	 */
	/*@
	 ensures \result != null;
	 */
	public String getClientName() {
		return clientname;
	}

	/**
	 * Set the ClientName of the ClientHandler
	 * @param clientname the clientname
	 */
	/*@
	 requires clientname != null;
	 ensures getClientName() == clientname;
	 */
	public void setClientName(String clientname) {
		assert clientname != null;
		this.clientname = clientname;
	}

	/**
	 * Close the ClientHandler
	 */
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
