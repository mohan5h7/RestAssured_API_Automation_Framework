package config;

import java.io.InputStream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class EndpointManager {

    private static final JsonNode endpointConfig;

    static {
        endpointConfig = loadEndpoints();
    }

    private EndpointManager() {
        // Prevent object creation
    }

    /**
     * Load endpoints.json
     */
    private static JsonNode loadEndpoints() {

        try (InputStream inputStream = EndpointManager.class
                .getClassLoader()
                .getResourceAsStream("config/endpoints.json")) {

            if (inputStream == null) {
                throw new RuntimeException("endpoints.json not found");
            }

            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.readTree(inputStream);

        } catch (Exception exception) {

            throw new RuntimeException(
                    "Failed to load endpoints.json",
                    exception);

        }
    }

    /**
     * Get endpoint by key
     *
     * Example:
     * EndpointManager.get("booker.booking");
     */
    public static String get(String key) {

        String[] keys = key.split("\\.");

        JsonNode currentNode = endpointConfig;

        for (String currentKey : keys) {

            currentNode = currentNode.path(currentKey);

            if (currentNode.isMissingNode()) {
                throw new RuntimeException(
                        "Endpoint not found : " + key);
            }
        }

        return currentNode.asText();
    }

}