package netwerk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

import main.Board;
import netwerkprotocol.ProtocolConstants;
import netwerkprotocol.ProtocolControl;

public class Client extends Thread implements ProtocolControl, ProtocolConstants {
	
	private int thePort;
	private String theName;
	private BufferedReader in;
	private BufferedWriter out;
	private Socket socket;

	public Client(InetAddress InetAddress, int port, String name) {
		thePort = port;
		theName = name;
		thePort = port;
		try {
			socket = new Socket(InetAddress, port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e){
			System.out.println("ERROR: could not create client on " + InetAddress
					+ " and port " + port);		}
	}
	
	@Override
	public void run() {
		
	}
	
	/*
	 * getBoard moet opgevraagd worden na elke zet en aan het begin van het spel
	 */
	public void getBoard(){

	}
		
	public void joinRequest(String clientname){
		
	}

	/*
	 * De client die aan de beurt is stuurt een “doMove” command gevolgd door
	 * een spatie en dan de index waar hij zijn zet wil doen naar de server.
	 */
	public void doMove(int move){
		
	}
	
	/*
	 * opvragen wie aan de beurt is. Returned de naam van de client die aan de beurt is.
	 */
	public void playerTurn(){
		
	}
	
	public void sendMessage(String msg){
		
	}
	
	public void getLeaderBoard(){
		
	}
	
	public void sendLeaderBoard(){
		
	}
}
