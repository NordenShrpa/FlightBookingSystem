package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.awt.GridLayout;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class IssueBookingWindow extends JFrame {
    private final FlightBookingSystem system;
    private final JTextField customerIdField, flightNumberField;

    public IssueBookingWindow(FlightBookingSystem system) {
        this.system = system;
        setTitle("Issue Booking");
        setSize(300, 150);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Customer ID:"));
        customerIdField = new JTextField();
        add(customerIdField);

        add(new JLabel("Flight Number:"));
        flightNumberField = new JTextField();
        add(flightNumberField);

        JButton bookButton = new JButton("Book");
        bookButton.addActionListener(e -> issueBooking());
        add(bookButton);

        setVisible(true);
    }

    private void issueBooking() {
        int customerId = Integer.parseInt(customerIdField.getText());
        String flightNumber = flightNumberField.getText();

        Customer customer = system.getCustomerById(customerId);
        Flight flight = system.getFlightByNumber(flightNumber);

        if (customer == null || flight == null) {
            JOptionPane.showMessageDialog(this, "Customer or flight not found", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalDate bookingDate = LocalDate.now(); 
        double price = flight.getPrice();
        Booking booking = new Booking(customer, flight, bookingDate, price);
        system.addBooking(booking);
        JOptionPane.showMessageDialog(this, "Booking issued successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}
