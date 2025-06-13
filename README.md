# Food Delivery JavaSwing OOP Project

A desktop application built with Java Swing, designed as a simplified food delivery system inspired by platforms like FoodPanda.  

> **Note:** This is not an exact replica of FoodPanda, but rather an educational project inspired by multiple real-world food delivery platforms.  
> The goal is to demonstrate Object-Oriented Programming (OOP) concepts in a realistic application context.  
> The user interface is intentionally minimal and may appear outdated due to the limitations of Java Swing and the requirement for the project to be built using native Java.


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

### 🧾 Order History
- **Track All Orders:** View a complete history of all orders in the system  
- **Retrieve by Username:** Find order history for any specific user  
- **Retrieve by Restaurant:** Access all orders associated with a particular restaurant
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
<h2></h2>
<p align="center">
  <img src="screenshots/Screenshot 2025-06-13 203118.png" alt="Login Screen" width="400"/>
</p>


<h2>User</h2>
<ul>
    <li> 
        <h4>Login Page</h4>
        <p align="center">
          <img src="screenshots/Screenshot 2025-06-13 203136.png" alt="Login Screen" width="400"/>
        </p>   
    </li>
        <li> 
        <h4>Food ordering page</h4>
        <p align="center">
          <img src="screenshots/Screenshot 2025-06-13 203232.png" alt="Login Screen" width="400"/>
        </p>  
    </li>
    <li>
        <h4>User can add or remove items to his cart.</h4>
    </li>
    <li>
        <h4>User can select any number of items he desire to checkout or find Total.</h4>
    </li>
</ul>



<h2>Restaurant Side</h2>

<ul>
    <li>
        <h4>Dashboard</h4>
        <p align="center">
          <img src="screenshots/Screenshot 2025-06-13 203324.png" alt="Login Screen" width="400"/>
        </p>        
    </li>
    <li>
        <h4>Restaurant - Add Item</h4>
        <p align="center">
          <img src="screenshots/Screenshot 2025-06-13 203539.png" alt="Login Screen" width="400"/>
        </p>
    </li>
    <li>
        <h4>Similarly, Restaurants can remove or edit items as well</h4>
    </li>
</ul>

<h2>Admin Panel</h2>
<ul>
    <li>
        <h4>Admin Page: Admin can see all the restaurants, user. Remove them or edit their information</h4>
         <p align="center">
          <img src="screenshots/Screenshot 2025-06-13 203936.png" width="400"/>
        </p>
    </li>
    <li>
        <h4>Restaurant Info: Admin can see details of a specific restaurant: Add , edit or remove items</h4>
         <p align="center">
          <img src="screenshots/Screenshot 2025-06-13 203951.png" width="400"/>
        </p>
    </li>
    <li>
        <h4>Order History: Admin can see all the orders history</h4>
    </li>
</ul>
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
