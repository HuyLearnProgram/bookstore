/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.bookstore.singleton;

import com.myproject.bookstore.entity.CartItem;
import java.util.HashMap;
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
        synchronized (CartManager.class) {
            if (instance == null) {
                instance = new CartManager();
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
}
