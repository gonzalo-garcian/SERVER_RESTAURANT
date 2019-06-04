package SERVER_RESTAURANT.DAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import SERVER_RESTAURANT.MODEL.Dish;
import SERVER_RESTAURANT.MODEL.Drink;
import SERVER_RESTAURANT.UTIL.HibernateUtil;

public class DrinkDAO {

    public Drink select(int idItemDrink) {
        List<Drink> drinkList = null;
        Drink drink = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Query q = session.createQuery("From Drink d where d.idItemDrink=:idItemDrink");
            q.setParameter("idItemDrink", idItemDrink);
            drinkList = q.list();
            drink = drinkList.get(0);
            session.close();
        } catch (Exception e) {

        }
        return drink;
    }

    public List<Drink> select() {
        List<Drink> drinkList = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Query q = session.createQuery("From Drink");
            drinkList = q.list();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drinkList;
    }

    public void update(Drink drink) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(drink);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean exists(int idItemDrink) {
        List<Drink> drinkList = null;
        boolean exists = false;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Query q = session.createQuery("From Drink d where d.idItemDrink=:idItemDrink");
            q.setParameter("idItemDrink", idItemDrink);
            drinkList = q.list();
            if (drinkList.size() != 0) {
                exists = true;
            }
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exists;
    }

    public void delete(Drink drink) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(drink);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
