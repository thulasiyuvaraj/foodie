# Foodie - Food Ordering Website (Java Full Stack)

A ready-to-run FSD assignment project built with **Java 17 + Spring Boot + Thymeleaf + H2 Database**.
It includes a food menu, search, add-to-cart, quantity updates, remove items, checkout, order confirmation, and order history.

## Requirements
- Java 17 or newer
- Maven 3.8+ (or open the folder in IntelliJ IDEA and import as a Maven project)

## Run the project
1. Extract `Foodie_FSD_Java_Project.zip`.
2. Open a terminal inside the `Foodie_FSD_Java_Project` folder.
3. Run:
   ```bash
   mvn spring-boot:run
   ```
4. Open `http://localhost:8081` in your browser (or override with `PORT=8080` if you want to use port 8080).

If using IntelliJ IDEA: open the folder, allow Maven to import dependencies, then run `src/main/java/com/foodie/FoodieApplication.java`.

## Features
- Responsive food ordering interface
- Search food by name/category
- Add food to cart
- Increase/decrease quantities and remove cart items
- Checkout form with customer name, phone, and address
- Order confirmation and order history
- H2 database (automatically created in memory for demo)
- Sample menu data seeded automatically

## Notes
- This is an educational/demo assignment. It does not process real payments or send real orders to restaurants.
- H2 database is in-memory, so data resets when the app restarts.
- H2 console: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:foodiedb`, username: `sa`, password: blank)
