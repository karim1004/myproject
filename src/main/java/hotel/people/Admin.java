/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package hotel.people;

import hotel.model.Gender;
import hotel.model.Role;
import java.time.LocalDate;

public class Admin extends Staff {

    public Admin(String username, String password, LocalDate dob, String address, Gender gender, int workingHours) {
        super(username, password, dob, address, gender, Role.ADMIN, workingHours);
    }

    public void addRoom() {
        System.out.println("Room added.");
    }
    public void updateRoom() {
        System.out.println("Room updated.");
    }
    public void deleteRoom() {
        System.out.println("Room deleted.");
    }

    public void addAmenity() {
        System.out.println("Amenity added.");
    }
    public void updateAmenity() {
        System.out.println("Amenity updated.");
    }
    public void deleteAmenity() {
        System.out.println("Amenity deleted.");
    }
}


