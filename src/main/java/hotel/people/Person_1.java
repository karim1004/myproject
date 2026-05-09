/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotel.people;
import hotel.model.Gender;
import java.time.LocalDate;

public abstract class Person {
    protected String username;
    protected String password;
    protected LocalDate dateOfBirth;


    public Person (String username, String password, LocalDate dateOfBirth){
        this.username=username;
        this.password=password;
        this.dateOfBirth=dateOfBirth;

    }

    public abstract void login (String username, String password);

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty())
            throw new IllegalArgumentException("Username cannot be empty");
        this.username = username;
    }

    public void setPassword(String password) {
        if (password == null || password.length() < 4)
            throw new IllegalArgumentException("Password must be at least 4 characters");
        this.password = password;
    }


    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}