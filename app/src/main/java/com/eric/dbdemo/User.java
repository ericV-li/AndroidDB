package com.eric.dbdemo;

import com.eric.db.annotation.Column;
import com.eric.db.annotation.Table;

import java.io.Serializable;

/**
 * @author li
 * @Package com.eric.dbdemo
 * @Title: User
 * @Description: Copyright (c)
 * Create DateTime: 2017/05/01
 */

@Table(name = "t_user")
public class User implements Serializable {

    @Column(isId = true, name = "id", autoGen = true)
    private int id;

    @Column(name = "userId", property = "UNIQUE")
    private String userId;

    @Column(name = "userName")
    private String userName;

    private double salary;


    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
