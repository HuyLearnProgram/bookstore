/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.myproject.bookstore.dao;

import com.myproject.bookstore.entity.Customers;
import java.util.List;

/**
 *
 * @author Tuan
 */
public interface CustomerDAO {
    public abstract boolean createUser(Customers customersInfor);
    public abstract List<Customers> findAllCustomer();
}
