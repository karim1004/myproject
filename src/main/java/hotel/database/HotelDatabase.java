package hotel.database;

import hotel.model.AmenityType;
import hotel.model.Gender;
import hotel.model.RoomAvailability;
import hotel.people.Guest;
import hotel.people.Staff;
import hotel.reservations.Invoice;
import hotel.reservations.Reservation;
import hotel.rooms.Amenity;
import hotel.rooms.Room;
import hotel.rooms.RoomType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HotelDatabase {

    private static List<Guest> guests = new ArrayList<>();
    private static List<Room> rooms = new ArrayList<>();
    private static List<Reservation> reservations = new ArrayList<>();
    private static List<Invoice> invoices = new ArrayList<>();
    private static int nextReservationId = 1;
    private static int nextInvoiceId = 1;

    static {
        seedRooms();
        seedGuests();
    }

    private static void seedRooms() {
        rooms.add(new Room(101, new RoomType("Standard"), Arrays.asList(
                new Amenity(AmenityType.WIFI, "High-speed WiFi"),
                new Amenity(AmenityType.TV, "Smart TV"),
                new Amenity(AmenityType.AIR_CONDITIONING, "Central AC")), 80.0));

        rooms.add(new Room(102, new RoomType("Standard"), Arrays.asList(
                new Amenity(AmenityType.WIFI, "High-speed WiFi"),
                new Amenity(AmenityType.TV, "Smart TV")), 80.0));

        rooms.add(new Room(201, new RoomType("Deluxe"), Arrays.asList(
                new Amenity(AmenityType.WIFI, "High-speed WiFi"),
                new Amenity(AmenityType.TV, "Smart TV"),
                new Amenity(AmenityType.MINIBAR, "Stocked Minibar"),
                new Amenity(AmenityType.AIR_CONDITIONING, "Central AC")), 150.0));

        rooms.add(new Room(202, new RoomType("Deluxe"), Arrays.asList(
                new Amenity(AmenityType.WIFI, "High-speed WiFi"),
                new Amenity(AmenityType.MINIBAR, "Stocked Minibar"),
                new Amenity(AmenityType.BREAKFAST, "Daily Breakfast")), 160.0));

        rooms.add(new Room(301, new RoomType("Suite"), Arrays.asList(
                new Amenity(AmenityType.WIFI, "Fiber WiFi"),
                new Amenity(AmenityType.TV, "4K TV"),
                new Amenity(AmenityType.MINIBAR, "Premium Minibar"),
                new Amenity(AmenityType.BREAKFAST, "Daily Breakfast"),
                new Amenity(AmenityType.AIR_CONDITIONING, "Smart Climate Control")), 300.0));

        rooms.add(new Room(302, new RoomType("Suite"), Arrays.asList(
                new Amenity(AmenityType.WIFI, "Fiber WiFi"),
                new Amenity(AmenityType.TV, "4K TV"),
                new Amenity(AmenityType.MINIBAR, "Premium Minibar"),
                new Amenity(AmenityType.BREAKFAST, "Daily Breakfast"),
                new Amenity(AmenityType.DINNER, "Fine Dining Dinner")), 350.0));

        rooms.add(new Room(401, new RoomType("Penthouse"), Arrays.asList(
                new Amenity(AmenityType.WIFI, "Dedicated Fiber"),
                new Amenity(AmenityType.TV, "Cinema 4K TV"),
                new Amenity(AmenityType.MINIBAR, "Premium Minibar"),
                new Amenity(AmenityType.BREAKFAST, "Butler Breakfast"),
                new Amenity(AmenityType.DINNER, "Private Chef Dinner")), 600.0));
    }

    private static void seedGuests() {
        guests.add(new Guest("admin", "admin123", LocalDate.of(1990, 1, 1),
                1000.0, "Cairo, Egypt", Gender.MALE, "Suite"));
        guests.add(new Guest("guest", "guest123", LocalDate.of(1995, 5, 15),
                500.0, "Alexandria, Egypt", Gender.FEMALE, "Deluxe"));
    }

    // ─── CRUD ────────────────────────────────────────────────────────────────────
    public static void addGuest(Guest guest)            { guests.add(guest); }
    public static void addRoom(Room room)               { rooms.add(room); }
    public static void addReservation(Reservation r)   { reservations.add(r); }
    public static void addInvoice(Invoice inv)          { invoices.add(inv); }

    public static List<Guest>       getGuests()        { return guests; }
    public static List<Room>        getRooms()         { return rooms; }
    public static List<Reservation> getReservations()  { return reservations; }
    public static List<Invoice>     getInvoices()      { return invoices; }

    public static int nextReservationId() { return nextReservationId++; }
    public static int nextInvoiceId()     { return nextInvoiceId++; }

    public static Guest findGuest(String username, String password) {
        for (Guest g : guests)
            if (g.getUsername().equals(username) && g.getPassword().equals(password))
                return g;
        return null;
    }

    public static boolean usernameExists(String username) {
        for (Guest g : guests)
            if (g.getUsername().equalsIgnoreCase(username)) return true;
        return false;
    }

    public static List<Room> getAvailableRooms() {
        List<Room> av = new ArrayList<>();
        for (Room r : rooms)
            if (r.getAvailability() == RoomAvailability.AVAILABLE) av.add(r);
        return av;
    }

    public static List<Reservation> getGuestReservations(Guest guest) {
        List<Reservation> res = new ArrayList<>();
        for (Reservation r : reservations)
            if (r.getGuest() == guest) res.add(r);
        return res;
    }

    public static void removeReservation(Reservation r) {
        reservations.remove(r);
    }
}
