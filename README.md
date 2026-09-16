# Flipkart Selenium Automation

This project is a Selenium WebDriver automation framework developed using Java, Selenium, TestNG, Maven, Apache POI, ExtentReports, Log4j2, and WebDriverManager.

The project automates different functional areas of the Flipkart website using the Page Object Model (POM).

## Technologies Used

- Java
- Selenium WebDriver
- TestNG
- Maven
- Apache POI
- ExtentReports
- Log4j2
- WebDriverManager
- Page Object Model (POM)

## Project Features

The framework covers the following modules:

- Home Page
- Login
- Logout
- Product Search
- Product Sorting
- Product Filtering
- Product Details
- Cart
- Address Management
- Profile
- Wishlist

## Project Structure

```text
Flipkart
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.flipkart
│   │   │       ├── pages
│   │   │       │   ├── Address.java
│   │   │       │   ├── BasePage.java
│   │   │       │   ├── Cart.java
│   │   │       │   ├── FlipkartProfile.java
│   │   │       │   ├── HomePage.java
│   │   │       │   ├── Login.java
│   │   │       │   ├── Logout.java
│   │   │       │   ├── Product.java
│   │   │       │   ├── ProductDetails.java
│   │   │       │   └── Wishlist.java
│   │   │       │
│   │   │       └── utils
│   │   │           ├── ExecutionRequired.java
│   │   │           ├── FileReadExcel.java
│   │   │           └── Screenshot.java
│   │
│   └── test
│       ├── java
│       │   └── com.flipkart.tests
│       │       ├── AddressTest.java
│       │       ├── BaseTest.java
│       │       ├── CartTest.java
│       │       ├── FlipkartProfileInfoTest.java
│       │       ├── HomePageTest.java
│       │       ├── LoginTest.java
│       │       ├── LogoutTest.java
│       │       ├── ProductDetailsTest.java
│       │       ├── ProductTest.java
│       │       ├── SearchTest.java
│       │       └── WishlistTest.java
│       │
│       └── resources
│
├── Resource
│   ├── config.properties
│   └── TestCasesFile.xlsx
│
├── Drivers
├── Reports
├── FailedScreenshots
├── pom.xml
└── testng.xml
