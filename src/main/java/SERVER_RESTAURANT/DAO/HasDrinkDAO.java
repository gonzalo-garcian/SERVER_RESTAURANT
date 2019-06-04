package SERVER_RESTAURANT.DAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import SERVER_RESTAURANT.MODEL.HasdishId;
import SERVER_RESTAURANT.MODEL.HasdrinkId;
import SERVER_RESTAURANT.UTIL.HibernateUtil;

public class HasDrinkDAO {
	public List<HasdrinkId> selectByIdTicket(int idTicket) {
		 
		List<HasdrinkId> hasdrinkList = null;
		HasdrinkId hasdrinkid = null;
		try {		
			Session session = HibernateUtil.getSessionFactory().openSession();
	        Query q = session.createQuery("From HasDrink d where d.idTicket=:idTicket");
	        q.setParameter("idTicket", idTicket);
	        hasdrinkList = q.list();
	        hasdrinkid = hasdrinkList.get(0);
	        session.close();
		} 
		catch (Exception e) {
			
					
		}
		return hasdrinkList;
	}

}
