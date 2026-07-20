package stepdefinitions;

import config.ConfigManager;
import core.ApiExecutor;
import enums.HttpMethod;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pojo.products.ProductResponse;

public class GetProductsSteps {

	@When("I send a GET request to grocery endpoint {string}")
	public void i_send_a_get_request_to_grocery_endpoint(String endpointKey) {

		ApiExecutor.execute(endpointKey, HttpMethod.GET, ConfigManager.getProperty("grocery.base.url"), null, null,
				null);

	}

	@Then("the products response status code should be {int}")
	public void the_products_response_status_code_should_be(Integer statusCode) {

		ApiExecutor.validateStatusCode(statusCode);

	}

	@Then("the products response should match {string}")
	public void the_products_response_should_match(String expectedJson) {

		ApiExecutor.validatePojoList(expectedJson, ProductResponse.class);

	}

}