package bcu.cmp5332.bookingsystem.main;

import bcu.cmp5332.bookingsystem.commands.AddBooking;
import bcu.cmp5332.bookingsystem.commands.AddCustomer;
import bcu.cmp5332.bookingsystem.commands.AddFlight;
import bcu.cmp5332.bookingsystem.commands.CancelBooking;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.commands.EditBooking;
import bcu.cmp5332.bookingsystem.commands.Help;
import bcu.cmp5332.bookingsystem.commands.ListCustomers;
import bcu.cmp5332.bookingsystem.commands.ListFlights;
import bcu.cmp5332.bookingsystem.commands.LoadGUI;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class CommandParser {
    
    public static Command parse(String line) throws IOException, FlightBookingSystemException {
        try {
            String[] parts = line.split(" ", 3);
            String cmd = parts[0].toLowerCase(); // Convert to lowercase for case-insensitive matching

            if (cmd.equals("addflight")) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Flight Number: ");
                String flighNumber = reader.readLine();
                System.out.print("Origin: ");
                String origin = reader.readLine();
                System.out.print("Destination: ");
                String destination = reader.readLine();

                LocalDate departureDate = parseDateWithAttempts(reader);

                System.out.print("Number of Seats: ");
                int seats = Integer.parseInt(reader.readLine());
                System.out.print("Price: ");
                double price = Double.parseDouble(reader.readLine());

                return new AddFlight(flighNumber, origin, destination, departureDate, seats, price);
            } else if (cmd.equals("addcustomer")) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Customer Name: ");
                String name = reader.readLine();
                System.out.print("Email: ");
                String email = reader.readLine();
                System.out.print("Phone: ");
                String phone = reader.readLine();

                return new AddCustomer(name, email, phone);
            } else if (cmd.equals("loadgui")) {
                return new LoadGUI();
            } else if (parts.length == 1) {
                switch (cmd) {
                    case "listflights":
                        return new ListFlights();
                    case "listcustomers":
                        return new ListCustomers();
                    case "help":
                        return new Help();
                    case "addbooking":
                        try (Scanner scanner = new Scanner(System.in)) {
                            System.out.print("Enter customer ID: ");
                            int customerId = scanner.nextInt();
                            System.out.print("Enter flight Number: ");
                            String flightNumber = scanner.next();
                            return new AddBooking(customerId, flightNumber);
                        }
                    case "editbooking":
                        return new EditBooking(new Scanner(System.in));
                    case "cancelbooking":
                        try (Scanner scanner = new Scanner(System.in)) {
                            System.out.print("Enter customer ID: ");
                            int customerId = scanner.nextInt();
                            System.out.print("Enter flight ID: ");
                            int flightId = scanner.nextInt();
                            return new CancelBooking(customerId, flightId);
                        }
                    default:
                        throw new FlightBookingSystemException("Invalid command.");
                }
            } else if (parts.length == 2) {
                int id = Integer.parseInt(parts[1]);
                switch (cmd) {
                    case "Flight":
                        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                        System.out.print("Origin: ");
                        String origin = reader.readLine();
                        System.out.print("Destination: ");
                        String destination = reader.readLine();
                        LocalDate departureDate = parseDateWithAttempts(reader);
                        System.out.print("Number of Seats: ");
                        int seats = Integer.parseInt(reader.readLine());
                        System.out.print("Price: ");
                        double price = Double.parseDouble(reader.readLine());
                        return new AddFlight(String.valueOf(id), origin, destination, departureDate, seats, price);
                    case "Customer":
                        System.out.print("Name: ");
                        String name = new BufferedReader(new InputStreamReader(System.in)).readLine();
                        System.out.print("Email: ");
                        String email = new BufferedReader(new InputStreamReader(System.in)).readLine();
                        System.out.print("Phone: ");
                        String phone = new BufferedReader(new InputStreamReader(System.in)).readLine();
                        return new AddCustomer(name, email, phone);
                    case "editbooking":
                        return new EditBooking(new Scanner(System.in));
                    case "cancelbooking":
                        System.out.print("Enter flight ID: ");
                        int flightId = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());
                        return new CancelBooking(id, flightId);
                    default:
                        throw new FlightBookingSystemException("Invalid command.");
                }
            } else if (parts.length == 3) {
                int customerId = Integer.parseInt(parts[1]);
                int flightId = Integer.parseInt(parts[2]);

                switch (cmd) {
                    case "addbooking":
                        return new AddBooking(customerId, String.valueOf(flightId));
                    case "editbooking":
                        return new EditBooking(new Scanner(System.in));
                    case "cancelbooking":
                        return new CancelBooking(customerId, flightId);
                    default:
                        throw new FlightBookingSystemException("Invalid command.");
                }
            }
        } catch (NumberFormatException ex) {
            throw new FlightBookingSystemException("Invalid number format in command.");
        }

        throw new FlightBookingSystemException("Invalid command.");
    }
    
    private static LocalDate parseDateWithAttempts(BufferedReader br, int attempts) throws IOException, FlightBookingSystemException {
        if (attempts < 1) {
            throw new IllegalArgumentException("Number of attempts should be higher than 0");
        }
        while (attempts > 0) {
            attempts--;
            System.out.print("Departure Date (\"YYYY-MM-DD\" format): ");
            try {
                String dateString = br.readLine();
                LocalDate departureDate = LocalDate.parse(dateString);
                return departureDate;
            } catch (DateTimeParseException dtpe) {
                System.out.println("Date must be in YYYY-MM-DD format. " + attempts + " attempts remaining...");
            }
        }
        
        throw new FlightBookingSystemException("Incorrect departure date provided. Cannot create flight.");
    }
    
    private static LocalDate parseDateWithAttempts(BufferedReader br) throws IOException, FlightBookingSystemException {
        return parseDateWithAttempts(br, 3);
    }
}
