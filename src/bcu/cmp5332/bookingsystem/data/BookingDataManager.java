package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

public class BookingDataManager implements DataManager {
    
    public final String RESOURCE = "resources/data/bookings.txt";

    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
        try (BufferedReader reader = new BufferedReader(new FileReader(RESOURCE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(SEPARATOR);
                int id = Integer.parseInt(parts[0]);
                int customerId = Integer.parseInt(parts[1]);
                int flightId = Integer.parseInt(parts[2]);
                LocalDate bookingDate = LocalDate.parse(parts[3]);
                boolean isCanceled = Boolean.parseBoolean(parts[4]);

                Customer customer = fbs.getCustomerByID(customerId);
                if (customer != null) {
                    Flight flight = fbs.getFlightByID(flightId);
                    Booking booking = new Booking(customer, flight, bookingDate, 0.0); // Assuming 0.0 for price
                    booking.setId(id); // Added this line to set the booking ID
                    if (isCanceled) {
                        booking.cancel();
                    } else {
                        flight.addPassenger(customer);
                    }
                    customer.addBooking(booking);
                    fbs.addBooking(booking);
                } else {
                    System.err.println("Customer with ID " + customerId + " not found.");
                }
            }
        }
    }

    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        if (!RESOURCE.equals("resources/data/bookings.txt")) {
            throw new IOException("Invalid file path: " + RESOURCE);
        }
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Booking booking : fbs.getBookings()) {
                out.printf("%d%s%d%s%d%s%s%s%b%n",
                    booking.getId(),
                    SEPARATOR,
                    booking.getCustomer().getId(),
                    SEPARATOR,
                    booking.getFlight().getId(),
                    SEPARATOR,
                    booking.getBookingDate(),
                    SEPARATOR,
                    booking.isCanceled()
                );
            }
        }
    }
}