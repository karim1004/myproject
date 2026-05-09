/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotel.reservations;

import hotel.model.PaymentMethod;
import hotel.interfaces.Payable;

public class Invoice implements Payable {
    private int invoiceId;
    private Reservation reservation;
    private double amount;
    private PaymentMethod paymentMethod;
    private boolean paid;


    public Invoice(int invoiceId, Reservation reservation, double amount, PaymentMethod paymentMethod) {
        this.invoiceId = invoiceId;
        this.reservation = reservation;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paid = false; // default: not paid yet
    }


    public void processPayment() {
        if (!paid) {
            paid = true;
            System.out.println("Invoice " + invoiceId + " paid using " + paymentMethod);
        }
        else {
            System.out.println("Invoice " + invoiceId + " is already paid.");
        }
    }

    // Getters
    public int getInvoiceId() {
        return invoiceId;
    }
    public Reservation getReservation() {
        return reservation;
    }
    public double getAmount() {
        return amount;
    }
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
    public boolean isPaid() {
        return paid;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}

