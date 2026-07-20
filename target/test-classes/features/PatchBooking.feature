@PatchBooking
Feature: Patch Booking API

  @PatchBookingById
  Scenario: Successfully patch booking

    Given a patch booking exists
    And a patch authentication token exists
    And I load patch booking request body from "booking/PatchBookingRequest.json"

    When I send a PATCH request to endpoint "booker.bookingById"

    Then the patch booking response status code should be 200
    And the patch booking response field "lastname" should be "Lakshmi"
    And the patch booking response should match "booking/ExpectedPatchBooking.json"