/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.myproject.bookstore.dao;

import com.myproject.bookstore.entity.Books;
import com.myproject.bookstore.entity.Orders;
import com.myproject.bookstore.singleton.CartManager;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Tuan
 */
public interface OrderDAO {
    public abstract List<Orders> findOrdersByUser(int userId);
    public abstract List<Books> findBooksByOrder(int OrderId);
    public abstract boolean placeOrderFromCart(CartManager cartManager,int userId,Orders orderInfor);
    public abstract boolean handleOrder(int orderId,String status);
    public abstract boolean successOrder(int orderId);
    public abstract boolean cancelOrder(int orderId);
    public abstract boolean deliveredOrder(int orderId);
    public Map<Books, Integer> findBooksWithQuantityByOrder(int orderId);
}
