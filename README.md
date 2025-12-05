# Product Sale Management System (Java + GUI + Database Project)

## Overview
This project is a Java application that manages product sales and stores sales
information in a relational database.  
It demonstrates Object-Oriented Programming principles, GUI development with
Swing, database connectivity (JDBC), and exception handling.

The system allows:
  - Adding products to a database
  - Viewing all sales
  - Viewing item-based and weight-based products separately
  - Updating product prices
  - Calculating totals for different product types

---

## Features

### Product Management
Allows adding both **Item-Based** and **Weight-Based** products.  
Products are stored in a database table `tblproduct` inside `productsaledb`.

### Product Class
- Attributes include product category, price, total cost, and barcode.
- Includes constructors for:
  - Category input
  - Total cost input
- `toString()` method displays product details neatly.

### Data Access Class (ProductDA)
Handles database operations and exceptions:
- `getProductSalesDbConnection()` → connects to `productsaledb`
- `addProduct(Product objProduct)` → adds a product to the database
- `returnAll()` → returns all products in the database
- `calculateTotalWBP()` → calculates total of weight-based products
- `calculateTotalIBP()` → calculates total of item-based products
- `updatePrice(barcode, percentage)` → updates product prices
- `returnBarcodes()` → returns only product barcodes as a list

### GUI Application
The Java Swing form allows:
- Adding products based on selection:
  - **Item-Based Product**
  - **Weight-Based Product**
- Viewing all sales
- Viewing only item-based products
- Updating product prices by barcode

GUI dynamically interacts with the database and catches all exceptions.

---

## Technologies Used
- **Programming Language:** Java  
- **Framework:** Swing GUI / JDBC  
- **Database:** MySQL  
- **IDE:** NetBeans
- **JDBC Driver:** MySQL Connector/J

---

## Database Setup

1. **Create a Database:**
   ```sql
   CREATE DATABASE productsaledb;
   ```
2. **Use the Database:**
   ```sql
   USE productsaledb;
   ```
3. **Create Tables:**

   - **Product Table:**
     ```sql
     CREATE TABLE tblproduct (
        Barcode_No INT(8) PRIMARY KEY NOT NULL,
        Product_Name VARCHAR(18) NOT NULL,
        Product_Category VARCHAR(4) NOT NULL,
        Manufacturer VARCHAR(10),
        Weight INT(11),
        Unit_Price INT(11) NOT NULL,
        Amount_Paid DOUBLE NOT NULL
     );
     ```

---

## Usage Instructions
1. Create the database `productsaledb` and the table `tblproduct`.
2. Run the GUI form in NetBeans.
3. To **add a product**:
   - Select **Item-Based** or **Weight-Based**
   - Fill in required details
   - Click **Sale**
4. To **view all sales**:
   - Click **View Sales**
5. To **view item-based products only**:
   - Click **View Item Based**
6. To **update product prices**:
   - Click **Update Price**
   - Enter barcode and percentage increase in input boxes
7. All actions interact with the database automatically.

---

## Author
This project was developed as part of an academic exercise to learn Java OOP,
GUI development with Swing, JDBC database connectivity, and exception handling.
