package com.flipkart.utils;

import org.testng.SkipException;

public class ExecutionRequired {

	/*
	 * Function to check the "Execution Required" field for the particular
	 * Test in the Excel Sheet - if "no", skip the test, else proceed.
	 *
	 * Fixed: the original called value.toLowerCase() before checking, which
	 * throws a NullPointerException if the Excel cell for that test row is
	 * blank/missing - a much less clear failure than a real assertion error.
	 * Also removed the redundant toLowerCase() before equalsIgnoreCase(),
	 * which already ignores case on its own.
	 */
	public static void checkExecutionRequired(String value) {
		if (value == null) {
			return; // no "Execution Required" value present - default to running the test
		}
		if (value.equalsIgnoreCase("no")) {
			throw new SkipException("Skipping this Test Case as The Execution is Not Required");
		}
	}

}