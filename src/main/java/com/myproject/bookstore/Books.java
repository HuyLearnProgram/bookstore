/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.bookstore;

import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "books")
@NamedQueries({
    @NamedQuery(name = "Books.findAll", query = "SELECT b FROM Books b"),
    @NamedQuery(name = "Books.findByBookIsbn", query = "SELECT b FROM Books b WHERE b.bookIsbn = :bookIsbn"),
    @NamedQuery(name = "Books.findByBookTitle", query = "SELECT b FROM Books b WHERE b.bookTitle = :bookTitle"),
    @NamedQuery(name = "Books.findByBookAuthor", query = "SELECT b FROM Books b WHERE b.bookAuthor = :bookAuthor"),
    @NamedQuery(name = "Books.findByBookImage", query = "SELECT b FROM Books b WHERE b.bookImage = :bookImage"),
    @NamedQuery(name = "Books.findByBookPrice", query = "SELECT b FROM Books b WHERE b.bookPrice = :bookPrice")})
public class Books implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "book_isbn")
    private String bookIsbn;
    @Column(name = "book_title")
    private String bookTitle;
    @Column(name = "book_author")
    private String bookAuthor;
    @Column(name = "book_image")
    private String bookImage;
    @Lob
    @Column(name = "book_descr")
    private String bookDescr;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "book_price")
    private Double bookPrice;

    public Books() {
    }

    public Books(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public Books(String bookIsbn, Double bookPrice) {
        this.bookIsbn = bookIsbn;
        this.bookPrice = bookPrice;
    }

    public Books(String bookIsbn, String bookTitle, String bookAuthor, String bookImage, String bookDescr, Double bookPrice) {
        this.bookIsbn = bookIsbn;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookImage = bookImage;
        this.bookDescr = bookDescr;
        this.bookPrice = bookPrice;
    }
    

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public String getBookDescr() {
        return bookDescr;
    }

    public void setBookDescr(String bookDescr) {
        this.bookDescr = bookDescr;
    }

    public Double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(Double bookPrice) {
        this.bookPrice = bookPrice;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookIsbn != null ? bookIsbn.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Books)) {
            return false;
        }
        Books other = (Books) object;
        if ((this.bookIsbn == null && other.bookIsbn != null) || (this.bookIsbn != null && !this.bookIsbn.equals(other.bookIsbn))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Books[ bookIsbn=" + bookIsbn +", bookTitle="+bookTitle+", bookAuthor="+bookAuthor+", bookPrice="+bookPrice+ " ]";
    }
}
