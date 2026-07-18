package stepdefinitions;

import java.util.Set;

import config.ConfigManager;
import core.ApiExecutor;
import enums.HttpMethod;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pojo.booking.BookingRequest;
import pojo.booking.CreateBookingResponse;

public class BookingSteps {

    private BookingRequest requestBody;

    @Given("I load request body from {string}")
    public void i_load_request_body_from(String requestFile) {

        requestBody = ApiExecutor.loadRequest(
                requestFile,
                BookingRequest.class);
    }

    @When("I send a POST request to endpoint {string}")
    public void i_send_a_post_request_to_endpoint(String endpointKey) {

        ApiExecutor.execute(
                endpointKey,
                HttpMethod.POST,
                ConfigManager.getProperty("booker.base.url"),
                requestBody);
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(Integer statusCode) {

        ApiExecutor.validateStatusCode(statusCode);
    }

    @Then("the response should contain field {string}")
    public void the_response_should_contain_field(String jsonPath) {

        ApiExecutor.validateFieldExists(jsonPath);
    }

    @Then("the response should match {string}")
    public void the_response_should_match(String expectedJson) {

        ApiExecutor.validatePojo(
                expectedJson,
                CreateBookingResponse.class,
                Set.of("bookingid"));
    }
}