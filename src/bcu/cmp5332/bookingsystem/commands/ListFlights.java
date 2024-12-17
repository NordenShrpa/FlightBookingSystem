package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.util.List;

public class ListFlights implements Command {
    /* List all future flights stored in the system. */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        List<Flight> flights = flightBookingSystem.getFutureFlights();
        if (flights.isEmpty()) {
            System.out.println("No future flights found.");
        } else {
			  System.out.println("Available flights:");
            for (Flight flight : flights) {
                System.out.println(flight);
            }
        }
    }
}



