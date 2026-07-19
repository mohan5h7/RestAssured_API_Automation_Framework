package specification;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import logging.ApiLoggingFilter;

public final class RequestSpecificationFactory {

	private RequestSpecificationFactory() {
	}

	public static RequestSpecification getRequestSpecification(String baseUrl) {

		RequestSpecification specification = new RequestSpecBuilder().setBaseUri(baseUrl)
				.setContentType(ContentType.JSON).addFilter(new ApiLoggingFilter()).build();

		return RestAssured.given().spec(specification);
	}
}