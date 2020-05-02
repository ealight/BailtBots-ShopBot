package service;

import dao.ProductDAO;
import model.Product;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.ceil;

public class ProductService {
    private ProductDAO productDAO = new ProductDAO();

    public List<Product> getAllRams(String category){
        return  productDAO.getAllRams(category);
    }

    public Product getRamById(Long id){
        return  productDAO.getRamById(id);
    }

    public List<Product> getRamsAtPage(int start, int maxPage, String category){
        return  productDAO.getRamsAtPage(start, maxPage, category);
    }

    public List<Product> getItemsByName(String text){
        return  productDAO.getItemsByName(text);
    }

    public List<Product> getItemsByNameAtPage(int start, int maxPage, String text){
        return  productDAO.getItemsByNameAtPage(start, maxPage, text);
    }

    public int getPagesCount(String category){
        return (int) ceil(getAllRams(category).size() / 8.0);
    }

    public int getPagesCountForName(String text){
        return (int) ceil(getItemsByName(text).size() / 8.0);
    }

}
