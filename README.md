# Flight Booking System in Java

## Project Overview
The **Flight Booking System** simplifies the global flight booking process. Travelers can register accounts, search for available flights, make reservations, and manage bookings through an easy-to-use interface. Users can also alter or cancel reservations as needed, enhancing the passenger experience.

For airline staff, the system supports efficient management of schedules and reservations. It benefits airlines, travel agents, and passengers by improving customer satisfaction and optimizing resource use. The system prioritizes **data security**, **compatibility with airline databases**, and **performance** to handle varying user traffic, ensuring reliability and efficiency.

---

## Implementation

### 1. GUI Packages
- Developed using **Java Swing**.
- Includes windows, dialogs, buttons, and text fields for user interaction.
- Interfaces with the controller layer to initiate activities and capture user input.

### 2. Model Packages
- Contains core business logic and data structures.
- Classes for flights, bookings, and customers.
- Implements the application's data model with methods for data manipulation, validation, and persistence.

### 3. Controller Packages
- Bridges the **GUI** and **Model** layers.
- Manages user interactions, reads commands from the GUI, calls appropriate methods on the model layer, and updates the GUI with relevant data.

This modular structure promotes code reuse, improves readability, and facilitates maintenance and testing. The **MVC design** enhances communication among developers, enabling effective application development and refinement.

---

## Functionality
The **Flight Booking System** offers a range of features for both administrators and users:

### Admin Functionality
1. **Add**:
   - Administrators can add new flights and customer details to keep the database up to date.
2. **Delete**:
   - Administrators can remove flights, customers, and files to maintain data integrity.
3. **Search**:
   - A powerful search feature allows administrators to explore flights, customers, and files efficiently.

### User Functionality
4. **Flight Booking**:
   - Users can search for and book domestic and international flights, manage their reservations, and personalize their travel experiences.
5. **Manage Bookings**:
   - Users can alter or cancel their reservations, providing flexibility and control over their travel plans.

---

## Working Environment: IDE
The **Eclipse Integrated Development Environment (IDE)** was used to develop the Flight Booking System. 

### Why Eclipse?
- Customizable workspace with features like **syntax highlighting**, **code completion**, and **debugging tools**.
- Integration with version control systems like **Git** and **SVN** facilitates collaborative development and efficient project management.
- Robust debugging capabilities expedite issue resolution, optimizing the development lifecycle.

---

## Summary of Features
The Flight Booking System includes the following features:

1. **Customer Management**:
   - Adding, listing, and managing customer details.
2. **Flight Management**:
   - Adding, listing, and managing flight details.
3. **Booking System**:
   - Creating, canceling, and modifying bookings.
   - Displaying customer-specific bookings.
4. **GUI Implementation**:
   - Comprehensive graphical user interface for all operations.
5. **Data Management**:
   - Data persistence for flight and customer information.
6. **System Core**:
   - Core functionalities tying all features together.
7. **Error Handling**:
   - Custom exception handling to manage errors efficiently.
8. **Utilities**:
   - User assistance or documentation features.
9. **Passenger Management**:
   - Displaying passenger details for specific flights.

---

## Technologies Used
- **Java Swing**: GUI development for an intuitive user interface.
- **Java MVC Architecture**: Ensures modular and clean design.
- **Eclipse IDE**: Development environment with robust debugging tools.

---

## Conclusion
The **Flight Booking System** offers a streamlined solution for managing flight reservations, enhancing customer satisfaction, and optimizing airline operations. Its modular design, user-friendly interface, and robust functionality make it an effective tool for travelers, administrators, and airline staff.
