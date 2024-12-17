package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.util.Scanner;

public class AddCustomer implements Command {
    private final String name;
    private final String phone;
    private final String email;

    public AddCustomer(String name, String email, String phone) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public void execute(FlightBookingSystem system) throws FlightBookingSystemException {
        // Validate input
        if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            throw new FlightBookingSystemException("All fields are required for adding a customer.");
        }

        // Create and add the new customer
        Customer newCustomer = new Customer(name, phone, email);
        system.addCustomer(newCustomer);
        System.out.println("Customer added successfully: " + newCustomer);
    }

    @Override
    public String toString() {
        return "AddCustomer: " + name + " with phone " + phone + " and email " + email;
    }

    // Main method for testing
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            System.out.print("Enter phone: ");
            String phone = scanner.nextLine();
            System.out.print("Enter email: ");
            String email = scanner.nextLine();

            FlightBookingSystem system = new FlightBookingSystem();
            AddCustomer addCustomer = new AddCustomer(name, email, phone);
            try {
                addCustomer.execute(system);
            } catch (FlightBookingSystemException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

