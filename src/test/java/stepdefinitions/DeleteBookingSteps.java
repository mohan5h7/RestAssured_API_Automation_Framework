package stepdefinitions;

import auth.AuthManager;
import config.ConfigManager;
import core.ApiExecutor;
import enums.HttpMethod;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import testdata.TestDataFactory;

public class DeleteBookingSteps {

	@Given("a delete booking exists")
	public void a_delete_booking_exists() {

		TestDataFactory.createBooking();

	}

	@And("a delete authentication token exists")
	public void a_delete_authentication_token_exists() {

		AuthManager.getHeaders();

	}

	@When("I send a DELETE request to endpoint {string}")
	public void i_send_a_delete_request_to_endpoint(String endpointKey) {

		ApiExecutor.execute(endpointKey, HttpMethod.DELETE, ConfigManager.getProperty("booker.base.url"), null,
				ApiExecutor.createPathParams("bookingId"), AuthManager.getHeaders());

	}

	@Then("the delete booking response status code should be {int}")
	public void the_delete_booking_response_status_code_should_be(Integer statusCode) {

		ApiExecutor.validateStatusCode(statusCode);

	}

	@When("I verify the deleted booking using endpoint {string}")
	public void i_verify_the_deleted_booking_using_endpoint(String endpointKey) {

		ApiExecutor.execute(endpointKey, HttpMethod.GET, ConfigManager.getProperty("booker.base.url"), null,
				ApiExecutor.createPathParams("bookingId"), null);

	}

	@Then("the deleted booking response status code should be {int}")
	public void the_deleted_booking_response_status_code_should_be(Integer statusCode) {

		ApiExecutor.validateStatusCode(statusCode);

	}

}