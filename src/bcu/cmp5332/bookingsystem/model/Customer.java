package bcu.cmp5332.bookingsystem.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int nextId = 1;

    private final int id;
    private String name;
    private String email;
    private final List<Booking> bookings;
    private boolean isDeleted;
    private String phone;

    public Customer(String name, String phone, String email) {
        this.id = nextId++;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.bookings = new ArrayList<>();
        this.isDeleted = false;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Booking> getBookings() {
        return new ArrayList<>(bookings);
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public void removeBooking(Booking booking) {
        bookings.remove(booking);
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public int getNumberOfBookings() {
        return bookings.size();
    }

    public String getDetailsShort() {
        return String.format("%s (%s)", getName(), getEmail());
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, Phone: %s, Email: %s, Bookings: %d", 
                             id, name, phone, email, getNumberOfBookings());
    }

    // For testing purposes
    public static void resetIdCounter() {
        nextId = 1;
    }
}
