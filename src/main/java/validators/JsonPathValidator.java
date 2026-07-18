package validators;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import logging.LoggerManager;
import reporting.ExtentTestManager;

import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

/**
 * Generic JSON Path Validator
 */
public final class JsonPathValidator {

    private static final Logger LOGGER =
            LoggerManager.getLogger(JsonPathValidator.class);

    private JsonPathValidator() {
    }

    /**
     * Returns JsonPath object.
     */
    private static JsonPath getJsonPath(Response response) {

        Assert.assertNotNull(response, "Response cannot be null");

        return response.jsonPath();

    }

    /**
     * Validate value.
     */
    public static void validateValue(Response response,
                                     String jsonPath,
                                     Object expected) {

        Object actual = getJsonPath(response).get(jsonPath);

        Assert.assertEquals(
                actual,
                expected,
                "Mismatch at JSONPath : " + jsonPath);

        LOGGER.info("JSONPath Validation Passed : {} = {}", jsonPath, actual);

        ExtentTestManager.pass(
                "JSONPath Validation Passed : " + jsonPath);

    }

    /**
     * Validate value is not null.
     */
    public static void validateNotNull(Response response,
                                       String jsonPath) {

        Object actual = getJsonPath(response).get(jsonPath);

        Assert.assertNotNull(
                actual,
                "Value is null at JSONPath : " + jsonPath);

        LOGGER.info("JSONPath Not Null Validation Passed : {}", jsonPath);

        ExtentTestManager.pass(
                "JSONPath Not Null Validation Passed : " + jsonPath);

    }

    /**
     * Validate value is null.
     */
    public static void validateNull(Response response,
                                    String jsonPath) {

        Object actual = getJsonPath(response).get(jsonPath);

        Assert.assertNull(
                actual,
                "Value should be null at JSONPath : " + jsonPath);

        LOGGER.info("JSONPath Null Validation Passed : {}", jsonPath);

        ExtentTestManager.pass(
                "JSONPath Null Validation Passed : " + jsonPath);

    }

    /**
     * Validate path exists.
     */
    public static void validateExists(Response response,
                                      String jsonPath) {

        Assert.assertTrue(
                getJsonPath(response).getMap("$").containsKey(jsonPath.split("\\.")[0]),
                "JSONPath does not exist : " + jsonPath);

        LOGGER.info("JSONPath Exists Validation Passed : {}", jsonPath);

        ExtentTestManager.pass(
                "JSONPath Exists Validation Passed : " + jsonPath);

    }

    /**
     * Validate list size.
     */
    public static void validateListSize(Response response,
                                        String jsonPath,
                                        int expectedSize) {

        List<?> list = getJsonPath(response).getList(jsonPath);

        Assert.assertNotNull(
                list,
                "List not found : " + jsonPath);

        Assert.assertEquals(
                list.size(),
                expectedSize,
                "List size mismatch at : " + jsonPath);

        LOGGER.info("List Size Validation Passed : {} Size={}",
                jsonPath,
                expectedSize);

        ExtentTestManager.pass(
                "List Size Validation Passed : " + jsonPath);

    }

    /**
     * Validate collection contains value.
     */
    public static void validateContains(Response response,
                                        String jsonPath,
                                        Object expectedValue) {

        List<?> values = getJsonPath(response).getList(jsonPath);

        Assert.assertTrue(
                values.contains(expectedValue),
                "Value not found at : " + jsonPath);

        LOGGER.info("Contains Validation Passed : {} contains {}",
                jsonPath,
                expectedValue);

        ExtentTestManager.pass(
                "Contains Validation Passed : " + jsonPath);

    }

    /**
     * Get value.
     */
    public static Object getValue(Response response,
                                  String jsonPath) {

        return getJsonPath(response).get(jsonPath);

    }

    /**
     * Get list.
     */
    public static <T> List<T> getList(Response response,
                                      String jsonPath) {

        return getJsonPath(response).getList(jsonPath);

    }

    /**
     * Get map.
     */
    public static Map<String, Object> getMap(Response response,
                                             String jsonPath) {

        return getJsonPath(response).getMap(jsonPath);

    }

}