/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3;

import java.time.LocalDate;
/**
 *
 * @author Bardes
 */
public class Reservation {
   private Guest guest;
    private Room room;
    private LocalDate checkindate;
    private LocalDate checkoutdate;
    private Reservationstatus reservationstatus;
    public Reservation(Guest guest,Room room,LocalDate checkoutdate,LocalDate checkindate){
        this.guest=guest;
        this.room=room;
        this.checkindate=checkindate;
        this.checkoutdate=checkoutdate;
        this.reservationstatus=Reservationstatus.pending;}
    public Guest getguest(){
        return guest;
    }
        public void setguest(Guest guest){
            this.guest=guest;
        }
        public Room getroom(){
            return room;}
             public void setroom(Room room){
            this.room=room;
        }
              public LocalDate getcheckindate(){
            return checkindate;}
             public void setLocaldate(LocalDate checkindate){
            this.checkindate=checkindate;
        }
            public void setdates(LocalDate checkindate, LocalDate checkoutdate) {
    // If the dates are invalid, we "throw" an error
    if (checkoutdate.isBefore(checkindate)) {
        throw new IllegalArgumentException("Check-out date (" + checkoutdate + ") cannot be before check-in date (" + checkindate + ").");
    }

    this.checkindate = checkindate;
    this.checkoutdate = checkoutdate;

        }
             public Reservationstatus getstatus(){
                 return reservationstatus;}
             public void setstatus(Reservationstatus status){
                 this.reservationstatus=status;}}
    
        
    
   
