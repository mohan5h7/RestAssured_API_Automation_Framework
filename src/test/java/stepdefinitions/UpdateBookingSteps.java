package stepdefinitions;

import auth.AuthManager;
import config.ConfigManager;
import context.ApiContext;
import context.ContextConstants;
import core.ApiExecutor;
import enums.HttpMethod;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pojo.booking.UpdateBookingRequest;
import pojo.booking.UpdateBookingResponse;
import testdata.TestDataFactory;

public class UpdateBookingSteps {

	@And("a booking exists")
	public void a_booking_exists() {

		TestDataFactory.createBooking();

	}

	@And("an authentication token exists")
	public void an_authentication_token_exists() {

		AuthManager.getHeaders();

	}

	@And("I load update booking request body from {string}")
	public void i_load_update_booking_request_body_from(String jsonFile) {

		ApiExecutor.loadRequest(jsonFile, UpdateBookingRequest.class);

	}

	@When("I send a PUT request to endpoint {string}")
	public void i_send_a_put_request_to_endpoint(String endpointKey) {

		UpdateBookingRequest request = ApiContext.get(ContextConstants.REQUEST);
		System.out.println(request);
		System.out.println(ApiExecutor.createPathParams("bookingId"));
		System.out.println(AuthManager.getHeaders());
		ApiExecutor.execute(endpointKey, HttpMethod.PUT, ConfigManager.getProperty("booker.base.url"), request,
				ApiExecutor.createPathParams("bookingId"), AuthManager.getHeaders());

	}

	@Then("the update booking response status code should be {int}")
	public void the_update_booking_response_status_code_should_be(Integer statusCode) {

		ApiExecutor.validateStatusCode(statusCode);

	}

	@Then("the update booking response should match {string}")
	public void the_update_booking_response_should_match(String expectedJson) {

		ApiExecutor.validatePojo(expectedJson, UpdateBookingResponse.class);

	}

}