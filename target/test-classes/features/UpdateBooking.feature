@UpdateBooking
Feature: Update Booking API

  @UpdateBookingById
  Scenario: Successfully update booking

    Given a booking exists
    And an authentication token exists
    And I load update booking request body from "booking/UpdateBookingRequest.json"
    When I send a PUT request to endpoint "booker.bookingById"
    Then the update booking response status code should be 200
    And the update booking response should match "booking/UpdateBookingRequest.json"