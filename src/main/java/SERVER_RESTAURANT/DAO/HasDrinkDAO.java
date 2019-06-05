package SERVER_RESTAURANT.DAO;

import SERVER_RESTAURANT.MODEL.Hasdrink;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import SERVER_RESTAURANT.MODEL.HasdrinkId;
import SERVER_RESTAURANT.UTIL.HibernateUtil;

public class HasDrinkDAO {

    public List<Hasdrink> select() {
        List<Hasdrink> hasdrinkList = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Query q = session.createQuery("From Hasdrink");
            hasdrinkList = q.list();
            session.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return hasdrinkList;
    }

}
