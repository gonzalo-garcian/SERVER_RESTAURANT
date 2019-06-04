package SERVER_RESTAURANT.DAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import SERVER_RESTAURANT.MODEL.Users;
import SERVER_RESTAURANT.UTIL.HibernateUtil;
import SERVER_RESTAURANT.VIEW.Consola;

public class UsersDAO {

    Consola console = Consola.getSingletonInstance();

    public Users select(String dni) {
        List<Users> usersList = null;
        Users users = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Query q = session.createQuery("From Users d where d.dni=:dni");
            q.setParameter("dni", dni);
            usersList = q.list();
            users = usersList.get(0);
            session.close();
        } catch (Exception e) {

        }
        return users;
    }

    public boolean exists(String dni) {
        List<Users> usersList = null;
        boolean exists = false;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Query q = session.createQuery("From Users d where d.dni=:dni");
            q.setParameter("dni", dni);
            usersList = q.list();
            if (usersList.size() != 0) {
                exists = true;
            }
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exists;
    }
    
    public List<Users> getAll(){
        List<Users> listUser = null;
        try{
            Session session = HibernateUtil.getSessionFactory().openSession();
            Query q = session.createQuery("From Ticket");
            listUser = q.list();
            session.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return listUser;
    }
    
    public void update(Users user){
        try{
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
            session.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void insert(Users user){
        try{
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    } catch(Exception e){
        e.printStackTrace();
    }
    }
    
    public void delete(Users user){
        try{
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}

//    private List<Drink> getDrinkList() {
//        Consola consola = Consola.getSingletonInstance();
//        DrinkDAO drinkDAO = new DrinkDAO();
//        List<Drink> drinkList = drinkDAO.select();
//
//        consola.escribirSL("Listado de platos (" + drinkList.size() + ")");
//        for (Drink dish : drinkList) {
//            consola.escribirSL("[Nombre: " + dish.getNameDrink() + ", aspecto: " + dish.getDescriptionDrink() + "]");
//        }
//        return drinkList;
//    }

//    private void updateDrink(int idItemDrink, int quantityStock) {
//
//        DrinkDAO drinkDAO = new DrinkDAO();
//        Drink drink = new Drink();
//
//        if (drinkDAO.exists(idItemDrink)) {
//
//            drink = drinkDAO.select(idItemDrink);
//            drink.setQuantityStock(quantityStock);
//            drinkDAO.update(drink);
//        }
//
//    }
//    private void deleteDrink(int idItemDrink) {
//
//        DrinkDAO drinkDAO = new DrinkDAO();
//        Drink drink = new Drink();
//
//        if (drinkDAO.exists(idItemDrink)) {
//
//            drink = drinkDAO.select(idItemDrink);
//            drinkDAO.delete(drink);
//        }
//    }

//    public void insertDish(String nameDish, float price, int quantityStock, String descriptionDish, String dniKitchen) {
//        DishDAO dishDAO = new DishDAO();
//        UsersDAO usersDAO = new UsersDAO();
//        Dish dish;
//
//        if (!usersDAO.exists(dniKitchen)) {
//            consola.escribirSL("ERROR: Ya existe un dish con ese nombre");
//        } else {
//            dish = new Dish(nameDish, price, quantityStock, descriptionDish, dniKitchen);
//            dishDAO.insert(dish);
//        }
//    }