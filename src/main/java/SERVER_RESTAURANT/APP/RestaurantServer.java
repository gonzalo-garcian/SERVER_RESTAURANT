package SERVER_RESTAURANT.APP;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import SERVER_RESTAURANT.DAO.UsersDAO;
import SERVER_RESTAURANT.MODEL.Users;
import SERVER_RESTAURANT.VIEW.Consola;

public class RestaurantServer {

	public RestaurantServer() {
		
	}

	public static void main(String[] args) {
		try {
			ServerSocket ssk = new ServerSocket(20002);
			System.out.println("Servidor iniciado");
			
			opcionRead();

			while (true) {
				Socket sk = ssk.accept();
				Server ser = new Server(sk);
				ser.start();
			}
		} catch (IOException ex) {

			ex.printStackTrace();
		}
	}
	
	public static void opcionRead() {
		
		Consola consola=Consola.getSingletonInstance();
		UsersDAO usersDAO = new UsersDAO();
		List<Users> usersList = usersDAO.select();
		
		consola.escribirSL("Users list (" + usersList.size() + ")");
		
	    for (Users user : usersList) {
	    	consola.escribirSL("[DNI : " + user.getDni() + " Name : " + user.getFirstName() + " Surnames : " + user.getSurnames() + "]");
	    }
	}
}
