package com.nikamooz.javase.workshop.data.dao;

import com.nikamooz.javase.workshop.data.entity.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDAO extends AbstractDAO<User> {

    String userMaxID = "select max(id) from user_tbl";
    String saveSQL = "insert into user_tbl (id, name, password, email, birthday, salary) " +
            "values (?, ?, ?, ?, ?, ?)";
    String updateSQL = "update user_tbl set name=?, password=?, email=?, birthday=?, salary=? where id=?";
    String deleteSQL = "delete from user_tbl where id=?";
    String changePasswordSQL = "update user_tbl set password=? where name=? and password=?";
    String countSQL = "select count(*) from user_tbl ";
    String loginSQL = "select * from user_tbl where name=? and password=?";
    String findAllSQL = "select * from user_tbl ";
    Long currentId;

    Logger logger = Logger.getLogger(UserDAO.class.getName());

    public UserDAO() {
        try {
            PreparedStatement ps = connection.prepareStatement(userMaxID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                currentId = rs.getLong(1);
            } else {
                currentId = 0L;
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User save(User user) throws SQLException {
        logger.info("Save: "+user);
        PreparedStatement ps = connection.prepareStatement(saveSQL);
        currentId++;
        user.setId(currentId);
        ps.setLong(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());
        ps.setString(4, user.getEmail());
        ps.setDate(5, new Date(user.getBirthday().getTime()));
        ps.setDouble(6, user.getSalary());
        ps.executeUpdate();
        return user;
    }

    @Override
    public User update(User user) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(updateSQL);
        ps.setString(1, user.getName());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getEmail());
        ps.setDate(4, new Date(user.getBirthday().getTime()));
        ps.setDouble(5, user.getSalary());
        ps.setLong(6, user.getId());
        ps.executeUpdate();
        connection.commit();
        return user;
    }

    @Override
    public User delete(User user) throws SQLException {
        logger.info("Delete: "+user);
        PreparedStatement ps = connection.prepareStatement(deleteSQL);
        ps.setLong(1, user.getId());
        ps.executeUpdate();
        connection.commit();
        return user;
    }

    @Override
    public int count() throws SQLException {
        PreparedStatement ps = connection.prepareStatement(countSQL);
        Long count = 0L;
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            count = rs.getLong(1);
        }
        connection.commit();
        return count.intValue();
    }

    @Override
    public List<User> findAll() throws SQLException {
        PreparedStatement ps = connection.prepareStatement(findAllSQL);
        List<User> users = new ArrayList<User>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String password = rs.getString("password");
            java.util.Date birthday = rs.getDate("birthday");
            Double salary = rs.getDouble("salary");
            users.add(new User(id, name, password, email, birthday, salary));
        }
        connection.commit();
        return users;
    }

    public boolean login(String name, String password) throws SQLException {
        boolean result = false;
        PreparedStatement ps = connection.prepareStatement(loginSQL);
        ps.setString(1, name);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            result = true;
        }
        connection.commit();
        return result;
    }

    public int changePassword(String user, String currentPass, String newPass) throws SQLException{
        PreparedStatement ps = connection.prepareStatement(changePasswordSQL);
        ps.setString(1, newPass);
        ps.setString(2, user);
        ps.setString(3, currentPass);
        int result = ps.executeUpdate();
        connection.commit();
        return result;
    }

    public void commit() throws SQLException {
        connection.commit();
    }

}
