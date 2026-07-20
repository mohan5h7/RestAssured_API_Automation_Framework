@DeleteBooking
Feature: Delete Booking API

  @DeleteBookingById
  Scenario: Successfully delete booking

    Given a delete booking exists
    And a delete authentication token exists

    When I send a DELETE request to endpoint "booker.bookingById"

    Then the delete booking response status code should be 201

    When I verify the deleted booking using endpoint "booker.bookingById"

    Then the deleted booking response status code should be 404