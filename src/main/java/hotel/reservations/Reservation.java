package hotel.reservations;

import hotel.people.Guest;
import hotel.rooms.Room;
import hotel.model.ReservationStatus;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reservation {
    private int reservationId;
    private Guest guest;
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private ReservationStatus status;

    public Reservation(int reservationId, Guest guest, Room room,
                       LocalDate checkInDate, LocalDate checkOutDate) {
        this.reservationId = reservationId;
        this.guest = guest;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = ReservationStatus.CONFIRMED;
    }

    public void cancel() {
        this.status = ReservationStatus.CANCELLED;
        room.free();
    }

    public void complete() {
        this.status = ReservationStatus.COMPLETED;
        room.free();
    }

    public double getTotalCost() {
        long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        if (nights <= 0) nights = 1;
        return nights * room.getPricePerNight();
    }

    public int getReservationId()           { return reservationId; }
    public Guest getGuest()                 { return guest; }
    public Room getRoom()                   { return room; }
    public LocalDate getCheckInDate()       { return checkInDate; }
    public LocalDate getCheckOutDate()      { return checkOutDate; }
    public ReservationStatus getStatus()    { return status; }
    public void setStatus(ReservationStatus s) { this.status = s; }
    public void setCheckInDate(LocalDate d)    { this.checkInDate = d; }
    public void setCheckOutDate(LocalDate d)   { this.checkOutDate = d; }
}
