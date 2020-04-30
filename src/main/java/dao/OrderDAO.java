package dao;

import model.Order;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateSessionFactoryUtil;

import java.util.List;

public class OrderDAO {
    public List<Order> getAllItems(Long telegramId){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Order where telegramId = :telegramId and execute = false");
        query.setParameter("telegramId", telegramId);
        List<Order> rams = query.getResultList();
        session.close();
        return rams;
    }

    public List<Order> getItemsAtPage(int start, int maxRows, Long telegramId){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Order where telegramId = :telegramId and execute = false");
        query.setParameter("telegramId", telegramId);
        query.setFirstResult(start);
        query.setMaxResults(maxRows);
        List<Order> rams = query.getResultList();
        session.close();
        return rams;
    }

    public Order getItemById(Long id){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Order where id = :id");
        query.setParameter("id", id);
        List<Order> items = query.list();
        session.close();
        return items.get(0);
    }

    public void update(Order order){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(order);
        transaction.commit();
        session.close();
    }

    public void save(Order order){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(order);
        transaction.commit();
        session.close();
    }
}
