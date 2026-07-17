@Booking
Feature: Create Booking API

  @CreateBooking
  Scenario Outline: Successfully create booking

    Given I load default booking request

    And I update firstname as "<firstname>"

    And I update lastname as "<lastname>"

    And I update totalprice as "<price>"

    When I send a POST request to endpoint "booker.booking"

    Then the response status code should be 200

    And the response should contain field "bookingid"

    And the response should match "booking/ExpectedBooking.json"

    Examples:
      | firstname | lastname | price |
      | John      | Smith    | 500   |
      | David     | Kumar    | 700   |
      | Alex      | Peter    | 900   |