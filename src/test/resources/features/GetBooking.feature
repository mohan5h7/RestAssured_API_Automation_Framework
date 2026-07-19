@GetBooking
Feature: Get Booking API

  @GetBookingById
  Scenario: Get booking by id

    When I send a GET request to endpoint "booker.bookingById"

    Then the get booking response status code should be 200

    And the get booking response should match "booking/ExpectedGetBooking.json"