/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.bookstore;

import com.myproject.bookstore.entity.Books;
import com.myproject.bookstore.entity.CartItem;
import com.myproject.bookstore.entity.Orders;
import com.myproject.bookstore.factory.BookDAO;
import com.myproject.bookstore.factory.BookFactory;
import com.myproject.bookstore.factory.DatabaseBookDAO;
import com.myproject.bookstore.factory.DatabaseOrderDAO;
import com.myproject.bookstore.factory.DefaultBookFactory;
import com.myproject.bookstore.factory.OrderDAO;
import com.myproject.bookstore.singleton.CartManager;
import java.util.Map;

/**
 *
 * @author Tuan
 */
public class OrderTest {
    public static void main(String[] args) {
        BookFactory factory = new DefaultBookFactory();
        BookDAO bookDAO = new DatabaseBookDAO(factory);
        OrderDAO orderDAO = new DatabaseOrderDAO();
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
        
        //Thêm chi tiết cho đơn hàng
        Orders orderInfor = new Orders();
        
        String shipAddress = "Quan 12";
        String shipCity = "Ha Long";
        String shipCountry = "USA";
        String shipName = "123-TrauCang";
        String shipZipCode = "1233-1234-123";
        
        orderInfor.setShipAddress(shipAddress);
        orderInfor.setShipCity(shipCity);
        orderInfor.setShipCountry(shipCountry);
        orderInfor.setShipName(shipName);
        orderInfor.setShipZipCode(shipZipCode);
        
        boolean placeOrder = orderDAO.placeOrderFromCart(cartManager, 1, orderInfor);
        
        if(placeOrder){
            System.out.println("You has order the order");
        }else{
            System.out.println("Error");
        }
        
        int orderSendToCustomer = 8;
        boolean setDeliveriedOrder = orderDAO.deliveredOrder(orderSendToCustomer);
        if(setDeliveriedOrder){
            System.out.println("Order has ID:"+orderSendToCustomer+" set to delivery");
        }else{
            System.out.println("Order has ID:"+orderSendToCustomer+" fail to set to delivery");
        }
        
        boolean setSuccesOrder = orderDAO.successOrder(orderSendToCustomer);
        if(setSuccesOrder){
            System.out.println("Order has ID:"+orderSendToCustomer+" set to success");
        }else{
            System.out.println("Order has ID:"+orderSendToCustomer+" fail to set to success");
        }
        
        int orderWithDamageItems = 9;
        boolean setCancelOrder = orderDAO.cancelOrder(orderWithDamageItems);
        if(setCancelOrder){
            System.out.println("Order has ID:"+orderWithDamageItems+" set to cancel");
        }else{
            System.out.println("Order has ID:"+orderWithDamageItems+" fail to set to cancel");
        }
    }
}
