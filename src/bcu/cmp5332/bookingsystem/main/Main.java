package bcu.cmp5332.bookingsystem.main;

import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        FlightBookingSystem fbs = new FlightBookingSystem(); // Initialize with an empty system
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;

        System.out.println("Flight Booking System");
        System.out.println("Enter 'help' to see a list of available commands.");

        try {
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                if (line.equalsIgnoreCase("exit")) break;

                try {
                    Command command = CommandParser.parse(line);
                    if (command != null) {
                        command.execute(fbs);
                    } else {
                        System.out.println("Invalid command. Type 'help' for a list of available commands.");
                    }
                } catch (IOException | FlightBookingSystemException e) {
                    System.out.println("Error: " + e.getMessage());
                } catch (IllegalArgumentException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
        }

        System.out.println("Exiting Flight Booking System.");
    }
}
