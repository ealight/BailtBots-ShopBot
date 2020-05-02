package dao;

import model.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateSessionFactoryUtil;

import java.util.List;

public class ProductDAO {
    public List<Product> getAllRams(String category){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Product where category = :category");
        query.setParameter("category", category);
        List<Product> rams = query.getResultList();
        session.close();
        return rams;
    }

    public List<Product> getRamsAtPage(int start, int maxRows, String category){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Product where category = :category");
        query.setParameter("category", category);
        query.setFirstResult(start);
        query.setMaxResults(maxRows);
        List<Product> rams = query.getResultList();
        session.close();
        return rams;
    }

    public Product getRamById(Long id){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Product where id = :id");
        query.setParameter("id", id);
        List<Product> products = query.list();
        session.close();
        return products.get(0);
    }

    public List<Product> getItemsByName(String text){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Product where name like :text");
        query.setParameter("text", "%" + text + "%");
        List<Product> rams = query.getResultList();
        session.close();
        return rams;
    }

    public List<Product> getItemsByNameAtPage(int start, int maxRows, String text){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Product where name like :text");
        query.setParameter("text", "%" + text + "%");
        query.setFirstResult(start);
        query.setMaxResults(maxRows);
        List<Product> rams = query.getResultList();
        session.close();
        return rams;
    }

    public void update(Product ram){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(ram);
        transaction.commit();
        session.close();
    }

    public void save(Product ram){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(ram);
        transaction.commit();
        session.close();
    }
}
