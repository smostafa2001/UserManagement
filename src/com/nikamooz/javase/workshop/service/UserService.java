package com.nikamooz.javase.workshop.service;

import com.nikamooz.javase.workshop.data.dao.UserDAO;
import com.nikamooz.javase.workshop.data.entity.User;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class UserService {
    UserDAO userDAO = new UserDAO();

    static Logger logger = Logger.getLogger(UserService.class.getName());

    public User save(User user){
        try {
            if(!user.getEmail().matches("^(.+)@(.+)$")){
                throw new ServiceException("Email Validation Error:"+user.getEmail());
            }
            user = userDAO.save(user);
            userDAO.commit();
            return user;
        } catch (SQLException e) {
            logger.severe("Error:"+e.getMessage());
            throw new ServiceException(e);
        }
    }
    public User update(User user){
        try {
            return userDAO.update(user);
        } catch (SQLException e) {
            logger.severe("Error:"+e.getMessage());
            throw new ServiceException(e);
        }
    }
    public User delete(User user){
        try {
            return userDAO.delete(user);
        } catch (SQLException e) {
            logger.severe("Error:"+e.getMessage());
            throw new ServiceException(e);
        }
    }
    public int count(){
        try {
            return userDAO.count();
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
    public List<User> findAll(){
        try {
            return userDAO.findAll();
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

}
