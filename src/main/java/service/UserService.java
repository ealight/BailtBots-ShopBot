package service;

import dao.UserDAO;
import model.User;

import java.util.HashSet;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    public boolean isUserExist(Long telegramID) {
        if (userDAO.findByTelegramID(telegramID) == null) {
            return false;
        }
        return true;
    }

    public User getByTelegramId(Long telegramID) {
        return userDAO.findByTelegramID(telegramID);
    }

    public void save(User user){
        userDAO.save(user);
    }

    public void update(User user){
        userDAO.update(user);
    }
}
