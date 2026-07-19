@Booking
Feature: Create Booking API

  @CreateBooking
  Scenario: Successfully create a booking

    Given I load request body from "booking/CreateBookingRequest.json"
    When I send a POST request to endpoint "booker.booking"
    Then the create booking response status code should be 200
    And the create booking response should contain field "bookingid"
    And the create booking response should match "booking/ExpectedBooking.json"
    Then I store create booking response field "bookingid" as "bookingId"