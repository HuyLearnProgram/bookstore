/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.bookstore.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

/**
 *
 * @author pc
 */
@Embeddable
public class OrderItemsPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "orderid")
    private int orderid;
    @Basic(optional = false)
    @Column(name = "book_isbn")
    private String bookIsbn;

    public OrderItemsPK() {
    }

    public OrderItemsPK(int orderid, String bookIsbn) {
        this.orderid = orderid;
        this.bookIsbn = bookIsbn;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) orderid;
        hash += (bookIsbn != null ? bookIsbn.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderItemsPK)) {
            return false;
        }
        OrderItemsPK other = (OrderItemsPK) object;
        if (this.orderid != other.orderid) {
            return false;
        }
        if ((this.bookIsbn == null && other.bookIsbn != null) || (this.bookIsbn != null && !this.bookIsbn.equals(other.bookIsbn))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.myproject.bookstore.entity.OrderItemsPK[ orderid=" + orderid + ", bookIsbn=" + bookIsbn + " ]";
    }
    
}
