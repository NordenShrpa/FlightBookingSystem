package bcu.cmp5332.bookingsystem.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Flight implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private final String flightNumber;
    private final String origin;
    private final String destination;
    private final LocalDate departureDate;
    private final int capacity;
    private final double basePrice;
    private final List<Customer> passengers;
    private boolean isDeleted;
    private final double price;

    public Flight(String flightNumber, String origin, String destination, LocalDate departureDate, int capacity, double basePrice) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.capacity = capacity;
        this.basePrice = basePrice;
        this.passengers = new ArrayList<>();
        this.isDeleted = false;
        this.price = basePrice;
        System.out.println("Flight created with flight number: " + flightNumber);
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public List<Customer> getPassengers() {
        return new ArrayList<>(passengers);
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isFull() {
        return passengers.size() >= capacity;
    }

    public boolean addPassenger(Customer customer) {
        if (!isFull()) {
            passengers.add(customer);
            return true;
        }
        return false;
    }

    public boolean removePassenger(Customer customer) {
        return passengers.remove(customer);
    }

    public int getAvailableSeats() {
        return capacity - passengers.size();
    }

    public double getPrice() {
        return price;
    }

    public String getDetailsShort() {
        return String.format("Flight %s from %s to %s on %s", 
            getFlightNumber(), getOrigin(), getDestination(), getDepartureDate());
    }

    @Override
    public String toString() {
        return String.format("Flight %s: %s to %s, Departure: %s, Seats: %d, Price: %.2f",
                getFlightNumber(), getOrigin(), getDestination(), getDepartureDate(), getCapacity(), getBasePrice());
    }

    public int getId() {
        return id;
    }
}