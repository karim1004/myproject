/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotel.people;

import hotel.model.Gender;
import hotel.model.Role;
import java.time.LocalDate;

public class Receptionist extends Staff {

    public Receptionist(String username, String password, LocalDate dob, String address, Gender gender, int workingHours) {
        super(username, password, dob, address, gender, Role.RECEPTIONIST, workingHours);
    }

    public void checkIn() {
        System.out.println("Guest checked in.");
    }
    public void checkOut() {
        System.out.println("Guest checked out.");
    }
}
