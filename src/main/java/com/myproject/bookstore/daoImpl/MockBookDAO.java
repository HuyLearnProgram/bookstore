/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.bookstore.daoImpl;

import com.myproject.bookstore.dao.BookDAO;
import com.myproject.bookstore.entity.Books;
import com.myproject.bookstore.factory.BookFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author pc
 */
public class MockBookDAO implements BookDAO {

    private List<Books> books = new ArrayList<>();
    private final BookFactory bookFactory;

    // Constructor mẫu khởi tạo dữ liệu
    public MockBookDAO(BookFactory bookFactory) {
        this.bookFactory = bookFactory;
        
        books.add(bookFactory.createBook("111", "Effective Java", "Joshua Bloch", "", "Best practices for Java", 50.0));
        books.add(bookFactory.createBook("222", "Clean Code", "Robert C. Martin", "", "Guide to writing clean code", 45.0));
        books.add(bookFactory.createBook("333", "Design Patterns", "Gang of Four", "", "Classic design patterns book", 60.0));
    }

    @Override
    public List<Books> findBooksByTitle(String title) {
        return books.stream()
                .filter(book -> book.getBookTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Books> findBooksByAuthor(String author) {
        return books.stream()
                .filter(book -> book.getBookAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean updateBook(Books updatedBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getBookIsbn().equals(updatedBook.getBookIsbn())) {
                books.set(i, updatedBook);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteBook(String isbn) {
        return books.removeIf(book -> book.getBookIsbn().equals(isbn));
    }

    // Optional: phương thức để in danh sách sách (debug/test)
    public void printAllBooks() {
        books.forEach(book -> System.out.println(book.getBookTitle() + " by " + book.getBookAuthor()));
    }
}
