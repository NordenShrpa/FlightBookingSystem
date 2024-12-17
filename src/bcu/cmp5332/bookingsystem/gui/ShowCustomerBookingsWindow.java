package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ShowCustomerBookingsWindow extends JFrame {
    public ShowCustomerBookingsWindow(Customer customer) {
        setTitle("Bookings for " + customer.getName());
        setSize(500, 300);
        setLocationRelativeTo(null);

        List<Booking> bookings = customer.getBookings();
        String[] columns = {"Booking ID", "Flight", "Origin", "Destination", "Date", "Price"};
        Object[][] data = new Object[bookings.size()][6];

        for (int i = 0; i < bookings.size(); i++) {
            Booking booking = bookings.get(i);
            Flight flight = booking.getFlight();
            data[i] = new Object[]{
                booking.getId(),
                flight.getFlightNumber(),
                flight.getOrigin(),
                flight.getDestination(),
                flight.getDepartureDate(),
                booking.getPrice()
            };
        }

        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }
}

