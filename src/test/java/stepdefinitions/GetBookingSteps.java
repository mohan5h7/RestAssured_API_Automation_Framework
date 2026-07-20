package stepdefinitions;

import config.ConfigManager;
import core.ApiExecutor;
import enums.HttpMethod;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pojo.booking.GetBookingResponse;
import testdata.TestDataFactory;

public class GetBookingSteps {

	@When("I send a GET request to endpoint {string}")
	public void i_send_a_get_request_to_endpoint(String endpointKey) {

		// Create Booking
		TestDataFactory.createBooking();

		// Execute GET
		ApiExecutor.execute(endpointKey, HttpMethod.GET, ConfigManager.getProperty("booker.base.url"), null,
				ApiExecutor.createPathParams("bookingId"), null);
	}

	@Then("the get booking response status code should be {int}")
	public void the_response_status_code_should_be(Integer statusCode) {

		ApiExecutor.validateStatusCode(statusCode);
	}

	@Then("the get booking response should match {string}")
	public void the_response_should_match(String expectedJson) {

		ApiExecutor.validatePojo(expectedJson, GetBookingResponse.class);
	}
}