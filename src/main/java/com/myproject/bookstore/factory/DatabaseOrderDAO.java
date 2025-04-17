/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.bookstore.factory;

import com.myproject.bookstore.entity.Books;
import com.myproject.bookstore.entity.CartItem;
import com.myproject.bookstore.entity.Customers;
import com.myproject.bookstore.entity.Orders;
import com.myproject.bookstore.singleton.CartManager;
import com.myproject.bookstore.singleton.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Tuan
 */
public class DatabaseOrderDAO implements OrderDAO {
    @Override
    public List<Orders> findOrdersByUser(int userId) {
        List<Orders> orders = new ArrayList<>();
        String sql = """
                     SELECT * FROM orders WHERE customerid = ?
                     """;
        try (Connection conn = DatabaseConnection.getInstance(); PreparedStatement orderStmt = conn.prepareStatement(sql)) {
            orderStmt.setInt(1, userId);
            ResultSet rs = orderStmt.executeQuery();
            while (rs.next()) {
                Orders order = new Orders(
                        rs.getInt("orderid"),
                        rs.getBigDecimal("amount"),
                        rs.getDate("date"),
                        rs.getString("ship_name"),
                        rs.getString("ship_address"),
                        rs.getString("ship_city"),
                        rs.getString("ship_zip_code"),
                        rs.getString("ship_country")
                );
                order.setStatus(rs.getString("status"));
                Customers customer = new Customers();
                customer.setCustomerid(userId);
                order.setCustomerid(customer);

                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public List<Books> findBooksByOrder(int orderId) {
        List<Books> books = new ArrayList<>();
        List<String> booksIsbnList = new ArrayList<>();

        String sqlGetIsbn = "SELECT book_isbn FROM order_items WHERE orderid = ?";

        try (Connection conn = DatabaseConnection.getInstance(); PreparedStatement isbnStmt = conn.prepareStatement(sqlGetIsbn)) {

            isbnStmt.setInt(1, orderId);
            ResultSet rs = isbnStmt.executeQuery();

            while (rs.next()) {
                booksIsbnList.add(rs.getString("book_isbn"));
            }

            if (booksIsbnList.isEmpty()) {
                return books;
            }

            // Tạo chuỗi dấu ? cho câu truy vấn IN (?,?,...)
            String placeholders = String.join(",", booksIsbnList.stream().map(s -> "?").toArray(String[]::new));
            String sqlGetBooks = "SELECT * FROM books WHERE book_isbn IN (" + placeholders + ")";

            try (PreparedStatement bookStmt = conn.prepareStatement(sqlGetBooks)) {
                for (int i = 0; i < booksIsbnList.size(); i++) {
                    bookStmt.setString(i + 1, booksIsbnList.get(i));
                }

                ResultSet bookRs = bookStmt.executeQuery();
                while (bookRs.next()) {
                    Books book = new Books(
                            bookRs.getString("book_isbn"),
                            bookRs.getString("book_title"),
                            bookRs.getString("book_author"),
                            bookRs.getString("book_image"),
                            bookRs.getString("book_descr"),
                            bookRs.getDouble("book_price")
                    );
                    books.add(book);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }
    
    @Override
    public Map<Books, Integer> findBooksWithQuantityByOrder(int orderId) {
    Map<Books, Integer> booksWithQuantity = new LinkedHashMap<>();
    String sql = """
                 SELECT b.book_isbn, b.book_title, b.book_author, b.book_image, 
                        b.book_descr, b.book_price, oi.quantity
                 FROM order_items oi
                 JOIN books b ON oi.book_isbn = b.book_isbn
                 WHERE oi.orderid = ?
                 """;

    try (Connection conn = DatabaseConnection.getInstance();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, orderId);
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
            int quantity = rs.getInt("quantity");
            booksWithQuantity.put(book, quantity);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return booksWithQuantity;
}

    @Override
    public boolean placeOrderFromCart(CartManager cartManager, int userId, Orders orderInfor) {
        cartManager = CartManager.getInstance();

        String insertOrderItemSql = "INSERT INTO order_items (orderid, book_isbn, item_price, quantity) VALUES (?, ?, ?, ?)";
        String insertOrderSql = "INSERT INTO orders (customerid, amount, date, ship_name, ship_address, ship_city, ship_zip_code, ship_country, status) VALUES (?, ?, NOW(), ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance(); PreparedStatement orderStmt = conn.prepareStatement(insertOrderSql, Statement.RETURN_GENERATED_KEYS); PreparedStatement itemStmt = conn.prepareStatement(insertOrderItemSql)) {
            orderStmt.setInt(1, userId);
            orderStmt.setDouble(2, cartManager.getTotalPrice());
            orderStmt.setString(3, orderInfor.getShipName()); 
            orderStmt.setString(4, orderInfor.getShipAddress());
            orderStmt.setString(5, orderInfor.getShipCity());
            orderStmt.setString(6, orderInfor.getShipZipCode());
            orderStmt.setString(7, orderInfor.getShipCountry());
            orderStmt.setString(8, "pending");
            int rowsAffected = orderStmt.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("Failed to insert order.");
                return false;
            }

            // Get generated orderid
            int orderId;
            try (ResultSet generatedKeys = orderStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    orderId = generatedKeys.getInt(1);
                } else {
                    System.out.println("Failed to retrieve order ID.");
                    return false;
                }
            }

            // Insert items into order_items
            for (Map.Entry<String, CartItem> entry : cartManager.getCartItems().entrySet()) {
                CartItem item = entry.getValue();
                Books book = item.getBook();

                itemStmt.setInt(1, orderId);
                itemStmt.setString(2, book.getBookIsbn());
                itemStmt.setDouble(3, book.getBookPrice()); // assuming Books has getPrice()
                itemStmt.setInt(4, item.getQuantity());
                itemStmt.addBatch();
            }
            itemStmt.executeBatch();
            System.out.println("Order placed successfully with order ID: " + orderId);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean handleOrder(int orderId, String status) {
        String sql = "UPDATE orders SET status = ? WHERE orderid = ?";
//        if(status)
        try (Connection conn = DatabaseConnection.getInstance(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, orderId);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean successOrder(int orderId) {
        return handleOrder(orderId, "success");
    }

    @Override
    public boolean cancelOrder(int orderId) {
        return handleOrder(orderId, "canceled");
    }

    @Override
    public boolean deliveredOrder(int orderId) {
        return handleOrder(orderId, "delivered");
    }

}
