/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3;
import java.time.LocalDate;
/**
 
 * @author Bardes
 */
public class Invoice {
    private double Totalamount;
    private Paymentmethod paymentmethod;
    private LocalDate  paymentdate;
    

public Invoice(double Totalamount,Paymentmethod paymentmethod,LocalDate paymentdate){
    this.Totalamount=Totalamount;
    this.paymentmethod=paymentmethod;
    this.paymentdate=paymentdate;}
public void setTotalamount(double Totalamount){
    if (Totalamount<0){
        System.out.println("total amount must be greater than zero");
        return ;}
    this.Totalamount=Totalamount;}
public double getTotalamount()   {
    return Totalamount;
}
    public void setpaymentmethod(Paymentmethod paymentmethod){
        this.paymentmethod=paymentmethod;}
    public Paymentmethod getpaymentmethod(){
        return paymentmethod;}
    public void setpaymentdate(LocalDate paymentdate){
        this.paymentdate=paymentdate;}
    public LocalDate getpaymentdate(){
        return paymentdate;}}
    
        
