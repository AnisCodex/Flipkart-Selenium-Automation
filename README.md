Flipkart Selenium Automation

A Java-based Selenium WebDriver automation framework for testing key
Flipkart web application workflows using TestNG, Maven, Page Object
Model (POM), Apache POI, ExtentReports, Log4j2, and WebDriverManager.

Project Overview

This project automates functional scenarios across different areas of
the Flipkart application, including:

Home page

Login

Logout

Product search

Product sorting

Product filtering

Product details

Cart

Address management

Profile information

Wishlist

The framework uses Page Object Model to separate page-specific Selenium
interactions from test cases.

Technology Stack

Technology           Purpose

Java                 Programming language
Selenium WebDriver   Browser automation
TestNG               Test execution and assertions
Maven                Build and dependency management
Apache POI           Excel test-data handling
ExtentReports        HTML test reporting
Log4j2               Logging
WebDriverManager     Browser driver management
PageFactory          Page Object initialization

Project Structure

Flipkart/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/flipkart/
│   │   │       ├── pages/
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
│   │   │       └── utils/
│   │   │           ├── ExecutionRequired.java
│   │   │           ├── FileReadExcel.java
│   │   │           └── Screenshot.java
│   │   │
│   │   └── resources/
│   │
│   └── test/
│       ├── java/
│       │   └── com/flipkart/tests/
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
│       └── resources/
│
├── Drivers/
├── Resource/
│   ├── config.properties
│   └── TestCasesFile.xlsx
│
├── Reports/
├── FailedScreenshots/
├── pom.xml
└── testng.xml

The exact location of config.properties should match the path
configured in BasePage.java.

Framework Architecture

TestNG Test Classes
        |
        v
    BaseTest
        |
        +-------------------+
        |                   |
        v                   v
   Excel Data          WebDriver
        |                   |
        v                   v
 FileReadExcel        Page Objects
        |                   |
        v                   v
ExecutionRequired     Selenium UI
        |                   |
        +---------+---------+
                  |
                  v
              Assertions
                  |
        +---------+---------+
        |                   |
        v                   v
   ExtentReports       Screenshots

Page Object Layer

The com.flipkart.pages package contains the application's Page Object
classes.

BasePage

Provides common functionality such as:

Configuration property loading

Explicit waits

Profile/menu navigation

Switching to a new browser window

Login

Handles:

Entering mobile number

Entering password

Clicking Login

Verifying login name

Reading login error messages

HomePage

Handles:

Opening the Flipkart home page

Opening the Electronics dropdown

Verifying dropdown text

Verifying page title

Product

Handles:

Product search

Product result verification

Low-to-high sorting

High-to-low sorting

Brand filtering

Rating filtering

Brand verification

ProductDetails

Handles:

Opening a product

Checking product image visibility

Changing product color

Changing product size

Cart

Handles:

Searching for products

Opening a product

Adding a product to cart

Checking sold-out status

Checking cart count

Removing products

Opening the cart

Address

Handles:

Manage Addresses

Adding an address

Entering address details

Reading address validation errors

Navigating to profile

FlipkartProfile

Handles:

Opening profile

Changing name

Changing gender

Verifying name

Verifying gender

Wishlist

Handles:

Adding a product to wishlist

Opening wishlist

Reading wishlist products

Removing a wishlist item

Logout

Handles:

Opening the profile menu

Logging out

Verifying the logout/login message

Test Layer

The com.flipkart.tests package contains TestNG test classes.

The tests use the Page Objects to perform UI actions and then use TestNG
assertions to validate the expected results.

Examples include:

LoginTest

HomePageTest

ProductTest

ProductDetailsTest

CartTest

AddressTest

FlipkartProfileInfoTest

WishlistTest

LogoutTest

Test Data

The framework uses Apache POI through FileReadExcel.java to read test
data from Excel.

The tests retrieve data using the test-case name and worksheet name.

Example:

HashMap<String, String> testData =
        fileExcel.getRowTestData(
                testPageData,
                testName);

Test data is then accessed by column name:

testData.get("mob")
testData.get("pwd")
testData.get("search")

The Excel-based execution control is handled through
ExecutionRequired.

If the execution value is no, the corresponding TestNG test is
skipped.

Configuration

The framework uses a properties file for browser and application
configuration.

Example:

Browser=chrome

headless=false

loginurl=https://www.flipkart.com/

login=https://www.flipkart.com/account/login

chromeDriverProperty=webdriver.chrome.driver
chromeDriverPath=./Drivers/chromedriver.exe

firefoxDriverProperty=webdriver.gecko.driver
firefoxDriverPath=./Drivers/geckodriver.exe

edgeDriverProperty=webdriver.edge.driver
edgeDriverPath=./Drivers/msedgedriver.exe

implicitwait5=5
implicitwait10=10
implicitwait15=15
implicitwait20=20

Do not commit real passwords, authentication tokens, or other secrets to
the repository.

Browser Support

The framework is configured to support:

Chrome

Firefox

Edge

Browser selection is controlled through the Browser property.

The current BaseTest implementation uses WebDriverManager for browser
driver setup.

Running the Tests

Prerequisites

Install:

Java JDK

Maven

Eclipse or another Java IDE

Git

A supported browser

Verify Java:

java -version

Verify Maven:

mvn -version

Run with Maven

From the project root:

mvn clean test

Run using TestNG

The project contains:

testng.xml

You can run the suite from Eclipse by:

Right-click testng.xml

Select Run As

Select TestNG Suite

Reporting

The framework uses ExtentReports for execution reporting.

The report is generated under:

Reports/extentreport.html

Failed tests also use the screenshot utility to capture screenshots
under:

FailedScreenshots/

These generated folders should normally be excluded from Git using
.gitignore.

Logging

Log4j2 is used for test execution logging.

The test classes use Log4j2 Logger and LogManager for logging
execution information.

Screenshot Handling

Screenshot.java uses Selenium's TakesScreenshot interface.

A failed test screenshot is stored using the test name, for example:

FailedScreenshots/<testName>.jpg

Test Execution Flow

A typical test follows this flow:

TestNG starts
      |
      v
@BeforeSuite
      |
      v
Read configuration + initialize Excel
      |
      v
@BeforeMethod
      |
      v
Initialize WebDriver
      |
      v
Navigate to Flipkart
      |
      v
Read test data from Excel
      |
      v
Check ExecutionRequired
      |
      v
Create Page Object
      |
      v
Perform Selenium actions
      |
      v
Validate expected result
      |
      v
Capture execution result
      |
      v
Quit browser
      |
      v
@AfterSuite
      |
      v
Flush ExtentReports

GitHub Repository

Recommended repository name:

Flipkart-Selenium-Automation

Recommended description:

Flipkart web automation framework built with Java, Selenium, TestNG, Maven, POM, and Excel-driven test data.

.gitignore

Recommended .gitignore:

# Maven
target/

# Eclipse
.classpath
.project
.settings/

# Test reports
Reports/
FailedScreenshots/

# Logs
*.log

# OS files
.DS_Store
Thumbs.db

# Temporary files
*.tmp

Notes

The project follows the Page Object Model.

Test execution is managed using TestNG.

Excel test data is handled through Apache POI.

Browser setup is handled in BaseTest.

Common page operations are centralized in BasePage.

Generated reports and screenshots should not be committed to Git.

Keep credentials and other sensitive information outside the Git
repository.

Author

Flipkart Selenium Automation Project

Built using Java, Selenium WebDriver, TestNG, Maven, Apache POI,
ExtentReports, Log4j2, and WebDriverManager.
