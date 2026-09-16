package com.flipkart.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.flipkart.pages.Product;
import com.flipkart.utils.ExecutionRequired;
import com.relevantcodes.extentreports.LogStatus;

public class ProductTest extends BaseTest {

	/* Initializing Logger */

	private static Logger logger = LogManager.getLogger(ProductTest.class);
	private static String testPageData = "ProductTestData";

	@Test(groups = { "product" }, priority = 22, enabled = true)
	public void searchProduct() {
		String testName = "searchProduct";
		HashMap<String, String> testData = fileExcel.getRowTestData(testPageData, testName);
		ExecutionRequired.checkExecutionRequired(testData.get("Execution Required"));
		extentTest = extent.startTest("Verify that searching a keyword returns matching products");
		logger.info("Test :: Search Product Test Case Started");
		Product product = new Product(driver);
		String keyword = testData.get("keyword");
		product.search(keyword);
		int count = product.getResults(keyword);
		assertTrue(count > 0, "Expected at least one product matching '" + keyword + "'");
		extentTest.log(LogStatus.PASS, testName + " Test has Passed");
		extent.endTest(extentTest);
		logger.info("Test :: Search Product Test Case Ended");
	}

	@Test(groups = { "product" }, priority = 23, enabled = true)
	public void sortProductLowToHigh() {
		String testName = "sortProductLowToHigh";
		HashMap<String, String> testData = fileExcel.getRowTestData(testPageData, testName);
		ExecutionRequired.checkExecutionRequired(testData.get("Execution Required"));
		extentTest = extent.startTest("Verify sorting products by Price -- Low to High");
		logger.info("Test :: Sort Product Low to High Test Case Started");
		Product product = new Product(driver);
		product.search(testData.get("keyword"));
		product.sortLToH();
		assertEquals(product.verifySort(), "ASC");
		extentTest.log(LogStatus.PASS, testName + " Test has Passed");
		extent.endTest(extentTest);
		logger.info("Test :: Sort Product Low to High Test Case Ended");
	}

	@Test(groups = { "product" }, priority = 24, enabled = true)
	public void sortProductHighToLow() {
		String testName = "sortProductHighToLow";
		HashMap<String, String> testData = fileExcel.getRowTestData(testPageData, testName);
		ExecutionRequired.checkExecutionRequired(testData.get("Execution Required"));
		extentTest = extent.startTest("Verify sorting products by Price -- High to Low");
		logger.info("Test :: Sort Product High to Low Test Case Started");
		Product product = new Product(driver);
		product.search(testData.get("keyword"));
		product.sortHToL();
		assertEquals(product.verifySort(), "DESC");
		extentTest.log(LogStatus.PASS, testName + " Test has Passed");
		extent.endTest(extentTest);
		logger.info("Test :: Sort Product High to Low Test Case Ended");
	}

	@Test(groups = { "product" }, priority = 25, enabled = true)
	public void filterProductByBrand() {
		String testName = "filterProductByBrand";
		HashMap<String, String> testData = fileExcel.getRowTestData(testPageData, testName);
		ExecutionRequired.checkExecutionRequired(testData.get("Execution Required"));
		extentTest = extent.startTest("Verify filtering products by Brand (Apple)");
		logger.info("Test :: Filter Product By Brand Test Case Started");
		Product product = new Product(driver);
		product.search(testData.get("keyword"));
		product.addbrand(testData.get("brand"));
		assertTrue(product.verifyBrand(testData.get("brand").toLowerCase()),
				"Expected results to be filtered to brand: " + testData.get("brand"));
		extentTest.log(LogStatus.PASS, testName + " Test has Passed");
		extent.endTest(extentTest);
		logger.info("Test :: Filter Product By Brand Test Case Ended");
	}

}