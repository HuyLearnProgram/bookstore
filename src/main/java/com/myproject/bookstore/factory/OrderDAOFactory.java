/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.bookstore.factory;

import com.myproject.bookstore.dao.OrderDAO;
import com.myproject.bookstore.daoImpl.DatabaseOrderDAO;
import com.myproject.bookstore.daoImpl.MockOrderDAO;

/**
 *
 * @author pc
 */
public class OrderDAOFactory {
    public static OrderDAO createDAO(String type, OrderFactory factory) {
        switch (type.toLowerCase()) {
            case "database" -> {
                return new DatabaseOrderDAO(factory);
            }
            case "mock" -> {
                return new MockOrderDAO(factory);
            }
            default -> {
                return new DatabaseOrderDAO(factory);
            }
        }
    }
}
