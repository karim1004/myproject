/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotel.people;

import hotel.model.Gender;
import hotel.model.Role;
import java.time.LocalDate;

public abstract class Staff extends Person {
    protected Role role;
    protected int workingHours;

    public Staff(String username, String password, LocalDate dob, String address, Gender gender, Role role, int workingHours) {
        super(username, password, dob);
        this.workingHours = workingHours;
    }

    public void login(String username, String password) {
        if (this.username.equals(username) && this.password.equals(password)) {
            System.out.println(role + " login successful!");
        }
        else {
            System.out.println("Invalid staff credentials.");
        }
    }


    public void viewGuests() {
        System.out.println("Viewing guest list...");
    }
    public void viewRooms() {
        System.out.println("Viewing room list...");
    }
    public void viewReservations() {
        System.out.println("Viewing reservations...");
    }


    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public int getWorkingHours() {
        return workingHours;
    }
    public void setWorkingHours(int workingHours) {
        this.workingHours = workingHours;
    }
}

