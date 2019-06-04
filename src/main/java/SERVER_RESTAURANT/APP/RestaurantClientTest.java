package SERVER_RESTAURANT.APP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class RestaurantClientTest {

    static Socket sk;

    static DataInputStream dis;
    static DataOutputStream dos;

    public static void main(String[] args) {

        try {
            String ip = "192.168.137.1";
            sk = new Socket(ip, 20002);
            System.out.println("Establecida la conexión con " + ip);
            dis = new DataInputStream(sk.getInputStream());
            dos = new DataOutputStream(sk.getOutputStream());

            //Aquí en el cliente de android serían getText de los EditText y se enviarán cifrados.
            dos.writeUTF("45992171Z");
            dos.writeUTF("GonzaloPass");

            boolean validatedLogin = dis.readBoolean();

            if (validatedLogin) {

                System.out.println("Login correcto");

                //Pasa de Activity.
            } else {

                System.out.println("Login fallido");
                //Se queda en la Activity y muestra un mensaje de Error.
            }

            byte[] encriptado = null;
            System.out.println(dis.read(encriptado));

        } catch (IOException ex) {

        }
    }

}
