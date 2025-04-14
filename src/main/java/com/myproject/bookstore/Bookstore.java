/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.myproject.bookstore;

import com.myproject.bookstore.factory.BookFactory;
import com.myproject.bookstore.entity.Books;
import com.myproject.bookstore.entity.CartItem;
import com.myproject.bookstore.factory.BookDAO;
import com.myproject.bookstore.factory.DatabaseBookDAO;
import com.myproject.bookstore.factory.DatabaseBookFactory;
import com.myproject.bookstore.singleton.CartManager;
import java.util.List;
import java.util.Map;

/**
 *
 * @author pc
 */
public class Bookstore {

    public static void main(String[] args) {
//        BookFactory factory = new DatabaseBookFactory(); // Hoặc new JsonBookFactory();
        BookDAO bookDAO = new DatabaseBookDAO();
//        List<Books> books = bookDAO.findBooksByTitle("Doing Good By Doing Good");
//        Books book = books.get(0);
//        book.setBookPrice(40.5);
//        boolean isupdate = bookDAO.updateBook(book);
//        System.out.println(book);
//        System.out.println(isupdate);

        CartManager cartManager = CartManager.getInstance();

        // Tạo sách
        Books book1 = bookDAO.findBooksByTitle("Doing Good By Doing Good").get(0);
        Books book2 = bookDAO.findBooksByAuthor("Thomas H. Cormen").get(0);
        
        // Thêm sách vào giỏ
        cartManager.addToCart(new CartItem(book1, 2)); // 2 quyển Java
        cartManager.addToCart(new CartItem(book2, 1)); // 1 quyển Python

        // Cập nhật số lượng
        cartManager.updateQuantity(book1.getBookIsbn(), 3); // update Java thành 3 quyển

        // In giỏ hàng
        System.out.println("Gio hang hien tai:");
        for (Map.Entry<String, CartItem> entry : cartManager.getCartItems().entrySet()) {
            CartItem item = entry.getValue();
            Books book = item.getBook();
            System.out.printf("ISBN: %s | Book Title: %s | Quantity: %d | Total Price: %.2f\n",
                    book.getBookIsbn(),
                    book.getBookTitle(),
                    item.getQuantity(),
                    item.getTotalPrice());
        }

        // In tổng số lượng và tổng giá trị
        System.out.println("Total Quantity: " + cartManager.getTotalQuantity());
        System.out.println("Total: $" + cartManager.getTotalPrice());
    }
}
