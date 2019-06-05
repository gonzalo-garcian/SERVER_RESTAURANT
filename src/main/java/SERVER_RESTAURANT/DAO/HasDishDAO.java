package SERVER_RESTAURANT.DAO;

import SERVER_RESTAURANT.MODEL.Hasdish;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import SERVER_RESTAURANT.MODEL.HasdishId;
import SERVER_RESTAURANT.UTIL.HibernateUtil;

public class HasDishDAO {
	
	public List<Hasdish> select(){
		List<Hasdish> hasdishList = null;
		try {		
			Session session = HibernateUtil.getSessionFactory().openSession();
	        Query q = session.createQuery("From Hasdish");        
	        hasdishList = q.list();
	        session.close();
		} 
		catch (Exception e) {
			e.printStackTrace();			
		}
		return hasdishList;
	}

    public Hasdish select(int idTicket) {
        List<Hasdish> hasdishList = null;
        Hasdish hasdish = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Query q = session.createQuery("From Hasdish");
            hasdishList = q.list();
            hasdish = hasdishList.get(1);
            session.close();
        } catch (Exception e) {

        }
        return hasdish;
    }

}
