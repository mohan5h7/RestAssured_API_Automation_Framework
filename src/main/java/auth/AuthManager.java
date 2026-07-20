package auth;

import config.ConfigManager;
import config.EndpointManager;
import io.restassured.response.Response;
import specification.RequestSpecificationFactory;
import utils.JsonReader;

import java.util.HashMap;
import java.util.Map;

public final class AuthManager {

	private static String token;

	private AuthManager() {

	}

	/**
	 * Returns Authentication Headers using default Content-Type from
	 * config.properties.
	 */
	public static synchronized Map<String, String> getHeaders() {

		return getHeaders(ConfigManager.getProperty("content.type"));

	}

	/**
	 * Returns Authentication Headers using specified Content-Type.
	 */
	public static synchronized Map<String, String> getHeaders(String contentType) {

		if (token == null || token.isBlank()) {

			generateToken();

		}

		Map<String, String> headers = new HashMap<>();

		if (contentType != null && !contentType.isBlank()) {

			headers.put("Content-Type", contentType);

		}

		headers.put("Accept", ConfigManager.getProperty("accept.type"));

		String authType = ConfigManager.getProperty("auth.type");

		switch (authType.toLowerCase()) {

		case "cookie":

			headers.put("Cookie", "token=" + token);

			break;

		case "bearer":

			headers.put("Authorization", "Bearer " + token);

			break;

		case "apikey":

			headers.put("x-api-key", token);

			break;

		case "basic":

			String username = ConfigManager.getProperty("username");

			String password = ConfigManager.getProperty("password");

			String credentials = java.util.Base64.getEncoder().encodeToString((username + ":" + password).getBytes());

			headers.put("Authorization", "Basic " + credentials);

			break;

		default:

			throw new RuntimeException("Unsupported Authentication Type : " + authType);

		}

		return headers;

	}

	/**
	 * Generate Authentication Token.
	 */
	private static synchronized void generateToken() {

		String payload = JsonReader.readJsonAsString("auth/CreateToken.json");

		Response response =

				RequestSpecificationFactory.getRequestSpecification(ConfigManager.getProperty("booker.base.url"))

						.body(payload)

						.post(EndpointManager.get("booker.auth"));

		if (response.statusCode() != 200) {

			throw new RuntimeException("Unable to generate authentication token.");

		}

		token = response.jsonPath().getString("token");

		if (token == null || token.isBlank()) {

			throw new RuntimeException("Authentication token is empty.");

		}

	}

	/**
	 * Returns Generated Token.
	 */
	public static String getToken() {

		if (token == null || token.isBlank()) {

			generateToken();

		}

		return token;

	}

	/**
	 * Clears cached token.
	 */
	public static void clearToken() {

		token = null;

	}

}