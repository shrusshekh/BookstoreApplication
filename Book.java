package coe528.project;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class Book {
    //creating variables 
    public String bookName;
    public double bookPrice;
    
    //setters
    public void setBookName(String bookName)
    {
    this.bookName = bookName;

    }
    public void setBookPrice(double bookPrice)
    {
     this.bookPrice = bookPrice;
    }

    //getters

    public String getBookName()
    {
    return bookName;
    }
    public double getBookPrice()
    {
    return bookPrice;
    }

    public void getBook(String bookName, double bookPrice)
    {

    this.bookName = bookName;
    this.bookPrice = bookPrice;
    }
}