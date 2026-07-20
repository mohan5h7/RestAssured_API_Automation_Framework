package validators;

import reporting.ExtentTestManager;

import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import logging.LoggerManager;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Generic Field Validation Utility.
 *
 * Contains reusable validation methods that can be used across all APIs.
 */
public final class FieldValidator {

	private static final Logger LOGGER = LoggerManager.getLogger(FieldValidator.class);

	private FieldValidator() {
	}

	/**
	 * Validate equality.
	 */
	public static void validateEquals(Object actual, Object expected, String fieldName) {

		Assert.assertEquals(actual, expected, "Mismatch in field : " + fieldName);

		LOGGER.info("Field Validation Passed : {}", fieldName);

		ExtentTestManager.pass("Field Validation Passed : " + fieldName);

	}

	/**
	 * Validate inequality.
	 */
	public static void validateNotEquals(Object actual, Object expected, String fieldName) {

		Assert.assertNotEquals(actual, expected, "Unexpected value in field : " + fieldName);

		LOGGER.info("Field Not Equals Validation Passed : {}", fieldName);

		ExtentTestManager.pass("Field Not Equals Validation Passed : " + fieldName);

	}

	/**
	 * Validate null.
	 */
	public static void validateNull(Object actual, String fieldName) {

		Assert.assertNull(actual, fieldName + " should be null");

		LOGGER.info("Null Validation Passed : {}", fieldName);

		ExtentTestManager.pass("Null Validation Passed : " + fieldName);

	}

	/**
	 * Validate not null.
	 */
	public static void validateNotNull(Object actual, String fieldName) {

		Assert.assertNotNull(actual, fieldName + " should not be null");

		LOGGER.info("Not Null Validation Passed : {}", fieldName);

		ExtentTestManager.pass("Not Null Validation Passed : " + fieldName);

	}

	/**
	 * Validate boolean true.
	 */
	public static void validateTrue(boolean condition, String message) {

		Assert.assertTrue(condition, message);

		LOGGER.info("Boolean TRUE Validation Passed");

		ExtentTestManager.pass("Boolean TRUE Validation Passed");

	}

	/**
	 * Validate boolean false.
	 */
	public static void validateFalse(boolean condition, String message) {

		Assert.assertFalse(condition, message);

		LOGGER.info("Boolean FALSE Validation Passed");

		ExtentTestManager.pass("Boolean FALSE Validation Passed");

	}

	/**
	 * Validate String contains.
	 */
	public static void validateContains(String actual, String expected, String fieldName) {

		validateNotNull(actual, fieldName);

		Assert.assertTrue(actual.contains(expected), fieldName + " does not contain : " + expected);

		LOGGER.info("Contains Validation Passed : {}", fieldName);

		ExtentTestManager.pass("Contains Validation Passed : " + fieldName);

	}

	/**
	 * Validate regex.
	 */
	public static void validateMatches(String actual, String regex, String fieldName) {

		validateNotNull(actual, fieldName);

		Assert.assertTrue(Pattern.matches(regex, actual), fieldName + " does not match regex");

		LOGGER.info("Regex Validation Passed : {}", fieldName);

		ExtentTestManager.pass("Regex Validation Passed : " + fieldName);

	}

	/**
	 * Validate collection size.
	 */
	public static void validateSize(Collection<?> collection, int expectedSize, String fieldName) {

		validateNotNull(collection, fieldName);

		Assert.assertEquals(collection.size(), expectedSize, fieldName + " size mismatch");

		LOGGER.info("Collection Size Validation Passed : {}", fieldName);

		ExtentTestManager.pass("Collection Size Validation Passed : " + fieldName);

	}

	/**
	 * Validate map size.
	 */
	public static void validateSize(Map<?, ?> map, int expectedSize, String fieldName) {

		validateNotNull(map, fieldName);

		Assert.assertEquals(map.size(), expectedSize, fieldName + " size mismatch");

		LOGGER.info("Map Size Validation Passed : {}", fieldName);

		ExtentTestManager.pass("Map Size Validation Passed : " + fieldName);

	}

	/**
	 * Validate empty collection.
	 */
	public static void validateEmpty(Collection<?> collection, String fieldName) {

		validateNotNull(collection, fieldName);

		Assert.assertTrue(collection.isEmpty(), fieldName + " should be empty");

		LOGGER.info("Empty Collection Validation Passed : {}", fieldName);

		ExtentTestManager.pass("Empty Collection Validation Passed : " + fieldName);

	}

	/**
	 * Validate not empty collection.
	 */
	public static void validateNotEmpty(Collection<?> collection, String fieldName) {

		validateNotNull(collection, fieldName);

		Assert.assertFalse(collection.isEmpty(), fieldName + " should not be empty");

		LOGGER.info("Not Empty Collection Validation Passed : {}", fieldName);

		ExtentTestManager.pass("Not Empty Collection Validation Passed : " + fieldName);

	}

	/**
	 * Validate object identity.
	 */
	public static void validateSame(Object actual, Object expected, String fieldName) {

		Assert.assertTrue(actual == expected, fieldName + " references are different");

		LOGGER.info("Object Identity Validation Passed : {}", fieldName);

		ExtentTestManager.pass("Object Identity Validation Passed : " + fieldName);

	}

	/**
	 * Validate object equality using equals().
	 */
	public static void validateObject(Object actual, Object expected, String fieldName) {

		Assert.assertTrue(Objects.equals(actual, expected), "Mismatch in field : " + fieldName);

		LOGGER.info("Object Equality Validation Passed : {}", fieldName);

		ExtentTestManager.pass("Object Equality Validation Passed : " + fieldName);

	}

}