package netwerk;

import java.io.IOException;
import java.net.ServerSocket;

public class Server extends Thread implements ProtocolControl, ProtocolConstants{
	
	private int port;
	private Socket serversocket;
	private List<ClientHandler> threads;
	private List<Game> games;

	public Server(int port){
		this.port = port;
	}
	
	@Override
	public void run() {
		try {
			ServerSocket socket = new ServerSocket(port);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void handleCommands(String command){
		String[] commandSplit = command.split(msgSeperator);

		switch(commandSplit[0]){

			case getBoard:

			case joinRequest:

			case startGame:

			case moveResult:

			case playerTurn:

			case sendMessage:

			case getLeaderBoard:

		}

	}
	
	public void sendBoard(){
		
	}
	
	public void acceptRequest(){
		
	}
	
	public void startGame(){
		
	}
	
	public void moveResult(){
		
	}
	
	public void turn(){
		
	}
	
	public void endGame(){
		
	}
	
	public void sendLeaderboard(){
		
	}
}
