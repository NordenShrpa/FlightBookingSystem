package bcu.cmp5332.bookingsystem.model;

import java.awt.GridLayout;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class IssueBooking extends JDialog {
    private final FlightBookingSystem system;
    private final JComboBox<Customer> customerComboBox;
    private final JComboBox<Flight> flightComboBox;
    private final JLabel priceLabel;

    public IssueBooking(JFrame parent, FlightBookingSystem system) {
        super(parent, "Issue New Booking", true);
        this.system = system;
        
        setLayout(new GridLayout(4, 2, 5, 5));
        setSize(400, 200);

        add(new JLabel("Select Customer:"));
        customerComboBox = new JComboBox<>(system.getCustomers().toArray(Customer[]::new));
        add(customerComboBox);

        add(new JLabel("Select Flight:"));
        flightComboBox = new JComboBox<>(system.getFlights().toArray(Flight[]::new));
        flightComboBox.addActionListener(e -> updatePrice());
        add(flightComboBox);

        add(new JLabel("Price:"));
        priceLabel = new JLabel();
        add(priceLabel);

        JButton bookButton = new JButton("Book Flight");
        bookButton.addActionListener(e -> issueBooking());
        add(bookButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);

        updatePrice();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void updatePrice() {
        Flight selectedFlight = (Flight) flightComboBox.getSelectedItem();
        if (selectedFlight != null) {
            double price = system.calculateFlightPrice(selectedFlight);
            priceLabel.setText(String.format("$%.2f", price));
        } else {
            priceLabel.setText("N/A");
        }
    }

    private void issueBooking() {
        Customer selectedCustomer = (Customer) customerComboBox.getSelectedItem();
        Flight selectedFlight = (Flight) flightComboBox.getSelectedItem();

        if (selectedCustomer == null || selectedFlight == null) {
            JOptionPane.showMessageDialog(this, "Please select both a customer and a flight", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (selectedFlight.isFull()) {
            JOptionPane.showMessageDialog(this, "This flight is full", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double price = system.calculateFlightPrice(selectedFlight);
        LocalDate bookingDate = LocalDate.now();
        Booking newBooking = new Booking(selectedCustomer, selectedFlight, bookingDate, price);
        system.issueBooking(newBooking);

        JOptionPane.showMessageDialog(this, "Booking issued successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}
