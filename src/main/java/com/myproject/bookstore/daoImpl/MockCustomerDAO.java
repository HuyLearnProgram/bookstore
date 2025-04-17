/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.bookstore.daoImpl;

import com.myproject.bookstore.dao.CustomerDAO;
import com.myproject.bookstore.entity.Customers;
import com.myproject.bookstore.factory.CustomerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pc
 */
public class MockCustomerDAO implements CustomerDAO {

    
    private List<Customers> customers = new ArrayList<>();
    private final CustomerFactory customerFactory;

    public MockCustomerDAO(CustomerFactory customerFactory) {
        this.customerFactory = customerFactory;   
        
        customers.add(customerFactory.createCustomer(1, "Alice", "123 Street", "Hanoi", "10000", "Vietnam"));
        customers.add(customerFactory.createCustomer(2, "Bob", "456 Street", "Saigon", "70000", "Vietnam"));
    }
    
    @Override
    public boolean createUser(Customers customer) {
        return customers.add(customer);
    }

    @Override
    public List<Customers> findAllCustomer() {
        return new ArrayList<>(customers);
    }
}

