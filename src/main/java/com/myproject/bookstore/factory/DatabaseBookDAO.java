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
public class DatabaseBookDAO implements BookDAO{
    // ✅ Cập nhật sách
    @Override
    public boolean updateBook(Books book) {
        String sql = "UPDATE books SET book_title = ?, book_author = ?, book_image = ?, book_descr = ?, book_price = ? WHERE book_isbn = ?";
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getBookTitle());
            stmt.setString(2, book.getBookAuthor());
            stmt.setString(3, book.getBookImage());
            stmt.setString(4, book.getBookDescr());
            stmt.setDouble(5, book.getBookPrice());
            stmt.setString(6, book.getBookIsbn());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ❌ Xoá sách theo ISBN
    @Override
    public boolean deleteBook(String isbn) {
        String sql = "DELETE FROM books WHERE book_isbn = ?";
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, isbn);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // 🔍 Tìm kiếm theo tiêu đề
    @Override
    public List<Books> findBooksByTitle(String title) {
        List<Books> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE LOWER(book_title) LIKE ?";
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + title.toLowerCase() + "%");
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    // 🔍 Tìm kiếm theo tác giả
    @Override
    public List<Books> findBooksByAuthor(String author) {
        List<Books> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE LOWER(book_author) LIKE ?";
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + author.toLowerCase() + "%");
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
}
