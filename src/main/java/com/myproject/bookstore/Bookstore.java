/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.myproject.bookstore;

import com.myproject.bookstore.factory.BookFactory;
import com.myproject.bookstore.entity.Books;
import com.myproject.bookstore.entity.CartItem;
import com.myproject.bookstore.factory.BookDAO;
import com.myproject.bookstore.factory.DatabaseBookDAO;
import com.myproject.bookstore.factory.DefaultBookFactory;
import com.myproject.bookstore.factory.BookDAOFactory;
import com.myproject.bookstore.factory.MockBookDAO;
import com.myproject.bookstore.singleton.CartManager;
import com.myproject.bookstore.singleton.SessionManager;
import java.util.List;
import java.util.Map;

/**
 *
 * @author pc
 */
public class Bookstore {

    public static void main(String[] args) throws InterruptedException {

        SessionManager session = SessionManager.getInstance();

        // Đăng nhập
        session.login("huy123", "admin");

        // Kiểm tra trạng thái đăng nhập
        if (session.isLoggedIn()) {
            System.out.println("User is logged in.");
            System.out.println("User ID: " + session.getUserId());
            System.out.println("Role: " + session.getRole());
            System.out.println("Login Time: " + new java.util.Date(session.getLoginTimestamp()));
        }

        // Mô phỏng thời gian trôi qua (31 phút)
        Thread.sleep(1000); // demo nhanh, có thể bỏ hoặc sửa thành 31 phút thật

        // Kiểm tra phiên có hết hạn không
        if (session.isSessionExpired()) {
            System.out.println("Session expired!");
        } else {
            System.out.println("Session is still active.");
        }

        // Đăng xuất
        session.logout();

        // Kiểm tra lại
        System.out.println("Is logged in? " + session.isLoggedIn());
    
    }
}
