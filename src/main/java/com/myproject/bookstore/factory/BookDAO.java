/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.bookstore.factory;

import com.myproject.bookstore.entity.Books;
import java.util.List;

/**
 *
 * @author pc
 */
public interface BookDAO {
    public abstract List<Books> findBooksByTitle(String title);
    public abstract List<Books> findBooksByAuthor(String author);
    public abstract boolean updateBook(Books book);
    public abstract boolean deleteBook(String isbn);
}
