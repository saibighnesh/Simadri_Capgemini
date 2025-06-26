# Parking Lot Management System

## 🚗 Approach and Design

### Problem Statement
The application addresses efficient parking lot management for 'N' customizable slots, divided into Small, Large, and Oversize categories. It handles vehicle entry/exit based on size compatibility and provides real-time status and historical reporting. Data is kept in memory.

### Design
An Object-Oriented approach uses classes like `ParkingLotApp` (main controller), `ParkingLot` (core logic for slots, vehicles, and history), `ParkingSlot` (individual spaces), `Vehicle` (car details), and `ParkingEvent` (to log park/unpark timestamps). `ParkingSize` enum defines sizing and compatibility. Data is stored in `ArrayList`s during runtime.

## Key files and folders

The project is contained within a single file:

* `ParkingLotApp.java`: Contains all Java classes (`ParkingLotApp`, `ParkingLot`, `ParkingSlot`, `Vehicle`, `ParkingEvent`, `ParkingSize` enum) for the application.

## Process to run, test, and verify.

### Prerequisites
* Java Development Kit (JDK) 11 or newer.

### How to Run

1.  **Save:** Save the code as `ParkingLotApp.java`.
2.  **Compile:** Open a terminal and run `javac ParkingLotApp.java`.
3.  **Run:** Execute the compiled application with `java ParkingLotApp`.

### User Interaction
The application is command-line based.
1.  **Initialization:** Enter the total number of slots (N).
2.  **Menu Options:** Choose to `Park Vehicle`, `Unpark Vehicle`, `Display Parking Status`, or `Exit`.
    * **Park:** Select vehicle size (1-3); if space is available, enter license plate.
    * **Unpark:** Checks if cars are parked; if so, enter license plate.
    * **Status:** Displays current slot occupancy.
3.  **Exit:** Selecting option `4` generates a `parking_lot_report_DD-MM-YYYY.txt` file and closes the application.

## Include test data and seed data as required

### Seed Data

The primary "seed data" is the initial number of parking slots (N) provided by the user at application startup.

* **Total Number of Slots (N):** A positive integer to define the parking lot capacity.
    * **Example Seed Value:** `N = 3` (Creates 1 Small, 1 Large, 1 Oversize slot).

### Test Data (Example Scenario for Verification)

Follow these steps to test the application's core functionalities:

**Scenario: Basic Parking Lot Operations**

**Initial Setup:**
* Start the application: `java ParkingLotApp`
* Input `3` for total slots.

**Test Steps & Expected Outcomes:**

1.  **Park Small Vehicle (ABC-111):**
    * **Action:** Select `1` (Park), `1` (SMALL), Enter `ABC-111`.
    * **Expected:** `Vehicle ABC-111 parked in Slot ID: 1 (Size: SMALL)`

2.  **Park Large Vehicle (DEF-222):**
    * **Action:** Select `1` (Park), `2` (LARGE), Enter `DEF-222`.
    * **Expected:** `Vehicle DEF-222 parked in Slot ID: 2 (Size: LARGE)`

3.  **Attempt to Park another Small Vehicle:**
    * **Action:** Select `1` (Park), `1` (SMALL).
    * **Expected:** `No space available for SMALL vehicles.`

4.  **Park Oversize Vehicle (GHI-333):**
    * **Action:** Select `1` (Park), `3` (OVERSIZE), Enter `GHI-333`.
    * **Expected:** `Vehicle GHI-333 parked in Slot ID: 3 (Size: OVERSIZE)`

5.  **Display Status:**
    * **Action:** Select `3` (Display Status).
    * **Expected:** All slots show as `OCCUPIED` by `ABC-111`, `DEF-222`, `GHI-333` respectively.

6.  **Unpark Vehicle (DEF-222):**
    * **Action:** Select `2` (Unpark), Enter `DEF-222`.
    * **Expected:** `Vehicle DEF-222 unparked from Slot ID: 2`

7.  **Attempt to Unpark Non-Existent Vehicle:**
    * **Action:** Select `2` (Unpark), Enter `XYZ-999`.
    * **Expected:** `No vehicle found with that license plate.`

8.  **Display Status (After Unparking):**
    * **Action:** Select `3` (Display Status).
    * **Expected:** Slot 2 (`LARGE`) shows `AVAILABLE`.

9.  **Exit Application & Verify Report:**
    * **Action:** Select `4` (Exit).
    * **Expected:** `parking_lot_report_DD-MM-YYYY.txt` file generated.
    * **Verification:** Open the file to confirm:
        * Correct Slot Distribution.
        * Current Slot Status (Slot 1 `ABC-111`, Slot 2 `AVAILABLE`, Slot 3 `GHI-333`).
        * **Parking History** includes `ABC-111` (parked), `DEF-222` (parked and unparked), and `GHI-333` (parked).
