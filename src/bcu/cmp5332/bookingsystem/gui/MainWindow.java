package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.CancelBooking;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.model.IssueBooking;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class MainWindow extends JFrame {
    private final FlightBookingSystem system;
    private JList<Customer> customerList;
    private JList<Flight> flightList;
    private JList<Booking> bookingList;

    public MainWindow(FlightBookingSystem system) {
        this.system = system;
        setTitle("Flight Booking System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                system.saveDataToFiles();
            }
        });

        createMenuBar();
        createMainPanel();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> {
            system.saveDataToFiles();
            System.exit(0);
        });
        fileMenu.add(exitItem);

        JMenuItem clearAllDataItem = new JMenuItem("Clear All Data");
        fileMenu.add(clearAllDataItem);

        clearAllDataItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(MainWindow.this,
                    "Are you sure you want to clear all data? This will delete all customers, flights, and bookings.",
                    "Confirm Clear All Data", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    system.clearAllData();
                    refreshLists();
                    JOptionPane.showMessageDialog(MainWindow.this,
                        "All data has been cleared.",
                        "Data Cleared", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        JMenu customerMenu = new JMenu("Customer");
        JMenuItem addCustomerItem = new JMenuItem("Add Customer");
        addCustomerItem.addActionListener(e -> {
            AddCustomerWindow addCustomerWindow = new AddCustomerWindow(system);
            addCustomerWindow.show();
            refreshLists();
        });
        customerMenu.add(addCustomerItem);

        JMenu flightMenu = new JMenu("Flight");
        JMenuItem addFlightItem = new JMenuItem("Add Flight");
        addFlightItem.addActionListener(e -> showAddFlightForm());
        flightMenu.add(addFlightItem);

        JMenu bookingMenu = new JMenu("Booking");
        JMenuItem issueBookingItem = new JMenuItem("Issue Booking: ");
        issueBookingItem.addActionListener(e -> new IssueBooking(this, system));
        JMenuItem cancelBookingItem = new JMenuItem("Cancel Booking");
        cancelBookingItem.addActionListener(e -> new CancelBooking(this, system));
        bookingMenu.add(issueBookingItem);
        bookingMenu.add(cancelBookingItem);

        menuBar.add(fileMenu);
        menuBar.add(customerMenu);
        menuBar.add(flightMenu);
        menuBar.add(bookingMenu);

        setJMenuBar(menuBar);
    }

    private void createMainPanel() {
        JTabbedPane tabbedPane = new JTabbedPane();

        customerList = new JList<>(new DefaultListModel<>());
        flightList = new JList<>(new DefaultListModel<>());
        bookingList = new JList<>(new DefaultListModel<>());

        tabbedPane.addTab("Customers", new JScrollPane(customerList));
        tabbedPane.addTab("Flights", new JScrollPane(flightList));
        tabbedPane.addTab("Bookings", new JScrollPane(bookingList));

        add(tabbedPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton refreshButton = new JButton("Refresh Lists");
        refreshButton.addActionListener(e -> refreshLists());
        buttonPanel.add(refreshButton);

        add(buttonPanel, BorderLayout.SOUTH);

        refreshLists();
    }

    public void refreshLists() {
        DefaultListModel<Customer> customerModel = (DefaultListModel<Customer>) customerList.getModel();
        customerModel.clear();
        for (Customer customer : system.getCustomers()) {
            customerModel.addElement(customer);
        }

        DefaultListModel<Flight> flightModel = (DefaultListModel<Flight>) flightList.getModel();
        flightModel.clear();
        for (Flight flight : system.getFlights()) {
            flightModel.addElement(flight);
        }

        DefaultListModel<Booking> bookingModel = (DefaultListModel<Booking>) bookingList.getModel();
        bookingModel.clear();
        for (Booking booking : system.getBookings()) {
            bookingModel.addElement(booking);
        }
    }

    private void showAddFlightForm() {
        JPanel panel = new JPanel(new GridLayout(6, 2));
        JTextField flightNumberField = new JTextField();
        JTextField departureField = new JTextField();
        JTextField destinationField = new JTextField();
        JTextField capacityField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField dateField = new JTextField();
        panel.add(new JLabel("Flight Number:"));
        panel.add(flightNumberField);
        panel.add(new JLabel("Departure:"));
        panel.add(departureField);
        panel.add(new JLabel("Destination:"));
        panel.add(destinationField);
        panel.add(new JLabel("Capacity:"));
        panel.add(capacityField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Date (YYYY-MM-DD):"));
        panel.add(dateField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Flight", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String flightNumber = flightNumberField.getText();
                String departure = departureField.getText();
                String destination = destinationField.getText();
                int capacity = Integer.parseInt(capacityField.getText());
                double price = Double.parseDouble(priceField.getText());
                LocalDate date = LocalDate.parse(dateField.getText());

                Flight newFlight = new Flight(flightNumber, departure, destination, date, capacity, price);
                system.addFlight(newFlight);
                refreshLists();
                JOptionPane.showMessageDialog(this, "Flight added successfully!");
            } catch (NumberFormatException | DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please check all fields and try again.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error adding flight: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        FlightBookingSystem system = FlightBookingSystem.getInstance(); // Use a public static method to get an instance
        SwingUtilities.invokeLater(() -> new MainWindow(system));
    }
}
