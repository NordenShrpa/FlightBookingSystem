package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerDataManager implements DataManager {
    private Map<Integer, Customer> customers = new HashMap<>();
    private static final String FILE_NAME = "resources/data/customers.txt";

    public void addCustomer(Customer customer) {
        customers.put(customer.getId(), customer);
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }

    public void saveToFile() throws IOException {
        if (!FILE_NAME.equals("resources/data/customers.txt")) {
            throw new IOException("Invalid file path: " + FILE_NAME);
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(customers);
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile() throws IOException, ClassNotFoundException {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                customers = (Map<Integer, Customer>) ois.readObject();
            }
        }
    }

    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException {
        // Implementation
    }

    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        // Implementation
    }
}