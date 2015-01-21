package netwerk;

public class Client extends Thread {
	
	private int thePort;
	private String theName;
	
	public Client(String InetAdress, int port, String name) {
		thePort = port;
		theName = name;
	}
	
	@Override
	public void run() {
		
	}
}
