/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SERVER_RESTAURANT.DAO;

import SERVER_RESTAURANT.MODEL.Dish;
import SERVER_RESTAURANT.UTIL.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Gonzalo
 */
public class DishDAO {
    
    public Dish select(int idItemDish) {
		List<Dish> dishList = null;
		Dish dish = null;
		try {		
			Session session = HibernateUtil.getSessionFactory().openSession();
	        Query q = session.createQuery("From Dish d where d.idItemDish=:idItemDish");
	        q.setParameter("idItemDish", idItemDish);
	        dishList = q.list();
	        dish = dishList.get(0);
	        session.close();
		} 
		catch (Exception e) {
			
					
		}
		return dish;
	}
}
