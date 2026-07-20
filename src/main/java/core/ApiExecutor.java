package core;

import context.ApiContext;
import context.ContextConstants;
import enums.HttpMethod;
import io.restassured.response.Response;
import logging.LoggerManager;
import models.ApiRequest;
import reporting.ExtentTestManager;
import utils.JsonReader;

import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import config.EndpointManager;
import validators.FieldValidator;
import validators.ResponseValidator;

public final class ApiExecutor {

	private static final Logger LOGGER = LoggerManager.getLogger(ApiExecutor.class);

	private ApiExecutor() {
	}

	/**
	 * Load any JSON request as any POJO.
	 */
	public static <T> T loadRequest(String jsonFile, Class<T> clazz) {

		LOGGER.info("Loading Request JSON : {}", jsonFile);

		T requestBody = JsonReader.readJsonAsPojo(jsonFile, clazz);

		ApiContext.set(ContextConstants.REQUEST, requestBody);

		return requestBody;
	}

	/**
	 * Create ApiRequest object.
	 */
	public static <T> ApiRequest createRequest(String endpointKey, HttpMethod method, String baseUrl, T body,
			Map<String, Object> pathParams, Map<String, String> headers) {

		ApiRequest request = new ApiRequest();

		request.setMethod(method);

		request.setBaseUrl(baseUrl);

		request.setEndpoint(EndpointManager.get(endpointKey));

		request.setBody(body);

		request.setPathParams(pathParams);

		request.setHeaders(headers);

		return request;
	}

	/**
	 * Creates and executes an API request.
	 *
	 * Supports GET, POST, PUT, PATCH and DELETE requests.
	 */
	public static <T> Response execute(String endpointKey, HttpMethod method, String baseUrl, T body,
			Map<String, Object> pathParams, Map<String, String> headers) {

		ApiRequest request = createRequest(endpointKey, method, baseUrl, body, pathParams, headers);

		return execute(request);
	}

	/**
	 * Execute ApiRequest.
	 */
	public static Response execute(ApiRequest request) {

		LOGGER.info("Executing API : {} {}", request.getMethod(), request.getEndpoint());

		Response response = RestClient.execute(request);

		ApiContext.set(ContextConstants.RESPONSE, response);

		return response;
	}

	/**
	 * Validate Status Code
	 */
	public static void validateStatusCode(int expectedStatusCode) {

		Response response = ApiContext.get(ContextConstants.RESPONSE);

		LOGGER.info("Validating Status Code : {}", expectedStatusCode);

		ExtentTestManager.info("Validating Status Code : " + expectedStatusCode);

		ResponseValidator.validateStatusCode(response, expectedStatusCode);
	}

	/**
	 * Validate Response Time
	 */
	public static void validateResponseTime(long maxResponseTime) {

		Response response = ApiContext.get(ContextConstants.RESPONSE);

		LOGGER.info("Validating Response Time");

		ExtentTestManager.info("Validating Response Time");

		ResponseValidator.validateResponseTime(response, maxResponseTime);
	}

	/**
	 * Validate JsonPath Value
	 */
	public static void validateResponseField(String jsonPath, Object expectedValue) {

		Response response = ApiContext.get(ContextConstants.RESPONSE);

		LOGGER.info("Validating JsonPath : {}", jsonPath);

		ExtentTestManager.info("Validating JsonPath : " + jsonPath);

		ResponseValidator.validateJsonPath(response, jsonPath, expectedValue);
	}

	/**
	 * Validate JsonPath Exists
	 */
	public static void validateFieldExists(String jsonPath) {

		Response response = ApiContext.get(ContextConstants.RESPONSE);

		LOGGER.info("Validating Field Exists : {}", jsonPath);

		ExtentTestManager.info("Validating Field Exists : " + jsonPath);

		ResponseValidator.validateJsonPathNotNull(response, jsonPath);
	}

	/**
	 * Validate Generic Field
	 */
	public static void validateField(Object actual, Object expected, String fieldName) {

		LOGGER.info("Validating Field : {}", fieldName);

		ExtentTestManager.info("Validating Field : " + fieldName);

		ResponseValidator.validateField(actual, expected, fieldName);
	}

	/**
	 * Validate Not Null
	 */
	public static void validateNotNull(Object value, String fieldName) {

		LOGGER.info("Validating Not Null : {}", fieldName);

		ExtentTestManager.info("Validating Not Null : " + fieldName);

		ResponseValidator.validateNotNull(value, fieldName);
	}

	/**
	 * Validate Null
	 */
	public static void validateNull(Object value, String fieldName) {

		LOGGER.info("Validating Null : {}", fieldName);

		ExtentTestManager.info("Validating Null : " + fieldName);

		ResponseValidator.validateNull(value, fieldName);
	}

	/**
	 * Validate Greater Than
	 */
	public static void validateFieldGreaterThan(String jsonPath, int expectedValue) {

		Response response = ApiContext.get(ContextConstants.RESPONSE);

		Integer actualValue = response.jsonPath().getInt(jsonPath);

		LOGGER.info("{} : {}", jsonPath, actualValue);

		ExtentTestManager.info(jsonPath + " : " + actualValue);

		FieldValidator.validateTrue(actualValue > expectedValue, jsonPath + " should be greater than " + expectedValue);
	}

	/**
	 * Validate Collection Size
	 */
	public static void validateCollectionSize(java.util.Collection<?> collection, int expectedSize, String fieldName) {

		ResponseValidator.validateCollectionSize(collection, expectedSize, fieldName);
	}

	/**
	 * Validate Map Size
	 */
	public static void validateMapSize(java.util.Map<?, ?> map, int expectedSize, String fieldName) {

		ResponseValidator.validateMapSize(map, expectedSize, fieldName);
	}

	/**
	 * Validate Collection Empty
	 */
	public static void validateEmpty(java.util.Collection<?> collection, String fieldName) {

		ResponseValidator.validateEmpty(collection, fieldName);
	}

	/**
	 * Validate Collection Not Empty
	 */
	public static void validateNotEmpty(java.util.Collection<?> collection, String fieldName) {

		ResponseValidator.validateNotEmpty(collection, fieldName);
	}

	/**
	 * Validate Response POJO
	 */
	public static <T> void validatePojo(String expectedJson, Class<T> clazz) {

		LOGGER.info("Validating Response POJO");

		Response response = ApiContext.get(ContextConstants.RESPONSE);

		T expected = JsonReader.readJsonAsPojo(expectedJson, clazz);

		T actual = response.as(clazz);

		ResponseValidator.validatePojo(actual, expected);

	}

	/**
	 * Validate Response POJO by ignoring fields
	 */
	public static <T> void validatePojo(String expectedJson, Class<T> clazz, java.util.Set<String> ignoreFields) {

		LOGGER.info("Validating Response POJO");

		Response response = ApiContext.get(ContextConstants.RESPONSE);

		T expected = JsonReader.readJsonAsPojo(expectedJson, clazz);

		T actual = response.as(clazz);

		ResponseValidator.validatePojo(actual, expected, ignoreFields);

	}

	/**
	 * Validate Response POJO List.
	 */
	public static <T> void validatePojoList(String expectedJson, Class<T> clazz) {

		LOGGER.info("Validating Response POJO List");

		Response response = ApiContext.get(ContextConstants.RESPONSE);

		List<T> expected = JsonReader.readJsonAsPojoList(expectedJson, clazz);

		List<T> actual = response.jsonPath().getList("", clazz);

		ResponseValidator.validatePojo(actual, expected);
	}

	/**
	 * Extract response field and store into ApiContext.
	 *
	 * Example: storeResponseField("bookingid", "id");
	 *
	 * ApiContext: id -> 1234
	 */
	public static void storeResponseField(String jsonPath, String contextKey) {

		Response response = ApiContext.get(ContextConstants.RESPONSE);

		Object value = response.jsonPath().get(jsonPath);

		ApiContext.set(contextKey, value);

		LOGGER.info("Stored Response Field [{}] as Context Key [{}] : {}", jsonPath, contextKey, value);

		ExtentTestManager
				.pass("Stored Response Field [" + jsonPath + "] as Context Key [" + contextKey + "] : " + value);
	}

	/**
	 * Create Path Parameters from ApiContext.
	 *
	 * Example: createPathParams("bookingId");
	 *
	 * Example: createPathParams("employeeId", "departmentId");
	 */
	public static Map<String, Object> createPathParams(String... contextKeys) {

		Map<String, Object> pathParams = new HashMap<>();

		for (String key : contextKeys) {

			Object value = getFromContext(key);

			if (value == null) {
				throw new RuntimeException("Context value not found : " + key);
			}

			pathParams.put(key, value);
		}

		return pathParams;
	}

	/**
	 * Extract value from response
	 */
	public static <T> T extractValue(String jsonPath, Class<T> clazz) {

		Response response = ApiContext.get(ContextConstants.RESPONSE);

		T value = response.jsonPath().getObject(jsonPath, clazz);

		LOGGER.info("Extracted {} : {}", jsonPath, value);

		return value;
	}

	/**
	 * Extract List from Response.
	 */
	public static <T> List<T> extractList(String jsonPath, Class<T> clazz) {

		Response response = ApiContext.get(ContextConstants.RESPONSE);

		List<T> values = response.jsonPath().getList(jsonPath, clazz);

		LOGGER.info("Extracted List from {}", jsonPath);

		return values;
	}

	/**
	 * Save value into ApiContext
	 */
	public static void saveToContext(String key, Object value) {

		ApiContext.set(key, value);

		LOGGER.info("Saved {} into Context", key);

	}

	/**
	 * Retrieve value from ApiContext
	 */
	public static <T> T getFromContext(String key) {

		return ApiContext.get(key);

	}

	/**
	 * Remove value from Context
	 */
	public static void removeFromContext(String key) {

		ApiContext.remove(key);

	}

}