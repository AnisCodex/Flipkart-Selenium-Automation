package com.flipkart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Logout extends BasePage {

	WebDriver driver;

	/*
	 * Logout Page for getting Locators like Logout and other input text
	 * fields
	 */

	public Logout(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	/* Declare web elements using different locators. */

	@FindBy(how = How.XPATH, using = "//span[@class='_16v3bB']")
	public WebElement logOut;

	@FindBy(how = How.CLASS_NAME, using = "_1_3w1N")
	public WebElement loginmsg;

	public void clickLogout() {
		logOut.click();
	}

	/*
	 * Various task Methods for performing the required task for executing Login
	 * Test Completely .
	 */

	/*
	 * NOTE ON THIS METHOD - please verify against the live site before trusting it:
	 *
	 * The original returned logOut.getText() - the same button clickLogout()
	 * just clicked. Since Selenium's @FindBy fields are re-located on every
	 * call (not cached here, no @CacheLookup), this might not literally throw
	 * a stale-element error, but it's still checking the clicked button's own
	 * label rather than any actual confirmation of logout succeeding - fragile
	 * and timing-dependent.
	 *
	 * This class already declares an unused "loginmsg" field
	 * (class "_1_3w1N") that was never referenced anywhere - its name suggests
	 * it's meant to be the actual post-logout confirmation message. I've wired
	 * verifyMsg() to use it instead, but I can't confirm what text it actually
	 * displays on the live Flipkart site (your LoginTest expected the literal
	 * string "Logout" against the old behavior) - please run this once and
	 * update the expected assertion string in LogoutTest.java if it differs.
	 */
	public String verifyMsg() {
		WebDriverWait wait = new WebDriverWait(driver, IMPLITICIT_WAIT_10);
		WebElement message = wait.until(ExpectedConditions.visibilityOf(loginmsg));
		return message.getText();
	}

	public void gotoProfile() {
		hoverAndClickMenuItem(driver, "My Profile");
	}

}