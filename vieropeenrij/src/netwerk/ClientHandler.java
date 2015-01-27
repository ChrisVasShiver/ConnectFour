package netwerk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import netwerkprotocol.ProtocolConstants;

public class ClientHandler extends Thread {

	private int gameID;
	private Socket socket;
	private BufferedReader in;
	private BufferedWriter out;
	private Server server;
	private String clientname;
	
	public ClientHandler(Socket socket, Server server) throws IOException {
		super("clienthandler");
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
				System.out.println("CH msg to server" + msg);

				if (msg == null || msg.equals(null)) {
					msg = "Stop";
				}

				/* Handle de commands op de server */
				System.out.println(server.toString());
				server.handleCommands(msg, this);

				if (msg.equals("Stop")) {
					break;
				}
			} catch (IOException e) {

			}
		}
	}

	// Send to client
	public void sendToClient(String msg) {
		try {
			out.write(msg);
			out.newLine();
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
