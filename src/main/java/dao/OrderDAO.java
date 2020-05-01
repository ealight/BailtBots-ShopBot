package dao;

import model.Order;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateSessionFactoryUtil;

import java.util.List;

public class OrderDAO {
    public List getAllItems(Long telegramId){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Order where telegramId = :telegramId and execute = false");
        query.setParameter("telegramId", telegramId);
        List rams = query.getResultList();
        session.close();
        return rams;
    }

    public List getItemsAtPage(int start, int maxRows, Long telegramId){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Order where telegramId = :telegramId and execute = false");
        query.setParameter("telegramId", telegramId);
        query.setFirstResult(start);
        query.setMaxResults(maxRows);
        List rams = query.getResultList();
        session.close();
        return rams;
    }

    public Order getItemById(Long id){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Order where id = :id");
        query.setParameter("id", id);
        List items = query.list();
        session.close();
        return (Order) items.get(0);
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
