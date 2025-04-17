/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.bookstore.daoImpl;

import com.myproject.bookstore.dao.OrderDAO;
import com.myproject.bookstore.entity.Books;
import com.myproject.bookstore.entity.CartItem;
import com.myproject.bookstore.entity.Customers;
import com.myproject.bookstore.entity.OrderItems;
import com.myproject.bookstore.entity.OrderItemsPK;
import com.myproject.bookstore.entity.Orders;
import com.myproject.bookstore.factory.OrderFactory;
import com.myproject.bookstore.singleton.CartManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author pc
 */
public class MockOrderDAO implements OrderDAO {
    private final List<Orders> mockOrders = new ArrayList<>();
    private final Map<Integer, List<OrderItems>> orderItemsMap = new HashMap<>();
    private final OrderFactory orderFactory;

    public MockOrderDAO(OrderFactory orderFactory) {
        this.orderFactory = orderFactory;

        // Khởi tạo dữ liệu mẫu
        Orders order = orderFactory.createOrder(
                1,
                new BigDecimal("100.00"),
                new Date(System.currentTimeMillis()),
                "John Doe",
                "123 Main St",
                "New York",
                "10001",
                "USA"
        );
        order.setStatus("pending");

        Customers customer = new Customers();
        customer.setCustomerid(1);
        order.setCustomerid(customer);

        mockOrders.add(order);

        // Tạo Book mẫu
        Books book = new Books("ISBN123", "Sample Book", "Author", "image.jpg", "Description", 50.0);

        // Tạo OrderItems mẫu
        OrderItemsPK pk = new OrderItemsPK(order.getOrderid(), book.getBookIsbn());
        OrderItems orderItem = new OrderItems(pk, book.getBookPrice(), (short) 2);
        orderItem.setBooks(book);
        orderItem.setOrders(order);

        orderItemsMap.put(order.getOrderid(), List.of(orderItem));
    }

    @Override
    public List<Orders> findOrdersByUser(int userId) {
        return mockOrders.stream()
                .filter(order -> order.getCustomerid() != null && order.getCustomerid().getCustomerid() == userId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Books> findBooksByOrder(int orderId) {
        return orderItemsMap.getOrDefault(orderId, List.of()).stream()
                .map(OrderItems::getBooks)
                .collect(Collectors.toList());
    }

    @Override
    public Map<Books, Integer> findBooksWithQuantityByOrder(int orderId) {
        Map<Books, Integer> map = new LinkedHashMap<>();
        for (OrderItems item : orderItemsMap.getOrDefault(orderId, List.of())) {
            map.put(item.getBooks(), (int) item.getQuantity());
        }
        return map;
    }

    @Override
    public boolean placeOrderFromCart(CartManager cartManager, int userId, Orders orderInfor) {
        int newOrderId = mockOrders.size() + 1;

        Orders newOrder = orderFactory.createOrder(
                newOrderId,
                BigDecimal.valueOf(cartManager.getTotalPrice()),
                new Date(System.currentTimeMillis()),
                orderInfor.getShipName(),
                orderInfor.getShipAddress(),
                orderInfor.getShipCity(),
                orderInfor.getShipZipCode(),
                orderInfor.getShipCountry()
        );
        newOrder.setStatus("pending");

        Customers customer = new Customers();
        customer.setCustomerid(userId);
        newOrder.setCustomerid(customer);

        mockOrders.add(newOrder);

        List<OrderItems> items = new ArrayList<>();
        for (CartItem cartItem : cartManager.getCartItems().values()) {
            Books book = cartItem.getBook();
            short quantity = (short) cartItem.getQuantity();
            OrderItemsPK pk = new OrderItemsPK(newOrderId, book.getBookIsbn());
            OrderItems orderItem = new OrderItems(pk, book.getBookPrice(), quantity);
            orderItem.setBooks(book);
            orderItem.setOrders(newOrder);
            items.add(orderItem);
        }
        orderItemsMap.put(newOrderId, items);
        return true;
    }

    @Override
    public boolean handleOrder(int orderId, String status) {
        for (Orders order : mockOrders) {
            if (order.getOrderid() == orderId) {
                order.setStatus(status);
                return true;
            }
        }
        return false;
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
