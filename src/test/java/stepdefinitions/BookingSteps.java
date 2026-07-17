package stepdefinitions;

import java.util.Set;

import config.ConfigManager;
import enums.HttpMethod;
import executor.ApiExecutor;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pojo.booking.BookingRequest;
import pojo.booking.CreateBookingResponse;

public class BookingSteps {

    private BookingRequest requestBody;

    @Given("I load default booking request")
    public void loadDefaultBookingRequest() {

        requestBody = ApiExecutor.loadRequest(
                "booking/CreateBookingRequest.json",
                BookingRequest.class);
    }

    @Given("I update firstname as {string}")
    public void updateFirstName(String firstname) {

        requestBody.setFirstname(firstname);
    }

    @Given("I update lastname as {string}")
    public void updateLastName(String lastname) {

        requestBody.setLastname(lastname);
    }

    @Given("I update totalprice as {string}")
    public void updateTotalPrice(String price) {

        requestBody.setTotalprice(Integer.parseInt(price));
    }

    @When("I send a POST request to endpoint {string}")
    public void sendPostRequest(String endpointKey) {

        ApiExecutor.execute(
                endpointKey,
                HttpMethod.POST,
                ConfigManager.getProperty("booker.base.url"),
                requestBody);
    }

    @Then("the response status code should be {int}")
    public void validateStatusCode(Integer statusCode) {

        ApiExecutor.validateStatusCode(statusCode);
    }

    @Then("the response should contain field {string}")
    public void validateField(String jsonPath) {

        ApiExecutor.validateFieldExists(jsonPath);
    }

    @Then("the response should match {string}")
    public void validateResponse(String expectedJson) {

        ApiExecutor.validatePojo(
                expectedJson,
                CreateBookingResponse.class,
                Set.of("bookingid"));
    }
}