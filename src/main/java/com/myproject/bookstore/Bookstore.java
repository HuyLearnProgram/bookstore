/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.myproject.bookstore;

/**
 *
 * @author pc
 */
public class Bookstore {

    public static void main(String[] args) {
        for (Books book : BookFactory.getBooksFromDb()) {
            System.out.println(book);
        }
    }
}
