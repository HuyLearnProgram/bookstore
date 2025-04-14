/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.bookstore.factory;

import com.myproject.bookstore.entity.Books;
import com.myproject.bookstore.singleton.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pc
 */
public class DatabaseBookFactory extends BookFactory {
    @Override
    public List<Books> createBooks() {
        List<Books> books = new ArrayList<>();

        try {
            Connection conn = DatabaseConnection.getInstance();
            String query = "SELECT book_isbn, book_title, book_author, book_image, book_descr, book_price FROM books";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Books book = new Books(
                    rs.getString("book_isbn"),
                    rs.getString("book_title"),
                    rs.getString("book_author"),
                    rs.getString("book_image"),
                    rs.getString("book_descr"),
                    rs.getDouble("book_price")
                );
                books.add(book);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
        
    }
    
}
