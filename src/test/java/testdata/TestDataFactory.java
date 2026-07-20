package testdata;

import config.ConfigManager;
import core.ApiExecutor;
import enums.HttpMethod;
import pojo.auth.AuthRequest;
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
				null, null);

		// Store bookingId into ApiContext
		ApiExecutor.storeResponseField("bookingid", "bookingId");
	}

	/**
	 * Creates authentication token and stores it in ApiContext.
	 */
	public static void createToken() {

		// Load Auth Request
		AuthRequest request = ApiExecutor.loadRequest("auth/AuthRequest.json", AuthRequest.class);

		// Execute Auth API
		ApiExecutor.execute("booker.auth", HttpMethod.POST, ConfigManager.getProperty("booker.base.url"), request, null,
				null);

		// Store Token into ApiContext
		ApiExecutor.storeResponseField("token", "token");
	}
}