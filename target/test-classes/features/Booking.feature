@Booking
Feature: Create Booking API

  @CreateBooking
  Scenario: Successfully create a booking

    Given I load request body from "booking/CreateBookingRequest.json"
    When I send a POST request to endpoint "booker.booking"
    Then the response status code should be 200
    And the response should contain field "bookingid"
    And the response should match "booking/ExpectedBooking.json"