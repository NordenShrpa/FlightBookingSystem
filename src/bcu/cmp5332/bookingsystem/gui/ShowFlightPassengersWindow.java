package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ShowFlightPassengersWindow extends JFrame {
    public ShowFlightPassengersWindow(Flight flight) {
        setTitle("Passengers for Flight " + flight.getFlightNumber());
        setSize(400, 300);
        setLocationRelativeTo(null);

        List<Customer> passengers = flight.getPassengers();
        String[] columns = {"ID", "Name", "Phone", "Email"};
        Object[][] data = new Object[passengers.size()][4];

        for (int i = 0; i < passengers.size(); i++) {
            Customer passenger = passengers.get(i);
            data[i] = new Object[]{
                passenger.getId(),
                passenger.getName(),
                passenger.getEmail()
            };
        }

        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}

