package com.nikamooz.javase.workshop.ui;

import com.nikamooz.javase.workshop.data.entity.User;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class UserTableModel extends AbstractTableModel {

    List<User> users = new ArrayList<>();

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int column) {
        if(column==0){
            return I18NUtility.getMessage("general.user");
        }else if(column==1){
            return I18NUtility.getMessage("user.email");
        }else if(column==2){
            return I18NUtility.getMessage("user.birthday");
        }else if(column==3){
            return I18NUtility.getMessage("user.salary");
        }
        return "";
    }

    @Override
    public Object getValueAt(int row, int column) {
        User user = users.get(row);
        if(column==0){
            return user.getName();
        }else if(column==1){
            return user.getEmail();
        }else if(column==2){
            return user.getBirthday();
        }else if(column==3){
            return user.getSalary();
        }
        return "";
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
        fireTableDataChanged();
    }

    public User getSelectedUser(int row) {
        return users.get(row);
    }
}
