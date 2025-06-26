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
````

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
