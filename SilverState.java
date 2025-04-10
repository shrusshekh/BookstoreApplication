/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;

/**
 *
 * @author sshekhad
 */

public class SilverState implements State {

    @Override
    public int earnPoints(double price) {
        // Silver customers earn 10 points per dollar.
        return (int) (price * 10);
    }

    @Override
    public double redeemPoints(double price) {
        // No discount applied in Silver state.
        return price;
    }

    @Override
    public void updateStatus(Customer customer) {
        // If the customer has 1000 or more points, switch to GoldState.
        if (customer.getPoints() >= 1000) {
            customer.setStatus(new GoldState());
        }
    }

    @Override
    public String getStatus() {
        return "Silver";
    }
}
