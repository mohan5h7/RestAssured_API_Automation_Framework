package stepdefinitions;

import auth.AuthManager;
import config.ConfigManager;
import context.ContextConstants;
import core.ApiExecutor;
import enums.HttpMethod;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pojo.booking.PatchBookingRequest;
import pojo.booking.PatchBookingResponse;
import testdata.TestDataFactory;

public class PatchBookingSteps {

	@Given("a patch booking exists")
	public void a_patch_booking_exists() {

		TestDataFactory.createBooking();

	}

	@And("a patch authentication token exists")
	public void a_patch_authentication_token_exists() {

		AuthManager.getHeaders();

	}

	@And("I load patch booking request body from {string}")
	public void i_load_patch_booking_request_body_from(String jsonFile) {

		ApiExecutor.loadRequest(jsonFile, PatchBookingRequest.class);

	}

	@When("I send a PATCH request to endpoint {string}")
	public void i_send_a_patch_request_to_endpoint(String endpointKey) {

		PatchBookingRequest request = ApiExecutor.getFromContext(ContextConstants.REQUEST);

		ApiExecutor.execute(endpointKey, HttpMethod.PATCH, ConfigManager.getProperty("booker.base.url"), request,
				ApiExecutor.createPathParams("bookingId"), AuthManager.getHeaders());

	}

	@Then("the patch booking response status code should be {int}")
	public void the_patch_booking_response_status_code_should_be(Integer statusCode) {

		ApiExecutor.validateStatusCode(statusCode);

	}

	@Then("the patch booking response field {string} should be {string}")
	public void the_patch_booking_response_field_should_be(String jsonPath, String expectedValue) {

		ApiExecutor.validateResponseField(jsonPath, expectedValue);

	}

	@Then("the patch booking response should match {string}")
	public void the_patch_booking_response_should_match(String expectedJson) {

		ApiExecutor.validatePojo(expectedJson, PatchBookingResponse.class);

	}

}