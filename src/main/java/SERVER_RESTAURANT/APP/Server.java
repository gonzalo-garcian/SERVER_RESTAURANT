package SERVER_RESTAURANT.APP;

import SERVER_RESTAURANT.DAO.DishDAO;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import SERVER_RESTAURANT.DAO.UsersDAO;
import SERVER_RESTAURANT.MODEL.Dish;
import SERVER_RESTAURANT.MODEL.Users;
import SERVER_RESTAURANT.VIEW.Consola;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Server extends Thread {

    private static Cipher rsa;
    PrivateKey privateKey;

    Socket sk;
    String clientIP;
    byte[] encriptado;
    DataInputStream dis;
    DataOutputStream dos;
    ObjectOutputStream oos;

    PublicKey publicKey = new PublicKey() {

        public String getFormat() {
            // TODO Auto-generated method stub
            return null;
        }

        public byte[] getEncoded() {
            // TODO Auto-generated method stub
            return null;
        }

        public String getAlgorithm() {
            // TODO Auto-generated method stub
            return null;
        }
    };

    Consola consola = Consola.getSingletonInstance();

    Server(Socket sk) {
        this.sk = sk;
    }

    @Override
    public void run() {

        try {

            dis = new DataInputStream(sk.getInputStream());
            dos = new DataOutputStream(sk.getOutputStream());
            oos = new ObjectOutputStream(sk.getOutputStream());
            Cryptography crypto = new Cryptography();
            PublicKey publicKey = null;
            
            publicKey = loadPublicKey("publickey.dat");
            privateKey = loadPrivateKey("privatekey.dat");
            rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            clientIP = sk.getInetAddress().getHostAddress();
            consola.escribirSL("The user with the ip : " + clientIP + " has connected");

            // Envio clave publica
            oos.writeObject(publicKey);
            publicKey.toString();

            int option = dis.readInt();
            
            if(option == 1) {
            	
            	String dni = crypto.decryptMessage(receiveMessage());
                String password = crypto.decryptMessage(receiveMessage());

                if (dni == null || password == null) {

                    dos.writeInt(3);
                } else {

                    if (validateLogin(dni, password)) {

                        dos.writeInt(1);
                    } else {

                        dos.writeInt(2);
                    }
                }
            }
            if(option == 2) {
            	
            	//Enviar objetos con un for .
            	System.out.print("Estoy en la opcion 2");
            	
            	List<Dish> dishList = getDishList();
            	
            	System.out.print(dishList.size());
            	dos.writeInt(2);
            	
            	for(int i = 0; i < dishList.size(); i++) {
            		
            		dos.writeUTF(dishList.get(i).getNameDish());
                    dos.writeInt(dishList.get(i).getIdItemDish());
                    dos.writeFloat(dishList.get(i).getPrice());
                    dos.writeInt(dishList.get(i).getQuantityStock());
                    dos.writeInt(dishList.get(i).getStatusDish());
                    dos.writeUTF(dishList.get(i).getDescriptionDish());
                    dos.writeUTF(dishList.get(i).getDniKitchen());

            	}
            	
            }
            
            sk.close();
            dis.close();
            dos.close();
            oos.close();

        } catch (IOException ex) {

            ex.printStackTrace();
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private List<Dish> getDishList(){
    	Consola consola=Consola.getSingletonInstance();
        DishDAO dishDAO = new DishDAO();
        List<Dish> dishList = dishDAO.select();
        
		consola.escribirSL("Listado de pokemons (" + dishList.size() + ")");
	    for (Dish dish : dishList) {
	    	consola.escribirSL("[Nombre: " + dish.getNameDish() + ", aspecto: " + dish.getDescriptionDish() + "]");
	    }
        
        return dishList;
    }

    
    private static void saveKey(Key key, String fileName) throws Exception {
        byte[] publicKeyBytes = key.getEncoded();
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(publicKeyBytes);
        fos.close();
    }

    private static PrivateKey loadPrivateKey(String fileName) throws Exception {
        FileInputStream fis = new FileInputStream(fileName);
        int numBtyes = fis.available();
        byte[] bytes = new byte[numBtyes];
        fis.read(bytes);
        fis.close();

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        KeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
        PrivateKey keyFromBytes = keyFactory.generatePrivate(keySpec);
        return keyFromBytes;
    }

    private static PublicKey loadPublicKey(String fileName) throws Exception {
        FileInputStream fis = new FileInputStream(fileName);
        int numBtyes = fis.available();
        byte[] bytes = new byte[numBtyes];
        fis.read(bytes);
        fis.close();

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        KeySpec keySpec = new X509EncodedKeySpec(bytes);
        PublicKey keyFromBytes = keyFactory.generatePublic(keySpec);
        return keyFromBytes;
    }

    private byte[] receiveMessage() throws Exception {
        byte[] message = null;
        int length = dis.readInt();
        if (length > 0) {
            message = new byte[length];
            dis.readFully(message, 0, message.length);
        }
        return message;

    }
    
    private void sendMessage(byte[] message) throws Exception{
        dos.write(message);
    }
    
    private void sendEncrypted(String message) {

        try {
            rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            rsa.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] encriptado = rsa.doFinal(message.getBytes());
            dos.writeInt(encriptado.length);
            dos.write(encriptado);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
