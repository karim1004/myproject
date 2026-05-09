/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotel.rooms;


import hotel.model.AmenityType;

public class Amenity {
    private AmenityType type;
    private String description;

    public Amenity(AmenityType type, String description) {
        this.type = type;
        this.description = description;
    }

    public AmenityType getType() {
        return type;
    }
    public void setType(AmenityType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return type + " - " + description;
    }
}


