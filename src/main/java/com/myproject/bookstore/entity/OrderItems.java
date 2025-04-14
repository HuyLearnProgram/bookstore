/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.bookstore.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "order_items")
@NamedQueries({
    @NamedQuery(name = "OrderItems.findAll", query = "SELECT o FROM OrderItems o"),
    @NamedQuery(name = "OrderItems.findByOrderid", query = "SELECT o FROM OrderItems o WHERE o.orderItemsPK.orderid = :orderid"),
    @NamedQuery(name = "OrderItems.findByBookIsbn", query = "SELECT o FROM OrderItems o WHERE o.orderItemsPK.bookIsbn = :bookIsbn"),
    @NamedQuery(name = "OrderItems.findByItemPrice", query = "SELECT o FROM OrderItems o WHERE o.itemPrice = :itemPrice"),
    @NamedQuery(name = "OrderItems.findByQuantity", query = "SELECT o FROM OrderItems o WHERE o.quantity = :quantity")})
public class OrderItems implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OrderItemsPK orderItemsPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "item_price")
    private Double itemPrice;
    @Basic(optional = false)
    @Column(name = "quantity")
    private short quantity;
    @JoinColumn(name = "book_isbn", referencedColumnName = "book_isbn", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Books books;
    @JoinColumn(name = "orderid", referencedColumnName = "orderid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Orders orders;

    public OrderItems() {
    }

    public OrderItems(OrderItemsPK orderItemsPK) {
        this.orderItemsPK = orderItemsPK;
    }

    public OrderItems(OrderItemsPK orderItemsPK, Double itemPrice, short quantity) {
        this.orderItemsPK = orderItemsPK;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
    }

    public OrderItems(int orderid, String bookIsbn) {
        this.orderItemsPK = new OrderItemsPK(orderid, bookIsbn);
    }

    public OrderItemsPK getOrderItemsPK() {
        return orderItemsPK;
    }

    public void setOrderItemsPK(OrderItemsPK orderItemsPK) {
        this.orderItemsPK = orderItemsPK;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public short getQuantity() {
        return quantity;
    }

    public void setQuantity(short quantity) {
        this.quantity = quantity;
    }

    public Books getBooks() {
        return books;
    }

    public void setBooks(Books books) {
        this.books = books;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderItemsPK != null ? orderItemsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderItems)) {
            return false;
        }
        OrderItems other = (OrderItems) object;
        if ((this.orderItemsPK == null && other.orderItemsPK != null) || (this.orderItemsPK != null && !this.orderItemsPK.equals(other.orderItemsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.myproject.bookstore.entity.OrderItems[ orderItemsPK=" + orderItemsPK + " ]";
    }
    
}
