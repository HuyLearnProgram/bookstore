/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.bookstore.singleton;

import com.myproject.bookstore.entity.CartItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author pc
 */
// Singleton 
public class CartManager {
    // Singleton instance
    private static CartManager instance;

    // Cart items: key = book ISBN, value = CartItem
    private Map<String, CartItem> cartItems;

    // Private constructor
    private CartManager() {
        cartItems = new HashMap<>();
    }

    // Thread-safe Singleton (Double-checked locking)
    public static CartManager getInstance() {
        if (instance == null) {                 // first check (without lock)
            synchronized (CartManager.class) {
                if (instance == null) {         // second check (with lock)
                    instance = new CartManager();
                }
            }
        }
        return instance;
    }

    // Add book to cart or update quantity
    public void addToCart(CartItem item) {
        String isbn = item.getBook().getBookIsbn();
        if (cartItems.containsKey(isbn)) {
            CartItem existing = cartItems.get(isbn);
            existing.setQuantity(existing.getQuantity() + item.getQuantity());
        } else {
            cartItems.put(isbn, item);
        }
    }

    // Update quantity
    public void updateQuantity(String isbn, int newQuantity) {
        if (cartItems.containsKey(isbn)) {
            if (newQuantity <= 0) {
                cartItems.remove(isbn);
            } else {
                cartItems.get(isbn).setQuantity(newQuantity);
            }
        }
    }

    // Remove item
    public void removeFromCart(String isbn) {
        cartItems.remove(isbn);
    }

    // Clear entire cart
    public void clearCart() {
        cartItems.clear();
    }

    // Get cart items
    public Map<String, CartItem> getCartItems() {
        return new HashMap<>(cartItems); // Trả bản sao để tránh bị chỉnh sửa bên ngoài
    }

    // Get total price of cart
    public double getTotalPrice() {
        return cartItems.values().stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
    }

    // Get total quantity of all items
    public int getTotalQuantity() {
        return cartItems.values().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
    public void printCartSummary() {
        System.out.println("           CART SUMMARY         ");
        System.out.println("===================================");
        System.out.printf("| %-20s | %3s | %10s | %8s |\n", "Title", "Qty", "Price/item", "Subtotal");
        System.out.println("|----------------------|-----|------------|----------|");

        for (CartItem item : cartItems.values()) {
            String title = item.getBook().getBookTitle();
            int qty = item.getQuantity();
            double price = item.getBook().getBookPrice();
            double subtotal = item.getTotalPrice();

            // Chia title thành các dòng nếu dài > 20 ký tự
            List<String> titleLines = splitTitle(title, 20);

            for (int i = 0; i < titleLines.size(); i++) {
                String line = titleLines.get(i);
                if (i == 0) {
                    // Dòng đầu tiên in đủ thông tin
                    System.out.printf("| %-20s | %3d | %10.2f | %8.2f |\n", line, qty, price, subtotal);
                } else {
                    // Các dòng sau chỉ in tiếp phần title
                    System.out.printf("| %-20s |     |            |          |\n", line);
                }
            }
        }

        System.out.println("--------------------------------------------------------");
        System.out.printf("| %-20s | %3d | %10s | %8.2f |\n",
                          "TOTAL",
                          getTotalQuantity(),
                          "",
                          getTotalPrice());
        System.out.println("===================================");
    }

    // Helper để chia title thành dòng dài 20 ký tự
    private List<String> splitTitle(String title, int maxLength) {
        List<String> lines = new ArrayList<>();
        for (int i = 0; i < title.length(); i += maxLength) {
            lines.add(title.substring(i, Math.min(i + maxLength, title.length())));
        }
        return lines;
    }
}
