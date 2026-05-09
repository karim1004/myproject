package hotel.rooms;

import hotel.model.RoomAvailability;
import java.util.List;

public class Room {
    private int roomNumber;
    private RoomType roomType;
    private List<Amenity> amenities;
    private RoomAvailability availability;
    private double pricePerNight;

    public Room(int roomNumber, RoomType roomType, List<Amenity> amenities, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.amenities = amenities;
        this.availability = RoomAvailability.AVAILABLE;
        this.pricePerNight = pricePerNight;
    }

    public void occupy()      { this.availability = RoomAvailability.OCCUPIED; }
    public void free()        { this.availability = RoomAvailability.AVAILABLE; }
    public void maintenance() { this.availability = RoomAvailability.UNDER_MAINTENANCE; }

    public int getRoomNumber()                  { return roomNumber; }
    public void setRoomNumber(int n)            { this.roomNumber = n; }
    public RoomType getRoomType()               { return roomType; }
    public void setRoomType(RoomType t)         { this.roomType = t; }
    public List<Amenity> getAmenities()         { return amenities; }
    public void setAmenities(List<Amenity> a)   { this.amenities = a; }
    public RoomAvailability getAvailability()         { return availability; }
    public void setAvailability(RoomAvailability av)  { this.availability = av; }
    public double getPricePerNight()            { return pricePerNight; }
    public void setPricePerNight(double p)      { this.pricePerNight = p; }

    public String getAmenitiesString() {
        if (amenities == null || amenities.isEmpty()) return "None";
        StringBuilder sb = new StringBuilder();
        for (Amenity a : amenities) sb.append(a.getType().name()).append(", ");
        return sb.substring(0, sb.length() - 2);
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (" + roomType + ") - $" + pricePerNight + "/night";
    }
}
