package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.io.IOException;

public interface Command {

    public static final String HELP_MESSAGE = "Commands:\n"
        + "\tlistflights                               print all flights\n"
        + "\tlistcustomers                             print all customers\n"
        + "\taddflight                                 add a new flight\n"
        + "\taddcustomer                               add a new customer\n"
        + "\taddbooking [customer id] [flight id]      add a new booking\n"
        + "\tcancelbooking [booking id]                cancel a booking\n"
        + "\teditbooking [booking id] [flight id]      update a booking\n"
        + "\tloadgui                                   loads the GUI version of the app\n"
        + "\thelp                                      prints this help message\n"
        + "\texit                                      exits the program";

    
    void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException;
    
    default void executeAndStore(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        FlightBookingSystem backup = flightBookingSystem.deepCopy();
        try {
            execute(flightBookingSystem);
            FlightBookingSystemData.store(flightBookingSystem);
        } catch (IOException e) {
            System.out.println("Error saving data. Rolling back changes.");
            flightBookingSystem.restoreFrom(backup);
            throw new FlightBookingSystemException("Failed to save data: " + e.getMessage());
        }
    }
}
