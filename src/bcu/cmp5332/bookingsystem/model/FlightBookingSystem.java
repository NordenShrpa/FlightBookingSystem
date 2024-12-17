package bcu.cmp5332.bookingsystem.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FlightBookingSystem implements Serializable {
    private static final long serialVersionUID = 1L;
    private static FlightBookingSystem instance;

    private List<Customer> customers;
    private List<Flight> flights;
    private List<Booking> bookings = new ArrayList<>();
    private LocalDate systemDate;

    public FlightBookingSystem() {
        customers = new ArrayList<>();
        flights = new ArrayList<>();
        bookings = new ArrayList<>();
        systemDate = LocalDate.now();
        loadDataFromFiles();
    }

    public static FlightBookingSystem getInstance() {
        if (instance == null) {
            instance = new FlightBookingSystem();
        }
        return instance;
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
        saveDataToFiles();
    }

    public void addFlight(Flight flight) {
        flights.add(flight);
        saveDataToFiles();
    }

    public void issueBooking(Booking booking) {
        bookings.add(booking);
        booking.getCustomer().addBooking(booking);
        booking.getFlight().addPassenger(booking.getCustomer());
        saveDataToFiles();
    }

    public void cancelBooking(Booking booking) {
        bookings.remove(booking);
        booking.getCustomer().removeBooking(booking);
        booking.getFlight().removePassenger(booking.getCustomer());
        booking.setCancelled(true);
        saveDataToFiles();
    }

    public List<Customer> getCustomers() {
        return new ArrayList<>(customers);
    }

    public List<Flight> getFlights() {
        return flights.stream()
                .filter(f -> !f.isDeleted() && f.getDepartureDate().isAfter(systemDate))
                .collect(Collectors.toList());
    }

    public List<Booking> getBookings() {
        return new ArrayList<>(bookings);
    }

    public void addBooking(Booking booking) {
        if (!bookings.contains(booking)) {
            bookings.add(booking);
        } else {
            System.out.println("Duplicate booking not added: " + booking);
        }
    }

    public void removeCustomer(Customer customer) {
        customer.setDeleted(true);
        saveDataToFiles();
    }

    public void removeFlight(Flight flight) {
        flight.setDeleted(true);
        saveDataToFiles();
    }

    public double calculateFlightPrice(Flight flight) {
        long daysUntilDeparture = systemDate.until(flight.getDepartureDate()).getDays();
        int availableSeats = flight.getCapacity() - flight.getPassengers().size();
        double basePrice = flight.getBasePrice();
        double priceIncrease = (100 - daysUntilDeparture) / 100.0 * basePrice;
        priceIncrease += (flight.getCapacity() - availableSeats) / (double) flight.getCapacity() * basePrice;
        return basePrice + priceIncrease;
    }

    public void saveDataToFiles() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("flightbooking.dat"))) {
            oos.writeObject(this);
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    private void loadDataFromFiles() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("flightbooking.dat"))) {
            FlightBookingSystem loadedSystem = (FlightBookingSystem) ois.readObject();
            this.customers = loadedSystem.customers;
            this.flights = loadedSystem.flights;
            this.bookings = loadedSystem.bookings;
            this.systemDate = loadedSystem.systemDate;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }

    public Customer getCustomerById(int id) {
        for (Customer customer : customers) {
            if (customer.getId() == id && !customer.isDeleted()) {
                return customer;
            }
        }
        return null;
    }

    public Flight getFlightByNumber(String flightNumber) {
        return flights.stream()
                .filter(flight -> flight.getFlightNumber().equals(flightNumber))
                .findFirst()
                .orElse(null);
    }

    public Flight getFlightByID(int flightId) {
        for (Flight flight : flights) {
            System.out.println("Comparing flight ID: " + flight.getId() + " with " + flightId);
            if (flight.getId() == flightId) {
                return flight;
            }
        }
        return null;
    }

    public void setSystemDate(LocalDate date) {
        this.systemDate = date;
    }

    public LocalDate getSystemDate() {
        return systemDate;
    }

    public Customer getCustomerByID(int id) {
        for (Customer customer : customers) {
            if (customer.getId() == id && !customer.isDeleted()) {
                return customer;
            }
        }
        return null;
    }

    public FlightBookingSystem deepCopy() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            oos.flush();
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (FlightBookingSystem) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to create deep copy", e);
        }
    }

    public List<Flight> getFutureFlights() {
        List<Flight> futureFlights = new ArrayList<>();
        for (Flight flight : flights) {
            if (flight.getDepartureDate().isAfter(systemDate)) {
                futureFlights.add(flight);
            }
        }
        return futureFlights;
    }

    public void restoreFrom(FlightBookingSystem backup) {
        // Implement the restoration logic here
        // For example:
        this.flights = new ArrayList<>(backup.flights);
        this.customers = new ArrayList<>(backup.customers);
        this.bookings = new ArrayList<>(backup.bookings);
    }

    public void updateBooking(Booking booking) {
        int index = bookings.indexOf(booking);
        if (index != -1) {
            bookings.set(index, booking);
            saveDataToFiles();
        }
    }

    public Booking getBookingById(String bookingId) {
        return bookings.stream()
                .filter(booking -> String.valueOf(booking.getId()).equals(bookingId))
                .findFirst()
                .orElse(null);
    }

    public Booking findBooking(int customerId, int flightId) {
        return bookings.stream()
            .filter(b -> b.getCustomer().getId() == customerId && b.getFlight().getId() == flightId)
            .findFirst()
            .orElse(null);
    }

    public void displayFlights() {
        for (Flight flight : flights) {
            if (!flight.isDeleted()) {
                System.out.println(flight);
            }
        }
    }

    public void clearAllData() {
        customers.clear();
        flights.clear();
        bookings.clear();
        saveDataToFiles(); 
    }

}