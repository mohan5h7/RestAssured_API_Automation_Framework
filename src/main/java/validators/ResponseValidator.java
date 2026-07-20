package validators;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import logging.LoggerManager;
import reporting.ExtentTestManager;

import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public final class ResponseValidator {

	private static final Logger LOGGER = LoggerManager.getLogger(ResponseValidator.class);

	private ResponseValidator() {
	}

	public static void validateStatusCode(Response response, int expectedStatusCode) {

		Assert.assertNotNull(response, "Response cannot be null");

		Assert.assertEquals(response.getStatusCode(), expectedStatusCode, "Status code mismatch");

		LOGGER.info("Status Code Validation Passed. Expected: {}, Actual: {}", expectedStatusCode,
				response.getStatusCode());

		ExtentTestManager.pass("Status Code Validation Passed. Expected: " + expectedStatusCode + ", Actual: "
				+ response.getStatusCode());
	}

	public static void validateResponseTime(Response response, long maxResponseTime) {

		Assert.assertNotNull(response, "Response cannot be null");

		Assert.assertTrue(response.getTime() <= maxResponseTime, "Response time exceeded : " + response.getTime());

		LOGGER.info("Response Time Validation Passed. Time: {} ms", response.getTime());

		ExtentTestManager.pass("Response Time Validation Passed. Time: " + response.getTime() + " ms");
	}

	public static void validateSchema(Response response, String schemaPath) {

		Assert.assertNotNull(response, "Response cannot be null");

		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File(schemaPath)));

		LOGGER.info("Schema Validation Passed : {}", schemaPath);

		ExtentTestManager.pass("Schema Validation Passed : " + schemaPath);
	}

	/**
	 * Validate Complete POJO.
	 */
	public static void validatePojo(Object actual, Object expected) {

		ReflectionComparator.compare(actual, expected);

		LOGGER.info("POJO Validation Passed");

		ExtentTestManager.pass("POJO Validation Passed");
	}

	/**
	 * Validate POJO with ignored fields.
	 */
	public static void validatePojo(Object actual, Object expected, Set<String> ignoreFields) {

		ReflectionComparator.compare(actual, expected, ignoreFields);

		LOGGER.info("POJO Validation Passed. Ignored Fields : {}", ignoreFields);

		ExtentTestManager.pass("POJO Validation Passed. Ignored Fields : " + ignoreFields);
	}

	/**
	 * Validate Single Field.
	 */
	public static void validateField(Object actual, Object expected, String fieldName) {

		FieldValidator.validateEquals(actual, expected, fieldName);

		LOGGER.info("Field Validation Passed : {}", fieldName);

		ExtentTestManager.pass("Field Validation Passed : " + fieldName);
	}

	/**
	 * Validate JSONPath value.
	 */
	public static void validateJsonPath(Response response, String jsonPath, Object expected) {

		JsonPathValidator.validateValue(response, jsonPath, expected);

		LOGGER.info("JsonPath Validation Passed : {}", jsonPath);

		ExtentTestManager.pass("JsonPath Validation Passed : " + jsonPath);
	}

	/**
	 * Validate JSONPath not null.
	 */
	public static void validateJsonPathNotNull(Response response, String jsonPath) {

		JsonPathValidator.validateNotNull(response, jsonPath);

		LOGGER.info("JsonPath Not Null Validation Passed : {}", jsonPath);

		ExtentTestManager.pass("JsonPath Not Null Validation Passed : " + jsonPath);
	}

	/**
	 * Validate Collection Size.
	 */
	public static void validateCollectionSize(Collection<?> collection, int expectedSize, String fieldName) {

		FieldValidator.validateSize(collection, expectedSize, fieldName);

		LOGGER.info("Collection Size Validation Passed : {}", fieldName);

		ExtentTestManager.pass("Collection Size Validation Passed : " + fieldName);
	}

	/**
	 * Validate Map Size.
	 */
	public static void validateMapSize(Map<?, ?> map, int expectedSize, String fieldName) {

		FieldValidator.validateSize(map, expectedSize, fieldName);

		LOGGER.info("Map Size Validation Passed : {}", fieldName);

		ExtentTestManager.pass("Map Size Validation Passed : " + fieldName);
	}

	/**
	 * Validate Collection Empty.
	 */
	public static void validateEmpty(Collection<?> collection, String fieldName) {

		FieldValidator.validateEmpty(collection, fieldName);

		LOGGER.info("Collection Empty Validation Passed : {}", fieldName);

		ExtentTestManager.pass("Collection Empty Validation Passed : " + fieldName);
	}

	/**
	 * Validate Collection Not Empty.
	 */
	public static void validateNotEmpty(Collection<?> collection, String fieldName) {

		FieldValidator.validateNotEmpty(collection, fieldName);

		LOGGER.info("Collection Not Empty Validation Passed : {}", fieldName);

		ExtentTestManager.pass("Collection Not Empty Validation Passed : " + fieldName);
	}

	/**
	 * Validate Object Null.
	 */
	public static void validateNull(Object actual, String fieldName) {

		FieldValidator.validateNull(actual, fieldName);

		LOGGER.info("Null Validation Passed : {}", fieldName);

		ExtentTestManager.pass("Null Validation Passed : " + fieldName);
	}

	/**
	 * Validate Object Not Null.
	 */
	public static void validateNotNull(Object actual, String fieldName) {

		FieldValidator.validateNotNull(actual, fieldName);

		LOGGER.info("Not Null Validation Passed : {}", fieldName);

		ExtentTestManager.pass("Not Null Validation Passed : " + fieldName);
	}

}
