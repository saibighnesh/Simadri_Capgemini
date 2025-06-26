import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

// Enum to define the size of parking slots and vehicles
enum ParkingSize {
    SMALL,
    LARGE,
    OVERSIZE;

    // Helper method to check if a slot of this size can accommodate a given vehicle size
    public boolean canAccommodate(ParkingSize vehicleSize) {
        return switch (this) {
            case SMALL -> vehicleSize == SMALL;
            case LARGE -> vehicleSize == SMALL || vehicleSize == LARGE;
            case OVERSIZE -> vehicleSize == SMALL || vehicleSize == LARGE || vehicleSize == OVERSIZE;
        };
    }
}

// Represents a vehicle entering the parking lot
class Vehicle {
    private String licensePlate;
    private ParkingSize size;

    public Vehicle(String licensePlate, ParkingSize size) {
        this.licensePlate = licensePlate;
        this.size = size;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public ParkingSize getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "License Plate: " + licensePlate + ", Size: " + size;
    }
}

// Represents a single parking slot
class ParkingSlot {
    private int slotId;
    private ParkingSize size;
    private boolean isOccupied;
    private Vehicle parkedVehicle;

    public ParkingSlot(int slotId, ParkingSize size) {
        this.slotId = slotId;
        this.size = size;
        this.isOccupied = false;
        this.parkedVehicle = null;
    }

    public int getSlotId() {
        return slotId;
    }

    public ParkingSize getSize() {
        return size;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public Vehicle getParkedVehicle() {
        return parkedVehicle;
    }

    // Occupy the slot with a vehicle
    public boolean occupy(Vehicle vehicle) {
        if (!isOccupied && this.size.canAccommodate(vehicle.getSize())) {
            this.parkedVehicle = vehicle;
            this.isOccupied = true;
            return true;
        }
        return false;
    }

    // Vacate the slot
    public void vacate() {
        this.parkedVehicle = null;
        this.isOccupied = false;
    }

    @Override
    public String toString() {
        String status = isOccupied ? "OCCUPIED by " + parkedVehicle.getLicensePlate() : "AVAILABLE";
        return "Slot ID: " + slotId + ", Size: " + size + ", Status: " + status;
    }
}

// Represents a single parking event (parked and unparked times for a vehicle)
class ParkingEvent {
    private String licensePlate;
    private ParkingSize size;
    private LocalDateTime parkTime;
    private LocalDateTime unparkTime;

    public ParkingEvent(String licensePlate, ParkingSize size, LocalDateTime parkTime) {
        this.licensePlate = licensePlate;
        this.size = size;
        this.parkTime = parkTime;
        this.unparkTime = null;
    }

    // Getters for report generation
    public String getLicensePlate() {
        return licensePlate;
    }

    public ParkingSize getSize() {
        return size;
    }

    public LocalDateTime getParkTime() {
        return parkTime;
    }

    public LocalDateTime getUnparkTime() {
        return unparkTime;
    }

    // Setter to record unpark time
    public void setUnparkTime(LocalDateTime unparkTime) {
        this.unparkTime = unparkTime;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String parkedAt = parkTime.format(formatter);
        String unparkedAt = (unparkTime != null) ? unparkTime.format(formatter) : "N/A (Still Parked)";
        return "  License Plate: " + licensePlate + ", Size: " + size +
               ", Parked: " + parkedAt + ", Unparked: " + unparkedAt;
    }
}


// Manages the parking lot operations
class ParkingLot {
    private List<ParkingSlot> slots;
    private int totalSlots;
    private int smallSlotCount;
    private int largeSlotCount;
    private int oversizeSlotCount;
    private List<ParkingEvent> parkingHistory;

    public ParkingLot(int totalSlots) {
        this.totalSlots = totalSlots;
        this.slots = new ArrayList<>();
        this.parkingHistory = new ArrayList<>();
        initializeSlots();
    }

    // Initializes parking slots with a distribution of sizes
    private void initializeSlots() {
        smallSlotCount = totalSlots / 3;
        largeSlotCount = totalSlots / 3;
        oversizeSlotCount = totalSlots - smallSlotCount - largeSlotCount;

        // Create Small slots
        for (int i = 0; i < smallSlotCount; i++) {
            slots.add(new ParkingSlot(i + 1, ParkingSize.SMALL));
        }
        // Create Large slots
        for (int i = 0; i < largeSlotCount; i++) {
            slots.add(new ParkingSlot(smallSlotCount + i + 1, ParkingSize.LARGE));
        }
        // Create Oversize slots
        for (int i = 0; i < oversizeSlotCount; i++) {
            slots.add(new ParkingSlot(smallSlotCount + largeSlotCount + i + 1, ParkingSize.OVERSIZE));
        }

        System.out.println("");
        System.out.println("Parking lot initialized with " + totalSlots + " slots:");
        System.out.println("  " + smallSlotCount + " Small slots");
        System.out.println("  " + largeSlotCount + " Large slots");
        System.out.println("  " + oversizeSlotCount + " Oversize slots");
    }

    // Parks a vehicle in the first available suitable slot
    public boolean parkVehicle(Vehicle vehicle) {
        for (ParkingSlot slot : slots) {
            if (slot.occupy(vehicle)) {
                parkingHistory.add(new ParkingEvent(vehicle.getLicensePlate(), vehicle.getSize(), LocalDateTime.now()));
                System.out.println("");
                System.out.println("Vehicle " + vehicle.getLicensePlate() + " parked in Slot ID: " + slot.getSlotId() + " (Size: " + slot.getSize() + ")");
                return true;
            }
        }
        System.out.println("No suitable parking slot found internally for vehicle " + vehicle.getLicensePlate() + " (Size: " + vehicle.getSize() + ").");
        return false;
    }

    // Unparks a vehicle by its license plate
    public boolean unparkVehicle(String licensePlate) {
        for (ParkingSlot slot : slots) {
            if (slot.isOccupied() && slot.getParkedVehicle().getLicensePlate().equalsIgnoreCase(licensePlate)) {
                Optional<ParkingEvent> eventToUpdate = parkingHistory.stream()
                    .filter(e -> e.getLicensePlate().equalsIgnoreCase(licensePlate) && e.getUnparkTime() == null)
                    .findFirst();

                eventToUpdate.ifPresent(event -> event.setUnparkTime(LocalDateTime.now()));

                System.out.println("");
                System.out.println("Vehicle " + licensePlate + " unparked from Slot ID: " + slot.getSlotId());
                slot.vacate();
                return true;
            }
        }
        System.out.println("No vehicle found with that license plate.");
        return false;
    }

    // Displays the current status of all parking slots
    public void displayStatus() {
        System.out.println("\n--- Parking Lot Status ---");
        long smallAvailable = slots.stream().filter(s -> s.getSize() == ParkingSize.SMALL && !s.isOccupied()).count();
        long largeAvailable = slots.stream().filter(s -> s.getSize() == ParkingSize.LARGE && !s.isOccupied()).count();
        long oversizeAvailable = slots.stream().filter(s -> s.getSize() == ParkingSize.OVERSIZE && !s.isOccupied()).count();

        System.out.println("Available Slots: ");
        System.out.println("  Small: " + smallAvailable);
        System.out.println("  Large: " + largeAvailable);
        System.out.println("  Oversize: " + oversizeAvailable);
        System.out.println("--------------------------");

        for (ParkingSlot slot : slots) {
            System.out.println(slot);
        }
        System.out.println("--------------------------");
    }

    // Method to check if any vehicles are currently parked
    public boolean hasParkedVehicles() {
        for (ParkingSlot slot : slots) {
            if (slot.isOccupied()) {
                return true;
            }
        }
        return false;
    }

    // Method to check if there is any slot available for a given vehicle size
    public boolean isSlotAvailableFor(ParkingSize vehicleSize) {
        for (ParkingSlot slot : slots) {
            if (!slot.isOccupied() && slot.getSize().canAccommodate(vehicleSize)) {
                return true;
            }
        }
        return false;
    }


    // Getter for parking history for report generation
    public List<ParkingEvent> getParkingHistory() {
        return parkingHistory;
    }

    // Getters for report generation (existing)
    public int getTotalSlots() {
        return totalSlots;
    }

    public int getSmallSlotCount() {
        return smallSlotCount;
    }

    public int getLargeSlotCount() {
        return largeSlotCount;
    }

    public int getOversizeSlotCount() {
        return oversizeSlotCount;
    }

    public List<ParkingSlot> getSlots() {
        return slots;
    }
}

// Main application class for command-line interaction
public class ParkingLotApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ParkingLot parkingLot = null;

        System.out.println("");
        System.out.println("Welcome to the Parking Lot Management Application!");
        System.out.println("");

        while (parkingLot == null) {
            System.out.print("Enter the total number of parking slots (N): ");
            if (scanner.hasNextInt()) {
                int n = scanner.nextInt();
                if (n > 0) {
                    parkingLot = new ParkingLot(n);
                } else {
                    System.out.println("Please enter a positive number for slots.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }

        int choice;
        do {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Park Vehicle");
            System.out.println("2. Unpark Vehicle");
            System.out.println("3. Display Parking Status");
            System.out.println("4. Exit");
            System.out.println("");
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.println("");
                        ParkingSize vehicleSize = null;
                        while (vehicleSize == null) {
                            System.out.println("Select vehicle size:");
                            System.out.println("  1. SMALL (Small and compact car)");
                            System.out.println("  2. LARGE (Full-size car)");
                            System.out.println("  3. OVERSIZE (SUV or Truck)");
                            System.out.println("");
                            System.out.print("Enter your choice (1-3): ");
                            if (scanner.hasNextInt()) {
                                int sizeChoice = scanner.nextInt();
                                scanner.nextLine();
                                switch (sizeChoice) {
                                    case 1:
                                        vehicleSize = ParkingSize.SMALL;
                                        break;
                                    case 2:
                                        vehicleSize = ParkingSize.LARGE;
                                        break;
                                    case 3:
                                        vehicleSize = ParkingSize.OVERSIZE;
                                        break;
                                    default:
                                        System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                                }
                            } else {
                                System.out.println("Invalid input. Please enter a number.");
                                scanner.next();
                            }
                        }

                        if (!parkingLot.isSlotAvailableFor(vehicleSize)) {
                            System.out.println("No space available for " + vehicleSize + " vehicles.");
                        } else {
                            System.out.print("Enter vehicle license plate: ");
                            String licensePlate = scanner.nextLine();
                            parkingLot.parkVehicle(new Vehicle(licensePlate, vehicleSize));
                        }
                        System.out.println("\nPress Enter to return to menu...");
                        scanner.nextLine(); 
                        break;
                    case 2:
                        System.out.println("");
                        System.out.println("Unpark Vehicle");
                        System.out.println("");
                        if (!parkingLot.hasParkedVehicles()) { 
                            System.out.println("No vehicle parked yet.");
                        } else {
                            System.out.print("Enter license plate of vehicle to unpark: ");
                            String unparkLicensePlate = scanner.nextLine();
                            parkingLot.unparkVehicle(unparkLicensePlate);
                        }
                        System.out.println("\nPress Enter to return to menu...");
                        scanner.nextLine();
                        break;
                    case 3:
                        parkingLot.displayStatus();
                        System.out.println("\nPress Enter to return to menu...");
                        scanner.nextLine();
                        break;
                    case 4:
                        System.out.println("Exiting application. Generating report...");
                        generateReportFile(parkingLot);
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                        System.out.println("\nPress Enter to try again...");
                        scanner.nextLine();
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
                choice = 0;
                System.out.println("\nPress Enter to try again...");
                scanner.nextLine();
            }

        } while (choice != 4);

        scanner.close();
    }

    // Generates a report file with the current status of the parking lot
    private static void generateReportFile(ParkingLot parkingLot) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = now.format(formatter);
        String fileName = "parking_lot_report_" + formattedDate + ".txt";

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {
            writer.println("--- Parking Lot Report ---");
            writer.println("Report Generated: " + now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            writer.println("--------------------------");
            writer.println("Total Parking Slots: " + parkingLot.getTotalSlots());
            writer.println("Slot Distribution:");
            writer.println("  Small Slots: " + parkingLot.getSmallSlotCount());
            writer.println("  Large Slots: " + parkingLot.getLargeSlotCount());
            writer.println("  Oversize Slots: " + parkingLot.getOversizeSlotCount());
            writer.println("--------------------------");
            writer.println("Current Slot Status:");
            for (ParkingSlot slot : parkingLot.getSlots()) {
                String status = slot.isOccupied() ?
                        "OCCUPIED by " + slot.getParkedVehicle().getLicensePlate() + " (Size: " + slot.getParkedVehicle().getSize() + ")" :
                        "AVAILABLE";
                writer.println("Slot ID: " + slot.getSlotId() + ", Size: " + slot.getSize() + ", Status: " + status);
            }
            writer.println("--------------------------");

            writer.println("\n--- Parking History ---");
            if (parkingLot.getParkingHistory().isEmpty()) {
                writer.println("No vehicles have been parked yet during this session.");
            } else {
                for (ParkingEvent event : parkingLot.getParkingHistory()) {
                    writer.println(event.toString());
                }
            }
            writer.println("--------------------------");

            System.out.println("Parking lot report successfully generated to " + fileName);
        } catch (IOException e) {
            System.err.println("Error generating parking lot report: " + e.getMessage());
        }
    }
}
