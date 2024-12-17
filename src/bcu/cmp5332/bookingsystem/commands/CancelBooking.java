package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem; 
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class CancelBooking extends JDialog implements Command {
    private FlightBookingSystem system;
    private JList<Booking> bookingList;
    private JLabel cancellationFeeLabel;
    private int customerId;
    private int flightId;

    // Constructor for GUI
    public CancelBooking(JFrame parent, FlightBookingSystem system) {
        super(parent, "Cancel Booking", true);
        this.system = system;
        initializeGUI(parent);
    }

    // Constructor for command-line
    public CancelBooking(int customerId, int flightId) {
        this.system = null; 
        this.customerId = customerId;
        this.flightId = flightId;
    }

    private void initializeGUI(JFrame parent) {
        this.bookingList = new JList<>(system.getBookings().toArray(Booking[]::new));
        bookingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bookingList.addListSelectionListener(e -> updateCancellationFee());

        setLayout(new GridLayout(4, 1, 5, 5));
        setSize(400, 200);

        add(new JScrollPane(bookingList));

        cancellationFeeLabel = new JLabel("Cancellation Fee: N/A");
        add(cancellationFeeLabel);

        JButton cancelButton = new JButton("Cancel Booking");
        cancelButton.addActionListener(e -> cancelBooking());
        add(cancelButton);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        add(closeButton);

        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void updateCancellationFee() {
        Booking selectedBooking = bookingList.getSelectedValue();
        if (selectedBooking != null) {
            double cancellationFee = selectedBooking.getCancellationFee();
            cancellationFeeLabel.setText(String.format("Cancellation Fee: $%.2f", cancellationFee));
        } else {
            cancellationFeeLabel.setText("Cancellation Fee: N/A");
        }
    }

    private void cancelBooking() {
        Booking selectedBooking = bookingList.getSelectedValue();
        if (selectedBooking == null) {
            JOptionPane.showMessageDialog(this, "Please select a booking to cancel", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to cancel this booking?\nCancellation fee: " + cancellationFeeLabel.getText(), 
            "Confirm Cancellation", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            system.cancelBooking(selectedBooking);
            JOptionPane.showMessageDialog(this, "Booking cancelled successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }

    @Override
    public void execute(FlightBookingSystem system) {
        this.system = system;
        Booking bookingToCancel = system.findBooking(customerId, flightId);
        if (bookingToCancel != null) {
            double cancellationFee = bookingToCancel.getCancellationFee();
            System.out.printf("Cancellation fee: $%.2f\n", cancellationFee);
            System.out.print("Are you sure you want to cancel this booking? (y/n): ");
            String confirmation = System.console().readLine().trim().toLowerCase();
            if (confirmation.equals("y")) {
                system.cancelBooking(bookingToCancel);
                System.out.println("Booking cancelled successfully");
            } else {
                System.out.println("Cancellation aborted");
            }
        } else {
            System.out.println("Booking not found");
        }
    }
}
