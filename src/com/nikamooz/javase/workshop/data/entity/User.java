package com.nikamooz.javase.workshop.data.entity;

import java.util.Date;

public class User {
    Long id;
    String name;
    String password;
    String email;
    Date birthday;
    Double salary;

    public User() {
    }

    public User(String name, String password, String email, Date birthday, Double salary) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
        this.salary = salary;
    }

    public User(Long id, String name, String password, String email, Date birthday, Double salary) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
