package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.awt.GridLayout;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class AddBooking extends JDialog implements Command {
    private FlightBookingSystem system;
    private JComboBox<Customer> customerComboBox;
    private JComboBox<Flight> flightComboBox;
    private JLabel priceLabel;
    private int customerId;
    private String flightNumber;

    // Constructor for GUI
    public AddBooking(JFrame parent, FlightBookingSystem system) {
        super(parent, "Add Booking", true);
        this.system = system;
        initializeGUI(parent);
    }

    // Constructor for command-line
    public AddBooking() {
        this.system = null; 
    }

    // New constructor for command-line with customerId and flightNumber
    public AddBooking(int customerId, String flightNumber) {
        this.customerId = customerId;
        this.flightNumber = flightNumber;
    }

    private void initializeGUI(JFrame parent) {
        setLayout(new GridLayout(5, 1, 5, 5));
        setSize(400, 250);

        customerComboBox = new JComboBox<>(system.getCustomers().toArray(Customer[]::new));
        flightComboBox = new JComboBox<>(system.getFlights().toArray(Flight[]::new));
        
        add(new JLabel("Select Customer:"));
        add(customerComboBox);
        add(new JLabel("Select Flight:"));
        add(flightComboBox);

        priceLabel = new JLabel("Price: N/A");
        add(priceLabel);

        JButton addButton = new JButton("Add Booking");
        addButton.addActionListener(e -> addBooking());
        add(addButton);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        add(closeButton);

        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void addBooking() {
        Customer selectedCustomer = (Customer) customerComboBox.getSelectedItem();
        Flight selectedFlight = (Flight) flightComboBox.getSelectedItem();
        
        if (selectedCustomer == null || selectedFlight == null) {
            JOptionPane.showMessageDialog(this, "Please select both a customer and a flight", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalDate bookingDate = LocalDate.now();
        double price = selectedFlight.getPrice(); 
        Booking newBooking = new Booking(selectedCustomer, selectedFlight, bookingDate, price);
        
        system.addBooking(newBooking);
        JOptionPane.showMessageDialog(this, "Booking added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    @Override
    public void execute(FlightBookingSystem system) {
        this.system = system;
        
        System.out.println("Customer ID: " + customerId);
        System.out.println("Flight Number: " + flightNumber);

        Customer customer = system.getCustomerById(customerId);
        Flight flight = system.getFlightByNumber(flightNumber);

        if (customer != null) {
            System.out.println("Customer found: " + customer.getName());
        } else {
            System.out.println("Customer not found");
        }

        if (flight != null) {
            System.out.println("Flight found: " + flight.getFlightNumber());
        } else {
            System.out.println("Flight not found");
        }

        if (customer != null && flight != null) {
            LocalDate bookingDate = LocalDate.now();
            double price = flight.getPrice();
            Booking newBooking = new Booking(customer, flight, bookingDate, price);
            system.addBooking(newBooking);
            System.out.println("Booking added successfully");
        } else {
            System.out.println("Invalid customer ID or flight number");
        }
    }
}


