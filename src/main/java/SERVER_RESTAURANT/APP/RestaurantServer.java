package SERVER_RESTAURANT.APP;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import SERVER_RESTAURANT.DAO.DishDAO;
import SERVER_RESTAURANT.MODEL.Dish;

public class RestaurantServer {

    public RestaurantServer() {

    }

    public static void main(String[] args) {   

        try {
            ServerSocket ssk = new ServerSocket(20002);
            System.out.println("Server online");

            while (true) {
                Socket sk = ssk.accept();
                Server ser = new Server(sk);
                ser.start();
            }
        } catch (IOException ex) {

            ex.printStackTrace();
        }
    }
}
