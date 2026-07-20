package core;

import io.restassured.specification.RequestSpecification;
import logging.LoggerManager;
import reporting.ExtentTestManager;

import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class RequestBuilder {

	private static final Logger LOGGER = LoggerManager.getLogger(RequestBuilder.class);

	private final Map<String, String> headers = new HashMap<>();

	private final Map<String, String> cookies = new HashMap<>();

	private final Map<String, Object> queryParams = new HashMap<>();

	private final Map<String, Object> pathParams = new HashMap<>();

	private final Map<String, Object> formParams = new HashMap<>();

	private Object body;

	// ======================== Headers ========================//

	public RequestBuilder addHeader(String key, String value) {

		if (key != null && value != null) {
			headers.put(key, value);
		}

		return this;
	}

	public RequestBuilder addHeaders(Map<String, String> headers) {

		if (headers != null && !headers.isEmpty()) {
			this.headers.putAll(headers);
		}

		return this;
	}

	// ======================== Cookies ========================//

	public RequestBuilder addCookie(String key, String value) {

		if (key != null && value != null) {
			cookies.put(key, value);
		}

		return this;
	}

	public RequestBuilder addCookies(Map<String, String> cookies) {

		if (cookies != null && !cookies.isEmpty()) {
			this.cookies.putAll(cookies);
		}

		return this;
	}

	// ===================== Query Params ======================//

	public RequestBuilder addQueryParam(String key, Object value) {

		if (key != null && value != null) {
			queryParams.put(key, value);
		}

		return this;
	}

	public RequestBuilder addQueryParams(Map<String, Object> queryParams) {

		if (queryParams != null && !queryParams.isEmpty()) {
			this.queryParams.putAll(queryParams);
		}

		return this;
	}

	// ====================== Path Params ======================//

	public RequestBuilder addPathParam(String key, Object value) {

		if (key != null && value != null) {
			pathParams.put(key, value);
		}

		return this;
	}

	public RequestBuilder addPathParams(Map<String, Object> pathParams) {

		if (pathParams != null && !pathParams.isEmpty()) {
			this.pathParams.putAll(pathParams);
		}

		return this;
	}

	// ====================== Form Params ======================//

	public RequestBuilder addFormParam(String key, Object value) {

		if (key != null && value != null) {
			formParams.put(key, value);
		}

		return this;
	}

	public RequestBuilder addFormParams(Map<String, Object> formParams) {

		if (formParams != null && !formParams.isEmpty()) {
			this.formParams.putAll(formParams);
		}

		return this;
	}

	// ======================== Body ========================//

	public RequestBuilder setBody(Object body) {

		this.body = body;

		return this;
	}

	// ================ Build Request Specification =================//

	public RequestSpecification build(RequestSpecification specification) {

		if (!headers.isEmpty()) {

			LOGGER.info("Headers : {}", headers);
			ExtentTestManager.info("Headers : " + headers);

			specification.headers(headers);
		}

		if (!cookies.isEmpty()) {

			LOGGER.info("Cookies : {}", cookies);
			ExtentTestManager.info("Cookies : " + cookies);

			specification.cookies(cookies);
		}

		if (!queryParams.isEmpty()) {

			LOGGER.info("Query Parameters : {}", queryParams);
			ExtentTestManager.info("Query Parameters : " + queryParams);

			specification.queryParams(queryParams);
		}

		if (!pathParams.isEmpty()) {

			LOGGER.info("Path Parameters : {}", pathParams);
			ExtentTestManager.info("Path Parameters : " + pathParams);

			specification.pathParams(pathParams);
		}

		if (!formParams.isEmpty()) {

			LOGGER.info("Form Parameters : {}", formParams);
			ExtentTestManager.info("Form Parameters : " + formParams);

			specification.formParams(formParams);
		}

		if (body != null) {

			LOGGER.info("Request Body : {}", body);
			ExtentTestManager.info("Request Body : " + body);

			specification.body(body);
		}

		LOGGER.info("Request Specification Built Successfully");
		ExtentTestManager.info("Request Specification Built Successfully");

		return specification;
	}

}