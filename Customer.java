/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;

/**
 *
 * @author sshekhad
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    // Creating all the variables
    private double points;
    private String status;
    public List<Book> shoppingCart = new ArrayList<>();
    public List<Book> purchased = new ArrayList<>();
    protected State customerState; // assuming CustomerState is an interface or class
    private boolean redeemPoints; // boolean for redeeming points 
    private double lastTransactionCost;  

    // Creating the customer constructor
  public Customer(String username, String password) {
        super(username, password);
        this.points = 0;
        this.status = "Silver";
        this.customerState = new SilverState(); 
    }
  private void updateStatus(double points) 
  {
        if (points >= 1000) {
            status = "Gold";
            customerState = new GoldState(); 
        } else {
            status = "Silver";
            customerState = new SilverState();
        }
    }
  public double getPoints() 
  {
    return points;
  }

  public String getStatus() 
  {
    return status;
  }


  public void setStatus(State newState) {
    this.customerState = newState;
    this.status = newState.getStatus();
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    public void setPoints(int points) {
        this.points = points;
    }


    public void addToCart(Book book) {
        shoppingCart.add(book);
    }

    public void Purchase() {
        double totalCost = 0;
        for (Book book : shoppingCart) { //going through the shopping cart and adding the cost of each book
            totalCost += book.getBookPrice();
        }

        if (redeemPoints) {
            double pointsToCash = points / 100.0;//converting the points to the discount
            double newCost = totalCost - pointsToCash;
            if (newCost < 0) { //if the points to cash is greater than the total cost, then the total cost is 0
                newCost = 0;
            }
            points = 0; // Redeem all current points.

            points += newCost * 10; // 10 points / $1
            // Use the net cost as the total cost for this transaction.
            totalCost = newCost;
        } else {
            // only buying, no redeeming
            points += totalCost * 10;
        }

        updateStatus(points);
        purchased.addAll(shoppingCart);
        shoppingCart.clear();

        // storing the old cost 
        lastTransactionCost = totalCost;
    }

    // getter
    public double getLastTransactionCost() {
        return lastTransactionCost;
    }

}
