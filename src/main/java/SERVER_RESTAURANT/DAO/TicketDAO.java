package SERVER_RESTAURANT.DAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import SERVER_RESTAURANT.MODEL.Ticket;
import SERVER_RESTAURANT.UTIL.HibernateUtil;

public class TicketDAO {

    public Ticket select(int idTicket) {
        List<Ticket> ticketList = null;
        Ticket ticket = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Query q = session.createQuery("From Ticket t where t.idTicket=:idTicket");
            q.setParameter("idTicket", idTicket);
            ticketList = q.list();
            ticket = ticketList.get(0);
            session.close();
        } catch (Exception e) {

        }
        return ticket;
    }

    public List<Ticket> select() {
        List<Ticket> listEntrenador = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Query q = session.createQuery("From Ticket");
            listEntrenador = q.list();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listEntrenador;
    }

}
