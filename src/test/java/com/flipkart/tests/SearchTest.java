package com.flipkart.tests;

import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.flipkart.pages.Product;
import com.relevantcodes.extentreports.LogStatus;

/*
 * NOTE: The original version of this file imported "pages.HomePage" and
 * "pages.SearchResultsPage", neither of which exists in this project.
 * This project's real HomePage (com.flipkart.pages.HomePage) only has
 * clickdropdown()/verifydropdown()/verifyTitle(), and there is no
 * SearchResultsPage class at all - so the original would not compile.
 *
 * Rewritten here to use the Product page object (com.flipkart.pages.Product),
 * which already has search() and getResults() methods, and to follow this
 * project's conventions (BaseTest, "product" group so the driver is
 * initialized/quit correctly, ExtentReports, Log4j2).
 */
public class SearchTest extends BaseTest {

	private static Logger logger = LogManager.getLogger(SearchTest.class);

	@Test(groups = { "product" }, priority = 26, enabled = true)
	public void searchForProductAndVerifyResults() {
		String keyword = "laptop";
		logger.info("Test :: Search For Product And Verify Results Test Case Started");
		extentTest = extent.startTest("Verify searching a product returns at least one result");

		Product product = new Product(driver);
		product.search(keyword);
		int count = product.getResults(keyword);
		logger.info("Number of matching products shown: " + count);

		assertTrue(count > 0, "Expected at least 1 product in search results, found 0");
		extentTest.log(LogStatus.PASS, "searchForProductAndVerifyResults Test has Passed");
		extent.endTest(extentTest);
		logger.info("Test :: Search For Product And Verify Results Test Case Ended");
	}
}