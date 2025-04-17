/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.bookstore.factory;

import com.myproject.bookstore.entity.Customers;

/**
 *
 * @author pc
 */
public interface CustomerFactory {
    Customers createCustomer(Integer customerid, String name, String address, String city, String zipCode, String country);
}
