package core;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import logging.LoggerManager;
import models.ApiRequest;
import reporting.ExtentTestManager;
import specification.RequestSpecificationFactory;

import org.apache.logging.log4j.Logger;

public final class RestClient {

	private static final Logger LOGGER = LoggerManager.getLogger(RestClient.class);

	private RestClient() {
	}

	public static Response execute(ApiRequest request) {

		if (request == null) {
			throw new IllegalArgumentException("ApiRequest cannot be null.");
		}

		if (request.getMethod() == null) {
			throw new IllegalArgumentException("HTTP Method cannot be null.");
		}

		if (request.getEndpoint() == null || request.getEndpoint().isBlank()) {
			throw new IllegalArgumentException("Endpoint cannot be null or empty.");
		}

		LOGGER.info("Executing {} Request : {}", request.getMethod(), request.getEndpoint());

		ExtentTestManager.info("Executing " + request.getMethod() + " : " + request.getEndpoint());

		RequestSpecification specification = RequestSpecificationFactory.getRequestSpecification(request.getBaseUrl());

		specification = new RequestBuilder().addHeaders(request.getHeaders()).addCookies(request.getCookies())
				.addQueryParams(request.getQueryParams()).addPathParams(request.getPathParams())
				.addFormParams(request.getFormParams()).setBody(request.getBody()).build(specification);

		Response response;

		switch (request.getMethod()) {

		case GET:
			response = specification.get(request.getEndpoint());
			break;

		case POST:
			response = specification.post(request.getEndpoint());
			break;

		case PUT:
			response = specification.put(request.getEndpoint());
			break;

		case PATCH:
			response = specification.patch(request.getEndpoint());
			break;

		case DELETE:
			response = specification.delete(request.getEndpoint());
			break;

		default:
			throw new IllegalArgumentException("Unsupported HTTP Method : " + request.getMethod());
		}
		System.out.println("========== RESPONSE ==========");
		response.prettyPrint();
		System.out.println("==============================");

		LOGGER.info("Status Code : {}", response.getStatusCode());
		LOGGER.info("Response Time : {} ms", response.getTime());

		ExtentTestManager.info("Status Code : " + response.getStatusCode());

		ExtentTestManager.info("Response Time : " + response.getTime() + " ms");

		return response;
	}
}