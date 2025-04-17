/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.bookstore.factory;

import com.myproject.bookstore.daoImpl.MockCustomerDAO;
import com.myproject.bookstore.daoImpl.DatabaseCustomerDAO;
import com.myproject.bookstore.dao.CustomerDAO;

/**
 *
 * @author pc
 */
public class CustomerDAOFactory {
    public static CustomerDAO createDAO(String type, CustomerFactory factory) {
        return switch (type.toLowerCase()) {
            case "database" -> new DatabaseCustomerDAO(factory);
            case "mock" -> new MockCustomerDAO(factory);
            default -> new DatabaseCustomerDAO(factory);
        };
    }
}

