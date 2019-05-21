package SERVER_RESTAURANT.APP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Server extends Thread {

	Socket sk;
	String clientIP;

	DataInputStream dis;
	DataOutputStream dos;

	Server(Socket sk) {
		this.sk = sk;
	}

	@Override
	public void run() {
		
		try {
			
			dis = new DataInputStream(sk.getInputStream());
			dos = new DataOutputStream(sk.getOutputStream());

			clientIP = sk.getInetAddress().getHostAddress();
			System.out.println("Conectado el cliente: " + clientIP);


		} catch (IOException ex) {
			
			ex.printStackTrace();
		}
	}
}
