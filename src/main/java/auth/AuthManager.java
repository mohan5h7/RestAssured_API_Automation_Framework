package auth;

import commonutils.JsonReader;
import config.ConfigManager;
import factory.RequestSpecificationFactory;
import io.restassured.response.Response;
import manager.EndpointManager;

import java.util.HashMap;
import java.util.Map;

public final class AuthManager {

    private static String token;

    private AuthManager() {

    }

    /**
     * Returns Authentication Headers
     */
    public static synchronized Map<String, String> getAuthenticationHeaders() {

        if (token == null || token.isBlank()) {

            generateToken();

        }

        Map<String, String> headers = new HashMap<>();

        headers.put(
                "Content-Type",
                ConfigManager.getProperty("content.type"));

        headers.put(
                "Accept",
                ConfigManager.getProperty("accept.type"));

        String authType =
                ConfigManager.getProperty("auth.type");

        switch (authType.toLowerCase()) {

            case "cookie":

                headers.put("Cookie",
                        "token=" + token);

                break;

            case "bearer":

                headers.put("Authorization",
                        "Bearer " + token);

                break;

            case "apikey":

                headers.put("x-api-key",
                        token);

                break;

            default:

                throw new RuntimeException(
                        "Unsupported Authentication Type : "
                                + authType);

        }

        return headers;

    }

    /**
     * Generate Authentication Token
     */
    private static synchronized void generateToken() {

        String payload =
                JsonReader.readJsonAsString(
                        "auth/CreateToken.json");

        Response response =

                RequestSpecificationFactory
                        .getRequestSpecification(
                                ConfigManager.getProperty("booker.base.url"))

                        .body(payload)

                        .post(
                                EndpointManager.get("booker.auth"));

        if (response.statusCode() != 200) {

            throw new RuntimeException(
                    "Unable to generate authentication token.");

        }

        token =
                response.jsonPath()
                        .getString("token");

        if (token == null || token.isBlank()) {

            throw new RuntimeException(
                    "Authentication token is empty.");

        }

    }

    /**
     * Returns Generated Token
     */
    public static String getToken() {

        if (token == null || token.isBlank()) {

            generateToken();

        }

        return token;

    }

}