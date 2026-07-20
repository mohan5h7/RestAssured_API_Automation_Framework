@GetProducts
Feature: Get Products API

  @GetAllProducts
  Scenario: Get all products

   When I send a GET request to grocery endpoint "grocery.products"
    Then the products response status code should be 200
    And the products response should match "products/ExpectedProducts.json"