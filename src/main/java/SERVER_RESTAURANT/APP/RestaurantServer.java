package SERVER_RESTAURANT.APP;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import SERVER_RESTAURANT.DAO.UsersDAO;
import SERVER_RESTAURANT.MODEL.Users;
import SERVER_RESTAURANT.VIEW.Consola;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;

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
