package com.flipkart.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Screenshot {

	/*
	 * captureScreenshot function for capturing the screenshot whenever a Test
	 * fails.
	 *
	 * Fixed: the original hardcoded "\\FailedScreenshots\\" (Windows-only
	 * path separators), which breaks on Mac/Linux/CI. Now uses File.separator
	 * to build a cross-platform path, matching the fix already applied in
	 * BaseTest/BasePage.
	 */
	public static String captureScreenshot(WebDriver driver, String testName) {
		System.out.println(testName);
		String screenshotPath = System.getProperty("user.dir") + File.separator + "FailedScreenshots"
				+ File.separator + testName + ".jpg";

		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(srcFile, new File(screenshotPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return screenshotPath;
	}
}