package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FlightBookingSystemData {
    
    private static final List<DataManager> dataManagers = new ArrayList<>();
    
    static {
        dataManagers.add(new FlightDataManager());
        dataManagers.add(new BookingDataManager());
        dataManagers.add(new CustomerDataManager());
    }
    
    public static FlightBookingSystem load() throws FlightBookingSystemException {
        FlightBookingSystem fbs = new FlightBookingSystem();
        File file = new File("resources/data/flightbooking.txt");
        if (!file.exists()) {
            // File doesn't exist, return a new system with default data
            return fbs;
        }
        try {
            for (DataManager dm : dataManagers) {
                dm.loadData(fbs);
            }
        } catch (IOException e) {
            throw new FlightBookingSystemException("Error loading data", e);
        }

        // Add debug information for customer loading
        System.out.println("Loading customer data...");
        // Your customer loading code here
        System.out.println("Number of customers loaded: " + fbs.getCustomers().size());

        return fbs;
    }


public static void store(FlightBookingSystem fbs) throws IOException {
    for (DataManager dm : dataManagers) {
        dm.storeData(fbs);
    }
}
}