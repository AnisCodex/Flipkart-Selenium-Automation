package com.flipkart.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

	WebDriver driver;

	// Cross-platform path (previously ".\\Resource\\config.properties",
	// which breaks on Mac/Linux/CI). This is now the single place that
	// loads config.properties - BaseTest.java reuses BasePage.prop1
	// instead of loading its own separate copy of the same file.
	public static final String CONFIG_PATH = System.getProperty("user.dir") + File.separator + "Resource"
			+ File.separator + "config.properties";

	static File file1 = new File(CONFIG_PATH);
	static FileInputStream fis1 = null;
	public static Properties prop1 = new Properties();

	static {
		try {
			fis1 = new FileInputStream(file1);
			prop1.load(fis1);
		} catch (FileNotFoundException e) {
			System.out.println("config.properties not found at " + file1.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public long IMPLITICIT_WAIT_5 = Long.valueOf(prop1.getProperty("implicitwait5"));
	public long IMPLITICIT_WAIT_10 = Long.valueOf(prop1.getProperty("implicitwait10"));
	public long IMPLITICIT_WAIT_15 = Long.valueOf(prop1.getProperty("implicitwait15"));
	public long IMPLITICIT_WAIT_20 = Long.valueOf(prop1.getProperty("implicitwait20"));

	public void waitForElementTobeClickable(WebElement webElement, WebDriver driver) {
		new WebDriverWait(driver, 14).until(ExpectedConditions.elementToBeClickable(webElement));
	}

	public void waitForElementToBeVisible(WebElement element, WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 14);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	/*
	 * Shared "hover over the logged-in username, click a menu item" flow.
	 * Previously copy-pasted with a hardcoded "pulkit" xpath in
	 * Address.gotoProfile(), FlipkartProfile.gotoProfile(), Logout.gotoProfile(),
	 * and Wishlist.gotToWishlist(). Centralized here and parameterized by the
	 * destination link text ("My Profile", "Wishlist", etc.).
	 *
	 * NOTE: the logged-in username is still hardcoded to "pulkit" to match
	 * the existing test account. Consider moving this into config.properties
	 * (e.g. prop1.getProperty("username")) so it isn't baked into code.
	 */
	public void hoverAndClickMenuItem(WebDriver driver, String menuItemText) {
		WebDriverWait wait = new WebDriverWait(driver, IMPLITICIT_WAIT_10);
		WebElement menu = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//div[@class='exehdJ'][normalize-space()='pulkit']")));
		Actions actions = new Actions(driver);
		actions.moveToElement(menu).perform();
		WebElement target = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//div[normalize-space()='" + menuItemText + "']")));
		actions.moveToElement(target);
		actions.click().build().perform();
	}

	/*
	 * Shared "a new tab/window opened, switch to it" flow. Previously
	 * copy-pasted in Cart.clickAddTocart(), Cart.verifySoldOut(),
	 * ProductDetails.checkimage(), and ProductDetails.changecolor().
	 */
	public void switchToNewWindow(WebDriver driver) {
		String currentHandle = driver.getWindowHandle();
		Set<String> handleSet = driver.getWindowHandles();
		for (String handle : handleSet) {
			if (!handle.equalsIgnoreCase(currentHandle)) {
				driver.switchTo().window(handle);
			}
		}
	}

}