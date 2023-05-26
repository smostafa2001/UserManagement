package com.nikamooz.javase.workshop.ui;

import com.nikamooz.javase.workshop.data.entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class UserDialog extends JDialog implements ActionListener {
    JLabel messageLbl = new JLabel("");
    JLabel nameLbl = new JLabel("User");
    JLabel passwordLbl = new JLabel("Password");
    JLabel emailLbl = new JLabel("Email");
    JLabel birthdayLbl = new JLabel("Birthday");
    JLabel salaryLbl = new JLabel("Salary");

    JTextField nameFld = new JTextField();
    JTextField passwordFld = new JTextField();
    JTextField emailFld = new JTextField();
    JTextField birthdayFld = new JTextField();
    JTextField salaryFld = new JTextField();

    JButton okBtn = new JButton("Ok");
    JButton cancelBtn = new JButton("Cancel");

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    User user;
    boolean ok;

    public UserDialog(User user) {
        this.user = user;
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
        panel.add(nameLbl);
        panel.add(nameFld);
        panel.add(passwordLbl);
        panel.add(passwordFld);
        panel.add(emailLbl);
        panel.add(emailFld);
        panel.add(birthdayLbl);
        panel.add(birthdayFld);
        panel.add(salaryLbl);
        panel.add(salaryFld);
        panel.add(okBtn);
        panel.add(cancelBtn);
        okBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
        add(panel, BorderLayout.CENTER);
        UIUtility.setLocation(this, 250, 220);
        setModal(true);
        if (user != null) {
            nameFld.setText(user.getName());
            passwordFld.setText(user.getPassword());
            emailFld.setText(user.getEmail());
            birthdayFld.setText(dateFormat.format(user.getBirthday()));
            salaryFld.setText(String.valueOf(user.getSalary()));
        }
        setVisible(true);
    }

    public UserDialog() {
        this(null);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == okBtn) {
                ok = true;
                String name = nameFld.getText();
                String password = passwordFld.getText();
                String email = emailFld.getText();
                String birthday = birthdayFld.getText();
                String salary = salaryFld.getText();
                if (user == null) {
                    user = new User();
                }
                user.setName(name);
                user.setPassword(password);
                user.setEmail(email);
                user.setBirthday(dateFormat.parse(birthday));
                user.setSalary(Double.parseDouble(salary));
                setVisible(false);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public User getUser() {
        return user;
    }

    public boolean isOk() {
        return ok;
    }
}
