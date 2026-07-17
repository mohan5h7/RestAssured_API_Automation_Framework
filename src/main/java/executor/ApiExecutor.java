package executor;

import commonutils.JsonReader;
import commonutils.LoggerManager;
import context.ApiContext;
import context.ContextConstants;
import enums.HttpMethod;
import io.restassured.response.Response;
import manager.EndpointManager;
import models.ApiRequest;
import client.RestClient;
import org.apache.logging.log4j.Logger;
import commonutils.ExtentTestManager;
import validators.FieldValidator;
import validators.ResponseValidator;

public final class ApiExecutor {

    private static final Logger LOGGER =
            LoggerManager.getLogger(ApiExecutor.class);

    private ApiExecutor() {
    }

    /**
     * Load any JSON request as any POJO.
     */
    public static <T> T loadRequest(String jsonFile,
                                    Class<T> clazz) {

        LOGGER.info("Loading Request JSON : {}", jsonFile);

        T requestBody =
                JsonReader.readJsonAsPojo(jsonFile, clazz);

        ApiContext.set(
                ContextConstants.REQUEST,
                requestBody);

        return requestBody;
    }

    /**
     * Create ApiRequest object.
     */
    public static <T> ApiRequest createRequest(String endpointKey,
                                               HttpMethod method,
                                               String baseUrl,
                                               T body) {

        ApiRequest request = new ApiRequest();

        request.setMethod(method);

        request.setBaseUrl(baseUrl);

        request.setEndpoint(
                EndpointManager.get(endpointKey));

        request.setBody(body);

        return request;
    }

    /**
     * Execute ApiRequest.
     */
    public static Response execute(ApiRequest request) {

        LOGGER.info("Executing API : {} {}",
                request.getMethod(),
                request.getEndpoint());

        Response response =
                RestClient.execute(request);

        ApiContext.set(
                ContextConstants.RESPONSE,
                response);

        return response;
    }

    /**
     * Create request and execute.
     */
    public static <T> Response execute(String endpointKey,
                                       HttpMethod method,
                                       String baseUrl,
                                       T body) {

        ApiRequest request =
                createRequest(
                        endpointKey,
                        method,
                        baseUrl,
                        body);

        return execute(request);
    }

    
    
    
    /**
     * Validate Status Code
     */
    public static void validateStatusCode(int expectedStatusCode) {

        Response response =
                ApiContext.get(ContextConstants.RESPONSE);

        LOGGER.info("Validating Status Code : {}", expectedStatusCode);

        ExtentTestManager.info(
                "Validating Status Code : " + expectedStatusCode);

        ResponseValidator.validateStatusCode(
                response,
                expectedStatusCode);
    }

    /**
     * Validate Response Time
     */
    public static void validateResponseTime(long maxResponseTime) {

        Response response =
                ApiContext.get(ContextConstants.RESPONSE);

        LOGGER.info("Validating Response Time");

        ExtentTestManager.info(
                "Validating Response Time");

        ResponseValidator.validateResponseTime(
                response,
                maxResponseTime);
    }

    /**
     * Validate JsonPath Value
     */
    public static void validateResponseField(String jsonPath,
                                             Object expectedValue) {

        Response response =
                ApiContext.get(ContextConstants.RESPONSE);

        LOGGER.info("Validating JsonPath : {}", jsonPath);

        ExtentTestManager.info(
                "Validating JsonPath : " + jsonPath);

        ResponseValidator.validateJsonPath(
                response,
                jsonPath,
                expectedValue);
    }

    /**
     * Validate JsonPath Exists
     */
    public static void validateFieldExists(String jsonPath) {

        Response response =
                ApiContext.get(ContextConstants.RESPONSE);

        LOGGER.info("Validating Field Exists : {}", jsonPath);

        ExtentTestManager.info(
                "Validating Field Exists : " + jsonPath);

        ResponseValidator.validateJsonPathNotNull(
                response,
                jsonPath);
    }

    /**
     * Validate Generic Field
     */
    public static void validateField(Object actual,
                                     Object expected,
                                     String fieldName) {

        LOGGER.info("Validating Field : {}", fieldName);

        ExtentTestManager.info(
                "Validating Field : " + fieldName);

        ResponseValidator.validateField(
                actual,
                expected,
                fieldName);
    }

    /**
     * Validate Not Null
     */
    public static void validateNotNull(Object value,
                                       String fieldName) {

        LOGGER.info("Validating Not Null : {}", fieldName);

        ExtentTestManager.info(
                "Validating Not Null : " + fieldName);

        ResponseValidator.validateNotNull(
                value,
                fieldName);
    }

    /**
     * Validate Null
     */
    public static void validateNull(Object value,
                                    String fieldName) {

        LOGGER.info("Validating Null : {}", fieldName);

        ExtentTestManager.info(
                "Validating Null : " + fieldName);

        ResponseValidator.validateNull(
                value,
                fieldName);
    }

    /**
     * Validate Greater Than
     */
    public static void validateFieldGreaterThan(String jsonPath,
                                                int expectedValue) {

        Response response =
                ApiContext.get(ContextConstants.RESPONSE);

        Integer actualValue =
                response.jsonPath().getInt(jsonPath);

        LOGGER.info("{} : {}", jsonPath, actualValue);

        ExtentTestManager.info(
                jsonPath + " : " + actualValue);

        FieldValidator.validateTrue(
                actualValue > expectedValue,
                jsonPath + " should be greater than " + expectedValue);
    }

    /**
     * Validate Collection Size
     */
    public static void validateCollectionSize(
            java.util.Collection<?> collection,
            int expectedSize,
            String fieldName) {

        ResponseValidator.validateCollectionSize(
                collection,
                expectedSize,
                fieldName);
    }

    /**
     * Validate Map Size
     */
    public static void validateMapSize(
            java.util.Map<?, ?> map,
            int expectedSize,
            String fieldName) {

        ResponseValidator.validateMapSize(
                map,
                expectedSize,
                fieldName);
    }

    /**
     * Validate Collection Empty
     */
    public static void validateEmpty(
            java.util.Collection<?> collection,
            String fieldName) {

        ResponseValidator.validateEmpty(
                collection,
                fieldName);
    }

    /**
     * Validate Collection Not Empty
     */
    public static void validateNotEmpty(
            java.util.Collection<?> collection,
            String fieldName) {

        ResponseValidator.validateNotEmpty(
                collection,
                fieldName);
    }
    
    
    
    /**
     * Validate Response POJO
     */
    public static <T> void validatePojo(String expectedJson,
                                        Class<T> clazz) {

        LOGGER.info("Validating Response POJO");

        Response response =
                ApiContext.get(ContextConstants.RESPONSE);

        T expected =
                JsonReader.readJsonAsPojo(
                        expectedJson,
                        clazz);

        T actual =
                response.as(clazz);

        ResponseValidator.validatePojo(
                actual,
                expected);

    }
    
    
    /**
     * Validate Response POJO by ignoring fields
     */
    public static <T> void validatePojo(String expectedJson,
                                        Class<T> clazz,
                                        java.util.Set<String> ignoreFields) {

        LOGGER.info("Validating Response POJO");

        Response response =
                ApiContext.get(ContextConstants.RESPONSE);

        T expected =
                JsonReader.readJsonAsPojo(
                        expectedJson,
                        clazz);

        T actual =
                response.as(clazz);

       

        ResponseValidator.validatePojo(
                actual,
                expected,
                ignoreFields);

    }
    
    /**
     * Extract value from response
     */
    public static <T> T extractValue(String jsonPath,
                                     Class<T> clazz) {

        Response response =
                ApiContext.get(ContextConstants.RESPONSE);

        T value =
                response.jsonPath()
                        .getObject(jsonPath, clazz);

        LOGGER.info("Extracted {} : {}", jsonPath, value);

        return value;
    }
    
    /**
     * Save value into ApiContext
     */
    public static void saveToContext(String key,
                                     Object value) {

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