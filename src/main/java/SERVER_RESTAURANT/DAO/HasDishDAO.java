package SERVER_RESTAURANT.DAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import SERVER_RESTAURANT.MODEL.Dish;
import SERVER_RESTAURANT.MODEL.Hasdish;
import SERVER_RESTAURANT.MODEL.HasdishId;
import SERVER_RESTAURANT.UTIL.HibernateUtil;

public class HasDishDAO {
	
	
	
	 public List<HasdishId> selectByIdTicket(int idTicket) {
		 
			List<HasdishId> hasdishList = null;
			HasdishId hasdishid = null;
			try {		
				Session session = HibernateUtil.getSessionFactory().openSession();
		        Query q = session.createQuery("From HasDish d where d.idTicket=:idTicket");
		        q.setParameter("idTicket", idTicket);
		        hasdishList = q.list();
		        hasdishid = hasdishList.get(0);
		        session.close();
			} 
			catch (Exception e) {
				
						
			}
			return hasdishList;
		}

	

}

