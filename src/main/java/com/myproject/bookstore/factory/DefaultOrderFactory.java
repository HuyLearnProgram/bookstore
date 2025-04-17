/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.bookstore.factory;

import com.myproject.bookstore.entity.Orders;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author pc
 */
public class DefaultOrderFactory implements OrderFactory {

    @Override
    public Orders createOrder(Integer orderid, BigDecimal amount, Date date,
                              String shipName, String shipAddress, String shipCity,
                              String shipZipCode, String shipCountry) {
        return new Orders(orderid, amount, date, shipName, shipAddress, shipCity, shipZipCode, shipCountry);
    }
}
