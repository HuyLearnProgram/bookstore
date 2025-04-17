/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.bookstore.factory;

import com.myproject.bookstore.entity.Customers;
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

    @Override
    public boolean createUser(Customers customersInfor) {
        String sql = "INSERT INTO customers(name,address,city,zip_code,country) VALUES (?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getInstance(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customersInfor.getName());
            stmt.setString(2, customersInfor.getAddress());
            stmt.setString(3, customersInfor.getCity());
            stmt.setString(4, customersInfor.getZipCode());
            stmt.setString(5, customersInfor.getCountry());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Customers> findAllCustomer() {
        List<Customers> customersList = new ArrayList<>();
        String sql = "SELECT * FROM customers";

        try (Connection conn = DatabaseConnection.getInstance(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Customers customer = new Customers();
                customer.setCustomerid(rs.getInt("customerid"));
                customer.setName(rs.getString("name"));
                customer.setAddress(rs.getString("address"));
                customer.setCity(rs.getString("city"));
                customer.setZipCode(rs.getString("zip_code"));
                customer.setCountry(rs.getString("country"));

                customersList.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customersList;
    }

}
