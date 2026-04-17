/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3;

/**
 *
 * @author Bardes
 */
public class Room {
    private int Roomnumber;
    private boolean isavailable;
    private double roomprice;
    private Roomtype roomtype;
    
    public Room(int Roomnumber, double roomprice, Roomtype roomtype){
        this.Roomnumber=Roomnumber;
        this.isavailable=true;
        this.roomprice=roomprice;
        this.roomtype=roomtype;
    }
    public void setRoomnumber(int roomnumber){
        this.Roomnumber=roomnumber;
    }
    public int getroomnumber(){
        return Roomnumber;}
        public boolean isavailable(){
        return isavailable;}
    
    public void setroomprice(double price){
        if (price<0){
            System.out.println("price must be greater than 0");
                    return;}
        
            this.roomprice=price;}
    public double getroomprice(){
        return roomprice;}
    public void setroomtype(Roomtype roomtype){
        this.roomtype=roomtype;}
public Roomtype getroomtype(){
return roomtype;}
public void setavilabilty(boolean availability){
this.isavailable=availability;}}
    
    

