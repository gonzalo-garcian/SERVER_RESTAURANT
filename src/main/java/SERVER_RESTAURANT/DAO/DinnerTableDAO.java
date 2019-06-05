/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SERVER_RESTAURANT.DAO;

import SERVER_RESTAURANT.MODEL.DinnerTable;
import SERVER_RESTAURANT.UTIL.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Gonzalo
 */
public class DinnerTableDAO {
    
    public DinnerTable select(int idItemDish) {
        List<DinnerTable> dishList = null;
        DinnerTable dish = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Query q = session.createQuery("From DinnerTable d where d.idItemDish=:idItemDish");
            q.setParameter("idItemDish", idItemDish);
            dishList = q.list();
            dish = dishList.get(0);
            session.close();
        } catch (Exception e) {

        }
        return dish;
    }

    public List<DinnerTable> select() {
        List<DinnerTable> listEntrenador = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Query q = session.createQuery("From DinnerTable");
            listEntrenador = q.list();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listEntrenador;
    }

    public void update(DinnerTable dish) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(dish);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean exists(int idItemDish) {
        List<DinnerTable> usersList = null;
        boolean exists = false;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Query q = session.createQuery("From DinnerTable d where d.idItemDish=:idItemDish");
            q.setParameter("idItemDish", idItemDish);
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

    public void insert(DinnerTable dish) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(dish);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(DinnerTable dish) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(dish);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
