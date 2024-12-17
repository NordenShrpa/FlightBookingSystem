package bcu.cmp5332.bookingsystem.model;
import java.io.Serializable;
import java.time.LocalDate;

public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int nextId = 1;

    private int id;
    private final Customer customer;
    private Flight flight;
    private LocalDate bookingDate;
    private final double price;
    private boolean isCancelled;
    private static final double CANCELLATION_FEE_PERCENTAGE = 0.10; // 10% cancellation fee

    public Booking(Customer customer, Flight flight, LocalDate bookingDate, double price) {
        this.id = nextId++;
        this.customer = customer;
        this.flight = flight;
        this.bookingDate = bookingDate;
        this.price = price;
        this.isCancelled = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public double getPrice() {
        return price;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public double getCancellationFee() {
        return price * CANCELLATION_FEE_PERCENTAGE;
    }

    public double getRefundAmount() {
        return price - getCancellationFee();
    }

    @Override
    public String toString() {
        String status = isCancelled ? "Cancelled" : "Active";
        return String.format("Booking ID: %d, Customer: %s, Flight: %s, Date: %s, Price: $%.2f, Status: %s",
                id, customer.getName(), flight.getFlightNumber(), bookingDate, price, status);
    }

    // For testing purposes
    public static void resetIdCounter() {
        nextId = 1;
    }

    private boolean isCanceled = false;

    public void cancel() {
        this.isCanceled = true;
    }

    public boolean isCanceled() {
        return isCanceled;
    }
}

