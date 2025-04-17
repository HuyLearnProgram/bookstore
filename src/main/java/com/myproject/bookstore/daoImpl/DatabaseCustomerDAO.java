/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.bookstore.daoImpl;

import com.myproject.bookstore.dao.CustomerDAO;
import com.myproject.bookstore.entity.Customers;
import com.myproject.bookstore.factory.CustomerFactory;
import com.myproject.bookstore.singleton.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tuan
 */
public class DatabaseCustomerDAO implements CustomerDAO {

    private final CustomerFactory customerFactory;

    public DatabaseCustomerDAO(CustomerFactory customerFactory) {
        this.customerFactory = customerFactory;
    }

    @Override
    public boolean createUser(Customers customer) {
        String sql = "INSERT INTO customers(name, address, city, zip_code, country) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getAddress());
            stmt.setString(3, customer.getCity());
            stmt.setString(4, customer.getZipCode());
            stmt.setString(5, customer.getCountry());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Customers> findAllCustomer() {
        List<Customers> customerList = new ArrayList<>();
        String sql = "SELECT * FROM customers";

        try (Connection conn = DatabaseConnection.getInstance(); 
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Customers customer = customerFactory.createCustomer(
                    rs.getInt("customerid"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("city"),
                    rs.getString("zip_code"),
                    rs.getString("country")
                );
                customerList.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerList;
    }
}
