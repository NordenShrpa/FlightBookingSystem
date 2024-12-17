package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.time.LocalDate;

public class AddFlight implements Command {
    private final String flightNumber;
    private final String origin;
    private final String destination;
    private final LocalDate departureDate;
    private final int capacity;
    private final double price;

    public AddFlight(String flightNumber, String origin, String destination, LocalDate departureDate, int capacity, double price) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.capacity = capacity;
        this.price = price;
    }

    @Override
    public void execute(FlightBookingSystem system) throws FlightBookingSystemException {
        // Validate input
        if (flightNumber.isEmpty() || origin.isEmpty() || destination.isEmpty() || departureDate == null) {
            throw new FlightBookingSystemException("All fields are required for adding a flight.");
        }

        // Check if flight number already exists
        if (system.getFlightByNumber(flightNumber) != null) {
            throw new FlightBookingSystemException("Flight number already exists.");
        }

        // Create and add the new flight
        Flight newFlight = new Flight(flightNumber, origin, destination, departureDate, capacity, price);
        system.addFlight(newFlight);
        System.out.println("Flight added successfully: " + newFlight);
    }

    @Override
    public String toString() {
        return "AddFlight: " + flightNumber + " from " + origin + " to " + destination + " on " + departureDate;
    }
}
