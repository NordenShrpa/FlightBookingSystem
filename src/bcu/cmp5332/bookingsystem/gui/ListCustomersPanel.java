package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.awt.BorderLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ListCustomersPanel extends JPanel {
    private final FlightBookingSystem system;
    private final JList<Customer> customerList;
    private final DefaultListModel<Customer> listModel;

    public ListCustomersPanel(FlightBookingSystem system) {
        this.system = system;
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        customerList = new JList<>(listModel);
        add(new JScrollPane(customerList), BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshCustomerList());
        add(refreshButton, BorderLayout.SOUTH);

        refreshCustomerList();
    }

    private void refreshCustomerList() {
        listModel.clear();
        for (Customer customer : system.getCustomers()) {
            listModel.addElement(customer);
        }
    }
}
