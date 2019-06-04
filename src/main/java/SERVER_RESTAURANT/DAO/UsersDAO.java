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

}
