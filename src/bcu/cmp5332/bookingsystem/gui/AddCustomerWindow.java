package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class AddCustomerWindow {
    private final JFrame parentFrame;
    private final FlightBookingSystem system;

    public AddCustomerWindow(FlightBookingSystem system) {
        this.system = system;
        this.parentFrame = new JFrame("Add Customer");
        this.parentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void show() {
        String firstName = showInputDialog("Enter First Name:");
        if (firstName == null || firstName.isEmpty()) return;

        String email = showInputDialog("Enter Email:");
        if (email == null || email.isEmpty()) return;

        String phoneNo = showInputDialog("Enter Phone Number:");
        if (phoneNo == null || phoneNo.isEmpty()) return;

        // All inputs received, add the customer
        Customer customer = new Customer(firstName, phoneNo, email);
        system.addCustomer(customer);
        JOptionPane.showMessageDialog(parentFrame, "Customer added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private String showInputDialog(String prompt) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JLabel iconLabel = new JLabel(UIManager.getIcon("OptionPane.questionIcon"));
        JLabel promptLabel = new JLabel(prompt);
        JTextField inputField = new JTextField(20);

        panel.add(iconLabel, BorderLayout.WEST);
        panel.add(promptLabel, BorderLayout.NORTH);
        panel.add(inputField, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(parentFrame, panel, "Input", 
                                                   JOptionPane.OK_CANCEL_OPTION, 
                                                   JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            return inputField.getText().trim();
        } else {
            return null; // User cancelled
        }
    }

    private boolean isValidPhone(String phone) {
        return phone.matches("\\+?\\d{10,14}"); 
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$"); 
    }
}
