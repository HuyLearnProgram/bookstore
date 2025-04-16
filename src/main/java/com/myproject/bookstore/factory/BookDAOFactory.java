/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.bookstore.factory;

/**
 *
 * @author pc
 */
public class BookDAOFactory {
    public static BookDAO createDAO(String type, BookFactory factory) {
        switch (type.toLowerCase()) {
            case "database" -> {
                return new DatabaseBookDAO(factory);
            }
            case "mock" -> { 
                return new MockBookDAO();
            }

            default -> {
                return new DatabaseBookDAO(factory);
            }
        }
    }
}