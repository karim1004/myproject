/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3;
/**
 *
 * @author Bardes
 */
public class Guest {
    private String username;
    private String password;
    public Guest(String username,String password){
        this.username=username;
    this.password=password;}
    public void setusername(String username){
                if(username==null||username.trim().isEmpty()){
                    System.out.println("username cant be empty");
                return;}
                      this.username=username;
               }
    public String getusername(){
        return username;}
    
          
            public void setpassword(String password){
        this.password=password;}
        
    public String getpassword(){
        return password;}}
        
    
        
    
    
    
