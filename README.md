# Food Delivery JavaSwing OOP Project

A desktop application built with Java Swing, designed as a simplified food delivery system inspired by platforms like FoodPanda.  
**Note:** This is not a clone or replica of FoodPanda, but rather an educational project showcasing key Object-Oriented Programming (OOP) concepts—**inheritance, polymorphism, abstraction, and interfaces**—in a real-world use case.  
The UI is intentionally simple, as Java Swing was used due to instructor requirements.

---

## Features

### 🧑 User
- **Register & Login**
- **Browse Restaurants:** View all available restaurants and their menu items
- **Add to Cart:** Select food items and add to personal cart
- **Place Order:** Checkout and place orders

### 🍴 Restaurant
- **Register & Login**
- **Manage Menu:** Add, edit, or remove menu items (with image support from local desktop)
- **Order Handling:** View orders placed by users

### 👨‍💼 Admin
- **View & Manage Users:** See registered users, block or remove as needed
- **View & Manage Restaurants:** Oversee restaurant accounts and their menus
- **Order Oversight:** Review and manage all orders in the system

---

## OOP Concepts Demonstrated

- **Inheritance:** Shared and specialized behavior among Users, Restaurants, Admins, and Items
- **Polymorphism:** Unified item handling (`PizzaItem`, `DrinkItem`, etc.) and role-based actions
- **Abstraction:** Key classes expose only relevant behaviors to reduce complexity
- **Interfaces:** Used for common contracts (e.g., menu actions, order processing)

---

## Tech Stack

- Java (Core language)
- Java Swing (UI Framework)
- Binary File Storage (For users, items, restaurants, and orders)

---

## Project Structure
```graphql
├── README.md
├── food-delivery-project.iml
└── src/
    ├── RunFromHere.java
    ├── Classes/                # Core business logic (Cart, Order, User, Restaurant, etc.)
    ├── data/                   # Binary data storage for app state
    ├── dummyImages/            # Placeholder images for menus/items
    └── gui/
        ├── adminpanel/         # Admin interface and panels
        ├── helper/             # Helper and utility classes for GUI and file handling
        ├── postlogin/          # Post-login dashboards for restaurant & user
        └── startup/            # Login & registration windows
```



---

## How to Run

1. **Clone this repository:**

    ```bash
    git clone https://github.com/ateebakmal/FoodPanda-JavaSwing-OOP.git
    ```

2. **Open in your preferred Java IDE** (IntelliJ IDEA recommended).

3. **Build and Run**  
   Run the main entry point:

    ```
    src/RunFromHere.java
    ```

---

## Example Screenshots

UI is intentionally basic due to Java Swing limitations. Screenshots are for reference only.

<!-- 
If you have screenshots, add them here!
![Login Screen](screenshots/login.png)
![User Dashboard](screenshots/user-dashboard.png)
-->

---

## Limitations & Notes

- UI is intentionally minimal and may look outdated
- Java Swing was required by course/instructor
- This project is a demonstration for learning and academic purposes
- Not suitable for production or real-world deployment

---

## Acknowledgments

Built for the **Object Oriented Programming (OOP)** course project  

---

## License

This project is open-source and free to use for educational purposes.
