/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;

/**
 *
 * @author sshekhad
 */

import java.util.ArrayList;
import java.util.List;
//imports for writing into books.txt and customers.txt
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BookStore {
    public List<Book> books;
    private List<Customer> customers;
    public String nameOfBookStore; //title for scene

    public BookStore(String nameOfBookStore) {
        this.nameOfBookStore = nameOfBookStore;
        this.books = new ArrayList<>();
        this.customers = new ArrayList<>();
    }

    public void addBook(Book book) { //add book method
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    //loading books into books.txt file
    public void loadBooks() {
        List<Book> loadedBooks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("books.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(" / "); //seperating name and price
                    if (parts.length == 2) {
                        String bookName = parts[0].trim();//the first element is the book name
                        double bookPrice = Double.parseDouble(parts[1].trim());//the second element is the book price
                        loadedBooks.add(new Book());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading books.txt: " + e.getMessage());
        }
        this.books = loadedBooks; //setting the books from the loaded books
    }

    // saving into txt
    public void saveBooks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt"))) {
            for (Book book : books) {
                writer.write(book.getBookName() + " / " + book.getBookPrice());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to books.txt: " + e.getMessage());
        }
    }

    public List<Book> listBooks() { //return list of books
        return books;
    }

    public List<Customer> listCustomers() { //return list of customers
        return customers;
    }
}
