package netwerk;

import java.io.IOException;
import java.net.ServerSocket;

public class Server extends Thread{
	
	private int thePort;
	
	public Server(int port){
		thePort = port;
	}
	
	@Override
	public void run() {
		try {
			ServerSocket socket = new ServerSocket(thePort);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
