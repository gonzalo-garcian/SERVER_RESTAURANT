package SERVER_RESTAURANT.APP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import SERVER_RESTAURANT.DAO.UsersDAO;
import SERVER_RESTAURANT.MODEL.Users;
import SERVER_RESTAURANT.VIEW.Consola;

public class Server extends Thread {

	Socket sk;
	String clientIP;

	DataInputStream dis;
	DataOutputStream dos;

	Consola consola = Consola.getSingletonInstance();

	Server(Socket sk) {
		this.sk = sk;
	}

	@Override
	public void run() {

		try {

			dis = new DataInputStream(sk.getInputStream());
			dos = new DataOutputStream(sk.getOutputStream());

			clientIP = sk.getInetAddress().getHostAddress();
			consola.escribirSL("The user with the ip : " + clientIP + " has connected");

			
			//Esto debería estar cifrado.
			String dni = dis.readUTF();
			String password = dis.readUTF();
			
			if(dni == null || password == null) {
				
				dos.writeInt(3);
			}else {	
				
				if (validateLogin(dni, password)) {

					dos.writeInt(1);
				} else {
					
					dos.writeInt(2);
				}
			}


		} catch (IOException ex) {

			ex.printStackTrace();
		}
	}

	private boolean validateLogin(String dni, String password) {

		Users user = new Users();
		UsersDAO usersDAO = new UsersDAO();

		if (usersDAO.exists(dni)) {

			user = usersDAO.select(dni);

			if (user.getAccessKey().equals(password)) {

				return true;
			}

		}

		return false;

	}
}
