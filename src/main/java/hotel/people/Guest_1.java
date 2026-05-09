package hotel.people;

import hotel.model.Gender;
import hotel.reservations.Reservation;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Guest extends Person {
    private double balance;
    private String roomPreferences;
    private Gender gender;
    private String address;
    private List<Reservation> reservations = new ArrayList<>();

    public Guest(String username, String password, LocalDate dob, double balance,
                 String address, Gender gender, String roomPreferences) {
        super(username, password, dob);
        this.balance = balance;
        this.address = address;
        this.gender = gender;
        this.roomPreferences = roomPreferences;
    }

    public void login(String username, String password) {
        if (this.username.equals(username) && this.password.equals(password))
            System.out.println("Guest login successful!");
        else
            System.out.println("Invalid guest credentials.");
    }

    public void addReservation(Reservation r) { reservations.add(r); }
    public void removeReservation(Reservation r) { reservations.remove(r); }
    public List<Reservation> getReservations() { return reservations; }

    public double getBalance()                  { return balance; }
    public void setBalance(double balance)      { this.balance = balance; }
    public String getRoomPreferences()          { return roomPreferences; }
    public void setRoomPreferences(String rp)   { this.roomPreferences = rp; }
    public Gender getGender()                   { return gender; }
    public void setGender(Gender g)             { this.gender = g; }
    public String getAddress()                  { return address; }
    public void setAddress(String a)            { this.address = a; }
}
