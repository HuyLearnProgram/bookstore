/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.bookstore.factory;

import com.myproject.bookstore.singleton.DatabaseConnection;
import com.myproject.bookstore.entity.Books;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author pc
 */


public abstract class BookFactory {
    public abstract List<Books> createBooks();
}

