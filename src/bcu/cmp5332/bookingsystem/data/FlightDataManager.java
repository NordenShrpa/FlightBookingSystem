package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

public class FlightDataManager implements DataManager {
    
    private final String RESOURCE = "resources/data/flights.txt";
    
    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
        try (BufferedReader reader = new BufferedReader(new FileReader(RESOURCE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(SEPARATOR);
                if (parts.length >= 2) {  // Ensure we have at least 2 parts
                    String flightNumber = parts[1].trim();
                    String origin = parts[2].trim();
                    String destination = parts[3].trim();
                    LocalDate departureDate = LocalDate.parse(parts[4]);
                    int capacity = Integer.parseInt(parts[5]);
                    double basePrice = Double.parseDouble(parts[6]);
                    boolean isDeleted = Boolean.parseBoolean(parts[7]);

                    Flight flight = new Flight(flightNumber, origin, destination, departureDate, capacity, basePrice);
                    if (isDeleted) {
                        flight.setDeleted(true);
                    }
                    fbs.addFlight(flight);
                } else {
                    System.out.println("Skipping invalid line: " + line);
                }
            }
        }
    }
    
    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        if (!RESOURCE.equals("resources/data/flights.txt")) {
            throw new IOException("Invalid file path: " + RESOURCE);
        }
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Flight flight : fbs.getFlights()) {
                out.printf("%d%s%s%s%s%s%s%s%s%s%d%s%.2f%s%b%n",
                    flight.getId(),
                    SEPARATOR,
                    flight.getFlightNumber(),
                    SEPARATOR,
                    flight.getOrigin(),
                    SEPARATOR,
                    flight.getDestination(),
                    SEPARATOR,
                    flight.getDepartureDate(),
                    SEPARATOR,
                    flight.getCapacity(),
                    SEPARATOR,
                    flight.getBasePrice(),
                    SEPARATOR,
                    flight.isDeleted()
                );
            }
        }
    }
}