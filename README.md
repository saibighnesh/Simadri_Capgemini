# Parking Lot Management Application

This is a command-line Java application designed to simulate the management of a small parking lot. It allows users to initialize a parking lot with a custom number of slots, park various vehicle types, unpark them, and generate a comprehensive report of parking activities.

## 1. Approach and Design

### Problem Statement

The goal was to create a simple, in-memory parking lot management system. Key requirements included:

- Defining `N` parking slots, configurable by the user.

- Categorizing slots into Small, Large, and Oversize.

- Categorizing vehicles into Small, Large, and Oversize.

- Implementing parking logic based on slot and vehicle size compatibility (e.g., a Large slot can accommodate Small or Large vehicles).

- Supporting vehicle entry and exit.

- Displaying the current status of the parking lot.

- Generating a report file upon application exit, detailing parking lot configuration, current status, and a history of all parking events.

- The application operates purely from the command-line, with all data managed in memory for the duration of the session.

### Design Choices

#### Object-Oriented Design (OOD)

The application follows an Object-Oriented design, breaking down the problem into logical entities:

- **`ParkingSize` (Enum):** Represents the distinct sizes for both parking slots and vehicles (SMALL, LARGE, OVERSIZE). It includes a `canAccommodate` method to define compatibility rules, ensuring correct vehicle placement.

- **`Vehicle` (Class):** Models a car with properties like `licensePlate` (String) and `size` (ParkingSize).

- **`ParkingSlot` (Class):** Represents an individual parking space. It has a `slotId` (int), `size` (ParkingSize), `isOccupied` (boolean), and a reference to the `parkedVehicle` (Vehicle). Methods like `occupy` and `vacate` manage its state.

- **`ParkingEvent` (Class):** A new addition to track the history of parking activities. It records the `licensePlate`, `size` of the vehicle, `parkTime`, and `unparkTime`. `unparkTime` is `null` if the vehicle is still parked.

- **`ParkingLot` (Class):** The central management class. It maintains a `List` of `ParkingSlot` objects and a `List` of `ParkingEvent` objects. It encapsulates the core logic for:

  - Initializing slots with a balanced distribution (1/3 Small, 1/3 Large, remaining Oversize).

  - `parkVehicle(Vehicle)`: Finds the first suitable available slot.

  - `unparkVehicle(String licensePlate)`: Locates and vacates a slot based on the license plate, and updates the `ParkingEvent`.

  - `displayStatus()`: Shows current occupancy and available slots.

  - `hasParkedVehicles()`: Checks if any vehicle is currently parked.

  - `isSlotAvailableFor(ParkingSize)`: Checks if a slot exists for a given vehicle size.

- **`ParkingLotApp` (Main Class):** Contains the `main` method, handling user interaction via a `Scanner`, displaying the menu, and orchestrating calls to the `ParkingLot` instance. It also manages the report generation upon exit.

#### Data Handling

- **In-Memory Storage:** All application data (`ParkingSlot` states, `Vehicle` objects, and `ParkingEvent` history) is stored in `ArrayList`s, residing in the application's memory. This means data is lost when the application closes, unless explicitly saved to a file.

- **Report Generation:** A `parking_lot_report_DD-MM-YYYY.txt` file is generated upon application exit, providing a snapshot of the parking lot's current status and a comprehensive history of all parking and unparking events during the session.

## 2. Key Files and Folders

The application consists of a single Java file, which contains all necessary classes:

- `ParkingLotApp.java`: This is the main source file containing the `ParkingLotApp` class (which holds the `main` method), `ParkingLot`, `ParkingSlot`, `Vehicle`, `ParkingEvent`, and `ParkingSize` enum.

## 3. Process to Run, Test, and Verify

### Prerequisites

- Java Development Kit (JDK) 11 or newer installed on your system.

### How to Run

1. **Save the Code:**
   Save the provided Java code into a file named `ParkingLotApp.java`.

2. **Open a Terminal/Command Prompt:**
   Navigate to the directory where you saved `ParkingLotApp.java`.

3. **Compile the Code:**
   Use the Java compiler to compile the source file:

   ```bash
   javac ParkingLotApp.java
   ```

   ```bash
   java ParkingLotApp.java
   ```
