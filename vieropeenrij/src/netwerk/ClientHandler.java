package netwerk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

public class ClientHandler extends Thread {
	protected int gameID;
	private Socket socket;
	private BufferedReader in;
	private BufferedWriter out;

	public ClientHandler(Socket socket) throws IOException{
		this.socket = socket;
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	} 
	
	public void run(){
		String msg; 
		try {
			msg = in.readLine();
			while (msg != null){
				handleCommand(msg, out);
				out.newLine();
				out.flush();
				msg = in.readLine();
			}
			close();
		} catch (IOException e){
			
		}
		
	}
	
	public int getGameID(){
		return gameID;
	}
	
	public void setGameID(int gameID){
		this.gameID = gameID;
	}
	
	public void handleCommand(String msg, Writer out){
		
	}
	
	public void close(){
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
