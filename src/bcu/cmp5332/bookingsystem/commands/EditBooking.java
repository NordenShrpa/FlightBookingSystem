package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.time.LocalDate;
import java.util.Scanner;

public class EditBooking implements Command {
    private final Scanner scanner;

    public EditBooking(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void execute(FlightBookingSystem system) {
        System.out.print("Enter booking ID: ");
        String bookingId = scanner.nextLine();

        Booking booking = system.getBookingById(bookingId);
        if (booking == null) {
            System.out.println("Booking not found.");
            return;
        }

        System.out.println("Current booking details:");
        System.out.println(booking);

        System.out.print("Enter new flight number (or press enter to keep current): ");
        String newFlightNumber = scanner.nextLine();
        if (!newFlightNumber.isEmpty()) {
            Flight newFlight = system.getFlightByNumber(newFlightNumber);
            if (newFlight == null) {
                System.out.println("Flight not found. Booking not updated.");
                return;
            }
            booking.setFlight(newFlight);
        }

        System.out.print("Enter new booking date (YYYY-MM-DD) (or press enter to keep current): ");
        String newDateStr = scanner.nextLine();
        if (!newDateStr.isEmpty()) {
            LocalDate newDate = LocalDate.parse(newDateStr);
            booking.setBookingDate(newDate);
        }

        system.updateBooking(booking);
        System.out.println("Booking updated successfully.");
        System.out.println("Updated booking details:");
        System.out.println(booking);
    }
}
