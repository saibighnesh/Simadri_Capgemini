# Parking Lot Management System

## 🚗 Your Approach and Design

This project implements a **Parking Lot Management System** in Java. It simulates real-world parking lot functionality through a command-driven interface. The system can handle the following operations:

- Creating a parking lot
- Parking a car
- Leaving a slot
- Displaying status
- Querying by car color or registration number

### Design Highlights:

- **OOP Design**: Encapsulates parking lot, slot, and car logic using classes.
- **Slot Allocation Strategy**: Assigns the lowest available slot number to incoming cars.
- **Command Interface**: Accepts input via standard input or file redirection to simulate commands.

---

## 📂 Key Files and Folders

- `ParkingLotApp.java`: Main Java class containing:
  - Parking lot creation logic
  - Command parsing and dispatching
  - Slot tracking and car registry

> Note: The project currently consists of a single file. Refactoring into separate classes (e.g., `ParkingLot`, `Vehicle`, `SlotManager`) can be done for scalability.

---

## 🧪 Process to Run, Test, and Verify

### 🔧 Requirements

- Java Development Kit (JDK) 8 or higher
- Terminal or Command Prompt

### ▶️ To Compile and Run:

````bash
javac ParkingLotApp.java
java ParkingLotApp


## Include test data and seed data as required

### Seed Data

For this application, the primary "seed data" is the initial configuration of the parking lot itself, provided at the start of the application:

* **Total Number of Slots (N):** This is the only "seed" input required from the user to initialize the parking lot.

    * **Example Seed Value:** `N = 3` (This will typically create 1 Small, 1 Large, and 1 Oversize slot based on the current application logic).

    * You can choose any positive integer for `N`. A value divisible by 3 (like 3, 6, 9, 12) will ensure an even distribution.

### Test Data (Example Scenario for Verification)

The "Test Data" consists of the sequence of user inputs and the expected outputs/behaviors to verify that the application's features work as intended.

**Scenario: Basic Parking Lot Operations and Report Generation**

**Initial Setup (Seed Data):**

* Start the application: `java ParkingLotApp`

* When prompted: `Enter the total number of parking slots (N):`

    * **Input:** `3`

**Test Steps & Expected Outcomes:**

**Step 1: Park a Small Vehicle**

* **Action:** Select option `1` (Park Vehicle)

* **Action:** Select vehicle size `1` (SMALL)

* **Action:** Enter license plate `ABC-111`

* **Expected Output:** `Vehicle ABC-111 parked in Slot ID: 1 (Size: SMALL)`

* **Action:** Press Enter to continue.

**Step 2: Park a Large Vehicle**

* **Action:** Select option `1` (Park Vehicle)

* **Action:** Select vehicle size `2` (LARGE)

* *Verification (Internal):* Application checks for available LARGE or OVERSIZE slots.

* **Action:** Enter license plate `DEF-222`

* **Expected Output:** `Vehicle DEF-222 parked in Slot ID: 2 (Size: LARGE)`

* **Action:** Press Enter to continue.

**Step 3: Try to Park another Small Vehicle (No Space Available)**

* **Action:** Select option `1` (Park Vehicle)

* **Action:** Select vehicle size `1` (SMALL)

* *Verification (Internal):* Application checks for available SMALL, LARGE, or OVERSIZE slots that can accommodate a SMALL vehicle. Slot 1 (SMALL) is occupied, Slot 2 (LARGE) is occupied. Only Slot 3 (OVERSIZE) is available, but the `isSlotAvailableFor` logic will correctly return `false` if `N/3` logic filled specific types.

* **Expected Output:** `No space available for SMALL vehicles.`

* **Action:** Press Enter to continue.

**Step 4: Park an Oversize Vehicle**

* **Action:** Select option `1` (Park Vehicle)

* **Action:** Select vehicle size `3` (OVERSIZE)

* *Verification (Internal):* Application checks for available OVERSIZE slots. Slot 3 is available.

* **Action:** Enter license plate `GHI-333`

* **Expected Output:** `Vehicle GHI-333 parked in Slot ID: 3 (Size: OVERSIZE)`

* **Action:** Press Enter to continue.

**Step 5: Display Current Parking Status**

* **Action:** Select option `3` (Display Parking Status)

* **Expected Output:**

    ```
    --- Parking Lot Status ---
    Available Slots:
      Small: 0
      Large: 0
      Oversize: 0
    --------------------------
    Slot ID: 1, Size: SMALL, Status: OCCUPIED by ABC-111
    Slot ID: 2, Size: LARGE, Status: OCCUPIED by DEF-222
    Slot ID: 3, Size: OVERSIZE, Status: OCCUPIED by GHI-333
    --------------------------
    ```

* **Action:** Press Enter to continue.

**Step 6: Unpark a Vehicle**

* **Action:** Select option `2` (Unpark Vehicle)

* **Action:** Enter license plate `DEF-222`

* **Expected Output:** `Vehicle DEF-222 unparked from Slot ID: 2`

* **Action:** Press Enter to continue.

**Step 7: Try to Unpark a Non-Existent Vehicle**

* **Action:** Select option `2` (Unpark Vehicle)

* **Action:** Enter license plate `XYZ-999`

* **Expected Output:** `No vehicle found with that license plate.`

* **Action:** Press Enter to continue.

**Step 8: Display Status After Unparking**

* **Action:** Select option `3` (Display Parking Status)

* **Expected Output:**

    ```
    --- Parking Lot Status ---
    Available Slots:
      Small: 0
      Large: 1  (Slot 2 is now available)
      Oversize: 0
    --------------------------
    Slot ID: 1, Size: SMALL, Status: OCCUPIED by ABC-111
    Slot ID: 2, Size: LARGE, Status: AVAILABLE
    Slot ID: 3, Size: OVERSIZE, Status: OCCUPIED by GHI-333
    --------------------------
    ```

* **Action:** Press Enter to continue.

**Step 9: Exit Application and Verify Report File**

* **Action:** Select option `4` (Exit)

* **Expected Output:** `Exiting application. Generating report...` and `Parking lot report successfully generated to parking_lot_report_DD-MM-YYYY.txt`

* **Verification:**

    * Locate the `parking_lot_report_DD-MM-YYYY.txt` file (where DD-MM-YYYY is the current date) in the same directory where you ran the Java application.

    * Open the file in a text editor.

    * **Expected Report File Content:**

        ```
        --- Parking Lot Report ---
        Report Generated: [Current Date and Time, e.g., 2025-06-26 16:20:58]
        --------------------------
        Total Parking Slots: 3
        Slot Distribution:
          Small Slots: 1
          Large Slots: 1
          Oversize Slots: 1
        --------------------------
        Current Slot Status:
        Slot ID: 1, Size: SMALL, Status: OCCUPIED by ABC-111 (Size: SMALL)
        Slot ID: 2, Size: LARGE, Status: AVAILABLE
        Slot ID: 3, Size: OVERSIZE, Status: OCCUPIED by GHI-333 (Size: OVERSIZE)
        --------------------------

        --- Parking History ---
          License Plate: ABC-111, Size: SMALL, Parked: [Timestamp], Unparked: N/A (Still Parked)
          License Plate: DEF-222, Size: LARGE, Parked: [Timestamp], Unparked: [Timestamp]
          License Plate: GHI-333, Size: OVERSIZE, Parked: [Timestamp], Unparked: N/A (Still Parked)
        --------------------------
        ```

        *(Note: `[Timestamp]` will be the actual date and time of the event.)*
````
