package service;

import dao.OrderDAO;
import model.Order;

import java.util.List;

public class OrderService {
    OrderDAO orderDAO = new OrderDAO();

    public List<Order> getAllItems(Long telegramId){
        return  orderDAO.getAllItems(telegramId);
    }

    public List<Order> getRamsAtPage(int start, int maxPage, Long telegramId){
        return  orderDAO.getItemsAtPage(start, maxPage, telegramId);
    }

    public Order getRamById(Long id){
        return  orderDAO.getItemById(id);
    }

    public void save(Order order){
        orderDAO.save(order);
    }

    public void update(Order order){
        orderDAO.update(order);
    }

    public int getPagesCount(Long telegramId){
        return (int) Math.ceil(getAllItems(telegramId).size() / 8.0);
    }
}
