package SERVER_RESTAURANT.APP;

import SERVER_RESTAURANT.DAO.DinnerTableDAO;
import SERVER_RESTAURANT.DAO.DishDAO;
import SERVER_RESTAURANT.DAO.DrinkDAO;
import SERVER_RESTAURANT.DAO.HasDishDAO;
import SERVER_RESTAURANT.DAO.HasDrinkDAO;
import SERVER_RESTAURANT.DAO.TicketDAO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import SERVER_RESTAURANT.DAO.UsersDAO;
import SERVER_RESTAURANT.MODEL.DinnerTable;
import SERVER_RESTAURANT.MODEL.Dish;
import SERVER_RESTAURANT.MODEL.Drink;
import SERVER_RESTAURANT.MODEL.Hasdish;
import SERVER_RESTAURANT.MODEL.HasdishId;
import SERVER_RESTAURANT.MODEL.Hasdrink;
import SERVER_RESTAURANT.MODEL.HasdrinkId;
import SERVER_RESTAURANT.MODEL.Ticket;
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
import java.util.ArrayList;
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

            if (option == 1) {

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
            if (option == 2) {

                // Enviar objetos con un for .
                System.out.print("Estoy en la opcion 2");

                List<Dish> dishList = getDishList();

                System.out.print(dishList.size());
                dos.writeInt(dishList.size());

                for (int i = 0; i < dishList.size(); i++) {

                    dos.writeUTF(dishList.get(i).getNameDish());
                    dos.writeInt(dishList.get(i).getIdItemDish());
                    dos.writeFloat(dishList.get(i).getPrice());
                    dos.writeInt(dishList.get(i).getQuantityStock());
                    dos.writeInt(dishList.get(i).getStatusDish());
                    dos.writeUTF(dishList.get(i).getDescriptionDish());
                    dos.writeUTF(dishList.get(i).getDniKitchen());

                }
            }
            if (option == 3) {

                // Enviar objetos con un for .
                System.out.print("Estoy en la opcion 3");
                List<Ticket> ticketList = getTicketList();

                System.out.print(ticketList.size());
                dos.writeInt(ticketList.size());

                for (int i = 0; i < ticketList.size(); i++) {

                    dos.writeInt(ticketList.get(i).getIdTicket());
                    dos.writeFloat(ticketList.get(i).getTotalPrice());
                    dos.writeInt(ticketList.get(i).getIdTable());
                }
            }
            if (option == 4) {

                int idItemDish = dis.readInt();
                int quantityStock = dis.readInt();
                updateDish(idItemDish, quantityStock);

            }
            if (option == 5) {
                int idItemDish = dis.readInt();
                deleteDish(idItemDish);
            }
            if (option == 6) {

                String nameDish = dis.readUTF();
                float price = dis.readFloat();
                int quantityStock = dis.readInt();
                String descriptionDish = dis.readUTF();
                String dniKitchen = dis.readUTF();

                insertDish(nameDish, price, quantityStock, descriptionDish, dniKitchen);

            }

            if (option == 7) {

				int idTicket = dis.readInt();
				System.out.println(idTicket);
				List<Dish> dishList = getDishListFromTicket(idTicket);
				List<Drink> drinkList = getDrinkListFromTicket(idTicket);
				double cantidad = 0;
				double precioTotal = 0;
				
				String ticket = "Ticket con ID : " + idTicket + "\n";
				ticket = ticket + "Platos : \n";
				ticket = ticket + "-----------------------------------------------\n";
				for (Dish d : dishList) {
					cantidad = getQuantityDish(idTicket, d.getIdItemDish());
					ticket = ticket + d.getNameDish();
					ticket = ticket + "  " + d.getPrice() + "â‚¬  ";
					ticket = ticket + "Cantidad : " + cantidad + "\n";
					precioTotal = precioTotal + (d.getPrice()*cantidad);
					
				}
				ticket = ticket + "Bebidas : \n";
				ticket = ticket + "-----------------------------------------------\n";
				for (Drink d : drinkList) {
					cantidad = getQuantityDrink(idTicket, d.getIdItemDrink());
					ticket = ticket + d.getNameDrink();
					ticket = ticket + " " + d.getPrice() + "â‚¬   ";
					ticket = ticket + "Cantidad : " + cantidad + "\n";
					precioTotal = precioTotal + (d.getPrice()*cantidad);
					}
				ticket = ticket + "Precio final : " + precioTotal + "â‚¬\n";
				ticket = ticket + "-----------------------------------------------\n";
				System.out.println(ticket);
				dos.writeUTF(ticket);
            }

            if (option == 8) {

                // Enviar objetos con un for .
                System.out.print("Estoy en la opcion 8");

                List<Drink> drinkList = getDrinkList();

                System.out.print(drinkList.size());
                dos.writeInt(drinkList.size());

                for (int i = 0; i < drinkList.size(); i++) {

                    dos.writeUTF(drinkList.get(i).getNameDrink());
                    dos.writeInt(drinkList.get(i).getIdItemDrink());
                    dos.writeFloat(drinkList.get(i).getPrice());
                    dos.writeInt(drinkList.get(i).getQuantityStock());
                    dos.writeInt(drinkList.get(i).getStatusDrink());
                    dos.writeUTF(drinkList.get(i).getDescriptionDrink());

                }

            }
            if (option == 9) {

                String nameDish = dis.readUTF();
                float price = dis.readFloat();
                int quantityStock = dis.readInt();
                String descriptionDish = dis.readUTF();

                insertDrink(nameDish, price, quantityStock, descriptionDish);
            }
            if (option == 10) {
                int idItemDrink = dis.readInt();
                int quantityStock = dis.readInt();
                updateDrink(idItemDrink, quantityStock);
            }
            if (option == 11) {

                int idItemDrink = dis.readInt();
                deleteDrink(idItemDrink);
            }
            if (option == 12) {
                //devuelve una lista con todos los usuarios
                List<Users> usersList = selectAllUsers();
                if (usersList != null) {
                    System.out.println(usersList.size());
                    dos.writeInt(usersList.size());

                    for (int i = 0; i < usersList.size(); i++) {
                        dos.writeUTF(usersList.get(i).getDni());
                        dos.writeUTF(usersList.get(i).getFirstName());
                        dos.writeUTF(usersList.get(i).getSurnames());
                        dos.writeUTF(usersList.get(i).getPhoneNumber());
                        dos.writeInt(usersList.get(i).getKind());
                    }
                }
            }
            if (option == 13) {
                //elimina un usuario de la BBDD
                String dniUser = dis.readUTF();
                deleteUser(dniUser);
            }
            if (option == 14) {
                //actualiza un user
                String dni = dis.readUTF();
                Users user = getUser(dni);
                int kind = dis.readInt();
                updateUser(user);
            }
            if (option == 15) {
                //inserta un user nuevo
                String dni = dis.readUTF();
                String firstName = dis.readUTF();
                String surnames = dis.readUTF();
                String phoneNumber = dis.readUTF();
                String accessKey = dis.readUTF();
                int kind = dis.readInt();
                Users user = new Users(dni, firstName, surnames, phoneNumber, accessKey, kind);
                insertUser(user);
            }
            if (option == 16) {
                //actualiza el kind de un usuario
                String dni = dis.readUTF();
                Users user = getUser(dni);
                int kind = dis.readInt();
                changeKind(user, kind);
            }
            if (option == 17) {
                String dni = dis.readUTF();
                Users user = getUser(dni);
                dos.writeUTF(user.getFirstName());
                dos.writeUTF(user.getSurnames());
                dos.writeUTF(user.getPhoneNumber());
                dos.writeInt(user.getKind());
            }
            if (option == 18) {
                
                List<DinnerTable> dinnerTableList = getDinnerTable();
                if (dinnerTableList != null) {
                    System.out.println(dinnerTableList.size());
                    dos.writeInt(dinnerTableList.size());

                    for (int i = 0; i < dinnerTableList.size(); i++) {
                        
                        dos.writeInt(dinnerTableList.get(i).getIdTable());
                        dos.writeUTF(dinnerTableList.get(i).getLocationTable());
                        dos.writeInt(dinnerTableList.get(i).getNumberDinersTable());
                    }
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
    
    private List<DinnerTable> getDinnerTable(){
        
        DinnerTableDAO dinnerTableDAO = new DinnerTableDAO();
        List<DinnerTable> dinnerTableList = dinnerTableDAO.select();
    
         return dinnerTableList;   
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

    // TODO LO QUE ES DE DISH EMPIEZA 
    private List<Dish> getDishList() {
        Consola consola = Consola.getSingletonInstance();
        DishDAO dishDAO = new DishDAO();
        List<Dish> dishList = dishDAO.select();

        consola.escribirSL("Listado de platos (" + dishList.size() + ")");
        for (Dish dish : dishList) {
            consola.escribirSL("[Nombre: " + dish.getNameDish() + ", aspecto: " + dish.getDescriptionDish() + "]");
        }
        return dishList;
    }

    private List<Ticket> getTicketList() {
        Consola consola = Consola.getSingletonInstance();
        TicketDAO ticketDAO = new TicketDAO();
        List<Ticket> ticketList = ticketDAO.select();

        consola.escribirSL("Lista Tickets : " + ticketList.size());
        for (Ticket ticket : ticketList) {
            consola.escribirSL("ID Ticket : " + ticket.getIdTicket() + "/n Precio Ticket :  " + ticket.getTotalPrice()
                    + "/n ID Mesa : " + ticket.getIdTable());
        }
        return ticketList;
    }
    
    private int getQuantityDish(int idTicket, int idDish) {
		HasDishDAO hasDishDAO = new HasDishDAO();
		List<Hasdish> hasDishList = hasDishDAO.select();
		int quantityFinal = 0;
		for (Hasdish hd : hasDishList) {
			if ((hd.getId().getIdItemDish() == idDish) && (hd.getId().getIdTicket() == idTicket)) {
				int quantity = hd.getQuantityItem().intValue();
				quantityFinal = quantity;
			}
		}
		return quantityFinal;
    }

    
    private List<Dish> getDishListFromTicket(int idTicket) {
		DishDAO dishDAO = new DishDAO();
		HasDishDAO hasDishDAO = new HasDishDAO();
		List<Dish> dishList = dishDAO.select();
		List<Dish> dishListEmpty = new ArrayList<Dish>();
		List<Hasdish> hasDishList = hasDishDAO.select();

		for (Hasdish hd : hasDishList) {
			if (hd.getId().getIdTicket() == idTicket) {
				int idDish = hd.getId().getIdItemDish();
				for (Dish d : dishList) {
					if (d.getIdItemDish() == idDish) {
						dishListEmpty.add(d);
					}

				}
			}
		}
		return dishListEmpty;
	}

    private List<Drink> getDrinkListFromTicket(int idTicket) {
		DrinkDAO drinkDAO = new DrinkDAO();
		HasDrinkDAO hasDrinkDAO = new HasDrinkDAO();
		List<Drink> drinkList = drinkDAO.select();
		List<Drink> drinkListEmpty = new ArrayList<Drink>();
		List<Hasdrink> hasDrinkList = hasDrinkDAO.select();

		for (Hasdrink hd : hasDrinkList) {
			if (hd.getId().getIdTicket() == idTicket) {
				int idDrink = hd.getId().getIdItemDrink();

				for (Drink d : drinkList) {
					if (d.getIdItemDrink() == idDrink) {
						drinkListEmpty.add(d);
					}

				}
			}
		}
		return drinkListEmpty;
	}

	private int getQuantityDrink(int id_ticket, int id_drink) {
		HasDrinkDAO hasDrinkDAO = new HasDrinkDAO();
		List<Hasdrink> hasDrinkList = hasDrinkDAO.select();
		int quantity = 1;

		for (Hasdrink hd : hasDrinkList) {
			if ((hd.getId().getIdItemDrink() == id_drink) && (hd.getId().getIdTicket() == id_ticket)) {
				quantity = hd.getQuantityItem().intValue();
			}
		}
		return quantity;
	}

    private void updateDish(int idItemDish, int quantityStock) {

        DishDAO dishDAO = new DishDAO();
        Dish dish = new Dish();

        if (dishDAO.exists(idItemDish)) {

            dish = dishDAO.select(idItemDish);
            dish.setQuantityStock(quantityStock);
            dishDAO.update(dish);
        }

    }

    private void deleteDish(int idItemDish) {

        DishDAO dishDAO = new DishDAO();
        Dish dish = new Dish();

        if (dishDAO.exists(idItemDish)) {

            dish = dishDAO.select(idItemDish);
            dishDAO.delete(dish);
        }
    }

    public void insertDish(String nameDish, float price, int quantityStock, String descriptionDish, String dniKitchen) {
        DishDAO dishDAO = new DishDAO();
        UsersDAO usersDAO = new UsersDAO();
        Dish dish;

        if (!usersDAO.exists(dniKitchen)) {
            consola.escribirSL("ERROR: Ya existe un dish con ese nombre");
        } else {
            dish = new Dish(nameDish, price, quantityStock, descriptionDish, dniKitchen);
            dishDAO.insert(dish);
        }
    }

//TODO LO QUE ES DE DISH ACABA
    //
    private List<Drink> getDrinkList() {
        Consola consola = Consola.getSingletonInstance();
        DrinkDAO drinkDAO = new DrinkDAO();
        List<Drink> drinkList = drinkDAO.select();

        consola.escribirSL("Listado de platos (" + drinkList.size() + ")");
        for (Drink dish : drinkList) {
            consola.escribirSL("[Nombre: " + dish.getNameDrink() + ", aspecto: " + dish.getDescriptionDrink() + "]");
        }
        return drinkList;
    }

    private void updateDrink(int idItemDrink, int quantityStock) {

        DrinkDAO drinkDAO = new DrinkDAO();
        Drink drink = new Drink();

        if (drinkDAO.exists(idItemDrink)) {

            drink = drinkDAO.select(idItemDrink);
            drink.setQuantityStock(quantityStock);
            drinkDAO.update(drink);
        }

    }

    private void deleteDrink(int idItemDrink) {

        DrinkDAO drinkDAO = new DrinkDAO();
        Drink drink = new Drink();

        if (drinkDAO.exists(idItemDrink)) {

            drink = drinkDAO.select(idItemDrink);
            drinkDAO.delete(drink);
        }
    }

    public void insertDrink(String nameDish, float price, int quantityStock, String descriptionDish) {
        DrinkDAO dishDAO = new DrinkDAO();
        Drink drink;

        drink = new Drink(nameDish, price, quantityStock, descriptionDish);
        dishDAO.insert(drink);
    }

    private List<Users> selectAllUsers() {
        Consola consola = Consola.getSingletonInstance();
        UsersDAO uDAO = new UsersDAO();
        List<Users> lUsers = uDAO.getAll();
        if (lUsers != null) {
            for (Users user : lUsers) {
                consola.escribirSL("[Nombre: " + user.getFirstName() + ", DNI: " + user.getDni() + "]");
            }
        }
        return lUsers;
    }

    private void updateUser(Users user) {
        UsersDAO uDAO = new UsersDAO();
        Users usu = new Users();
        if (uDAO.exists(user.getDni())) {
            usu = uDAO.select(user.getDni());
            if (usu.getAccessKey() != user.getAccessKey()) {
                usu.setAccessKey(user.getAccessKey());
            }
            if (usu.getFirstName() != user.getFirstName()) {
                usu.setFirstName(user.getFirstName());
            }
            if (usu.getKind() != user.getKind()) {
                usu.setKind(user.getKind());
            }
            if (usu.getPhoneNumber() != user.getPhoneNumber()) {
                usu.setPhoneNumber(user.getPhoneNumber());
            }
            if (usu.getSurnames() != user.getPhoneNumber()) {
                usu.setSurnames(usu.getSurnames());
            }
            uDAO.update(user);
        }
    }

    private void deleteUser(String dni) {
        UsersDAO uDAO = new UsersDAO();
        if (uDAO.exists(dni)) {
            Users user = new Users();
            user = uDAO.select(dni);
            uDAO.delete(user);
        }
    }

    private void insertUser(Users user) {
        UsersDAO uDAO = new UsersDAO();
        if (!uDAO.exists(user.getDni())) {
            uDAO.insert(user);
        } else {
            consola.escribirSL("ERROR: ESE USUARIO YA EXISTE");
        }
    }

    private void updateUserDirecto(Users user) {
        UsersDAO uDAO = new UsersDAO();
        if (uDAO.exists(user.getDni())) {
            uDAO.update(user);
            consola.escribirSL("Usuario actualizado con DNI: " + user.getDni());
        }
    }

    private void changeKind(Users user, int kind) {
        UsersDAO uDAO = new UsersDAO();
        if (uDAO.exists(user.getDni())) {
            if (user.getKind() != kind) {
                if (kind == 1 || kind == 2 || kind == 3) {
                    user.setKind(kind);
                    uDAO.update(user);
                }
            }
        }
    }

    private Users getUser(String dni) {
        UsersDAO uDAO = new UsersDAO();
        Users user = new Users();
        if (uDAO.exists(dni)) {
            user = uDAO.select(dni);
        }
        return user;
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

    private void sendMessage(byte[] message) throws Exception {
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
