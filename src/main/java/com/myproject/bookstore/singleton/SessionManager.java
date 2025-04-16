/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.bookstore.singleton;

/**
 *
 * @author pc
 */

//Singleton
public class SessionManager {
    // Dùng volatile để đảm bảo visibility trong môi trường đa luồng
    private static volatile SessionManager instance;
    private String userId;
    private String role;
    private long loginTimestamp;

    // Constructor private
    private SessionManager() {}

    // Double-Checked Locking Singleton
    public static SessionManager getInstance() {
        if (instance == null) {
            synchronized (SessionManager.class) {
                if (instance == null) {
                    instance = new SessionManager();
                }
            }
        }
        return instance;
    }

    // Đăng nhập
    public void login(String userId, String role) {
        this.userId = userId;
        this.role = role;
        this.loginTimestamp = System.currentTimeMillis();
        System.out.println("User " + userId + " is logged in with role " + role);
    }

    // Đăng xuất
    public void logout() {
        System.out.println("User " + userId + " is logged out.");
        this.userId = null;
        this.role = null;
        this.loginTimestamp = 0;
    }

    // Kiểm tra đã đăng nhập
    public boolean isLoggedIn() {
        return userId != null;
    }

    // Kiểm tra phiên có hết hạn không (VD: sau 30 phút)
    public boolean isSessionExpired() {
        long currentTime = System.currentTimeMillis();
        return isLoggedIn() && (currentTime - loginTimestamp > 30 * 60 * 1000);
    }

    // Getter
    public String getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }

    public long getLoginTimestamp() {
        return loginTimestamp;
    }
}

