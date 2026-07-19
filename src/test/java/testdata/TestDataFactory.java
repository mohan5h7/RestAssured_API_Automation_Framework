package testdata;

import config.ConfigManager;
import core.ApiExecutor;
import enums.HttpMethod;
import pojo.booking.BookingRequest;

public final class TestDataFactory {

	private TestDataFactory() {

	}

	/**
	 * Creates a Booking and stores bookingId into ApiContext.
	 */
	public static void createBooking() {

		// Load Request JSON
		BookingRequest request = ApiExecutor.loadRequest("booking/CreateBookingRequest.json", BookingRequest.class);

		// Execute POST Request
		ApiExecutor.execute("booker.booking", HttpMethod.POST, ConfigManager.getProperty("booker.base.url"), request,
				null);

		// Store bookingId into ApiContext
		ApiExecutor.storeResponseField("bookingid", "bookingId");
	}

}