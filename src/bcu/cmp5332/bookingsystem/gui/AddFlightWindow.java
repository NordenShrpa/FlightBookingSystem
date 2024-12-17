package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class AddFlightWindow extends JFrame implements ActionListener {

    private final FlightBookingSystem system;
    private final JTextField flightNumberField, originField, destinationField, dateField, capacityField, priceField;

    public AddFlightWindow(FlightBookingSystem system) {
        this.system = system;
        setTitle("Add Flight");
        setSize(300, 250);
        setLayout(new GridLayout(7, 2));

        add(new JLabel("Flight Number:"));
        flightNumberField = new JTextField();
        add(flightNumberField);

        add(new JLabel("Origin:"));
        originField = new JTextField();
        add(originField);

        add(new JLabel("Destination:"));
        destinationField = new JTextField();
        add(destinationField);

        add(new JLabel("Date (YYYY-MM-DD):"));
        dateField = new JTextField();
        add(dateField);

        add(new JLabel("Capacity:"));
        capacityField = new JTextField();
        add(capacityField);

        add(new JLabel("Price:"));
        priceField = new JTextField();
        add(priceField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(this);
        add(addButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        addFlight();
    }

    private void addFlight() {
        try {
            String flightNumber = flightNumberField.getText();
            String origin = originField.getText();
            String destination = destinationField.getText();
            LocalDate date = LocalDate.parse(dateField.getText(), DateTimeFormatter.ISO_LOCAL_DATE);
            int capacity = Integer.parseInt(capacityField.getText());
            double price = Double.parseDouble(priceField.getText());

            Flight flight = new Flight(flightNumber, origin, destination, date, capacity, price);
            system.addFlight(flight);
            JOptionPane.showMessageDialog(this, "Flight added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please check all fields.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
