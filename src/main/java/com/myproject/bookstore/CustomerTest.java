/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.bookstore;

import com.myproject.bookstore.entity.Books;
import com.myproject.bookstore.entity.Customers;
import com.myproject.bookstore.entity.Orders;
import com.myproject.bookstore.factory.CustomerDAO;
import com.myproject.bookstore.factory.DatabaseCustomerDAO;
import com.myproject.bookstore.factory.DatabaseOrderDAO;
import com.myproject.bookstore.factory.OrderDAO;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Tuan
 */
public class CustomerTest {

    public static void main(String[] args) {
        CustomerDAO customerDAO = new DatabaseCustomerDAO();
        OrderDAO orderDAO = new DatabaseOrderDAO();

        List<Customers> allCustomer = new ArrayList<>();
        allCustomer = customerDAO.findAllCustomer();
        System.out.println("All customer:");
        System.out.printf("%-5s %-20s %-30s %-15s %-18s %-15s%n",
                "CustomerID", "name", "Address", "City", "Zip code", "Country");

        for (Customers customer : allCustomer) {
            System.out.printf("%-12d %-20s %-30s %-15s %-18s %-15s%n",
                    customer.getCustomerid(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getCity(),
                    customer.getZipCode(),
                    customer.getCountry());
        }
        Customers newCustomer = new Customers();
        newCustomer.setName("Thanh Ha");
        newCustomer.setCity("Sai Gon");
        newCustomer.setCountry("Viet Nam");
        newCustomer.setZipCode("1145-654-98");
        newCustomer.setAddress("Quan 9");

        //Tao 1 customer
        boolean successCreateNewCustomer = customerDAO.createUser(newCustomer);
        if (successCreateNewCustomer) {
            System.out.println("Success create new customer");
        } else {
            System.out.println("Error");
        }
        
        List<Orders> orderByCustomer = new ArrayList<>();
        int userId = 1;
        orderByCustomer = orderDAO.findOrdersByUser(userId);
        System.out.println("All order by customer has ID = " + userId + ":");
        System.out.printf("%-11s %-12s %-15s %-15s %-10s%n",
                "OrderID", "CustomerID", "Date order", "Total amount", "Status");

        for (Orders order : orderByCustomer) {
            System.out.printf("%-11d %-12d %-15s %-15.2f %-10s%n",
                    order.getOrderid(),
                    order.getCustomerid().getCustomerid(),
                    order.getDate().toString(),
                    order.getAmount(),
                    order.getStatus());
        }

        List<Books> bookByOrder = new ArrayList<>();
        int orderId = 6;


        Map<Books, Integer> booksInOrder = orderDAO.findBooksWithQuantityByOrder(orderId);

        System.out.println("Book in order ID = " + orderId + ":");
        System.out.printf("%-20s %-40s %-20s %-10s %-10s%n", "ISBN", "Title", "Author", "Price", "Qty");

        int totalQuantity = 0;

        for (Map.Entry<Books, Integer> entry : booksInOrder.entrySet()) {
            Books book = entry.getKey();
            int quantity = entry.getValue();
            System.out.printf("%-20s %-40s %-20s %-10.2f %-10d%n",
                    book.getBookIsbn(),
                    book.getBookTitle(),
                    book.getBookAuthor(),
                    book.getBookPrice(),
                    quantity);
            totalQuantity += quantity;
        }

        System.out.println("All book in order: " + totalQuantity);

    }
}
