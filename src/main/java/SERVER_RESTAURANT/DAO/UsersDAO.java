package SERVER_RESTAURANT.DAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import SERVER_RESTAURANT.MODEL.Users;
import SERVER_RESTAURANT.UTIL.HibernateUtil;

public class UsersDAO {

	@SuppressWarnings("unchecked")
	public List<Users> select() {
		List<Users> listEntrenador = null;
		try {		
			Session session = HibernateUtil.getSessionFactory().openSession();
	        Query q = session.createQuery("From Users");        
	        listEntrenador = q.list();
	        session.close();
		} 
		catch (Exception e) {
			e.printStackTrace();			
		}
		return listEntrenador;
	}
}
