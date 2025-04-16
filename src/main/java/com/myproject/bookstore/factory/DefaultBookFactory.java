/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.bookstore.factory;

import com.myproject.bookstore.entity.Books;

/**
 *
 * @author pc
 */
public class DefaultBookFactory implements BookFactory {

    @Override
    public Books createBook(String isbn, String title, String author, String image, String descr, double price) {
        return new Books(isbn, title, author, image, descr, price);
    }
}
