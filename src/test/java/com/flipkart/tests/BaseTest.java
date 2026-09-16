package com.flipkart.tests;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.flipkart.pages.BasePage;
import com.flipkart.utils.FileReadExcel;
import com.flipkart.utils.Screenshot;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;
// Requires the WebDriverManager dependency in pom.xml, e.g.:
// <dependency>
//     <groupId>io.github.bonigarcia</groupId>
//     <artifactId>webdrivermanager</artifactId>
//     <version>5.6.2</version>
// </dependency>

public class BaseTest {

	/* Base Test which is extended to all our Test Classes */

	public static WebDriver driver;

	// Config is now loaded once, in BasePage - reused here instead of
	// loading the same config.properties file a second time.
	static final java.util.Properties prop1 = BasePage.prop1;

	static final String RESOURCE_DIR = System.getProperty("user.dir") + File.separator + "Resource";
	static final String REPORT_DIR = System.getProperty("user.dir") + File.separator + "Reports";
	static FileReadExcel fileExcel;

	private static final Logger logger = LogManager.getLogger(BaseTest.class);

	public static ExtentReports extent;
	public static ExtentTest extentTest;

	@BeforeSuite(groups = { "Login", "Logout", "home", "profile", "cart", "wishlist", "product" })
	public void setExtent() {
		extent = new ExtentReports(REPORT_DIR + File.separator + "extentreport.html", true);
		logger.info("Extent Reporting is Initiated");
		fileExcel = new FileReadExcel(RESOURCE_DIR + File.separator + "TestCasesFile.xlsx");
	}

	@AfterSuite(groups = { "Login", "Logout", "home", "profile", "cart", "wishlist", "product" })
	public void endReport() {
		extent.flush();
		extent.close();
		logger.info("Extent Reporting is Finished");
	}

	@BeforeMethod(groups = { "Login", "Logout", "home", "profile", "cart", "wishlist", "product" })
	public static void initializeWebdriver() {
		String browser = prop1.getProperty("Browser");
		boolean headless = "true".equalsIgnoreCase(prop1.getProperty("headless"));

		if (browser == null) {
			throw new IllegalStateException("Browser property not set in config.properties");
		}

		if (browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			if (headless) {
				options.addArguments("--headless=new");
			}
			driver = new ChromeDriver(options);
			driver.manage().window().maximize();

		} else if (browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions options = new FirefoxOptions();
			if (headless) {
				options.addArguments("-headless");
			}
			driver = new FirefoxDriver(options);
			driver.manage().window().maximize();

		} else if (browser.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			driver.manage().window().maximize();
		} else {
			throw new IllegalArgumentException("Unsupported browser configured: " + browser);
		}
	}

	@BeforeMethod(groups = { "Login", "Logout", "home", "profile", "cart", "wishlist" })
	public static void navigateToFlipkart() {
		driver.get(prop1.getProperty("loginurl"));
	}

	@BeforeMethod(groups = { "product" })
	public static void navigateToFlipkartlogin() {
		driver.get(prop1.getProperty("login"));
	}

	@AfterMethod(groups = { "Login", "Logout", "home", "profile", "cart", "wishlist", "product" })
	public static void closebrowser() {
		if (driver != null) {
			driver.quit();
			logger.info("Webdriver is Closed");
		}
	}

	@AfterMethod(groups = { "Login", "Logout", "home", "profile", "cart", "wishlist", "product" })
	public void attachScreenshot(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			String screenshotPath = Screenshot.captureScreenshot(driver, result.getName());
			extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(screenshotPath));
			extent.endTest(extentTest);
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			extentTest.log(LogStatus.PASS, "Test Case Passed Successfully!!!");
		}
	}

}