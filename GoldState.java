/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;

/**
 *
 * @author sshekhad
 */

public class GoldState implements State {

    @Override
    public int earnPoints(double price) {
        // Gold customers might earn more points per dollar, e.g., 15 points per dollar.
        return (int) (price * 15);
    }

    @Override
    public double redeemPoints(double price) {
        // Gold state could have a different redemption rule.
        return price;
    }

    @Override
    public void updateStatus(Customer customer) {
        // If points drop below 1000, switch back to SilverState.
        if (customer.getPoints() < 1000) {
            customer.setStatus(new SilverState());
        }
    }

    @Override
    public String getStatus() {
        return "Gold";
    }
}