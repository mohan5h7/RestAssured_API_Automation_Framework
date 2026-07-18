package utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import config.ConfigManager;

public final class JsonReader {

    private static final ObjectMapper mapper = new ObjectMapper();
    String jsonFolder =
            ConfigManager.getProperty("json.folder");

    private JsonReader() {
        // Prevent object creation
    }

    /**
     * Returns InputStream of JSON file.
     */
    private static InputStream getFile(String fileName) {

        String jsonFolder = ConfigManager.getProperty("json.folder");

        InputStream inputStream = JsonReader.class
                .getClassLoader()
                .getResourceAsStream(jsonFolder + fileName);

        if (inputStream == null) {
            throw new RuntimeException("JSON file not found: " + fileName);
        }

        return inputStream;
    }
    /**
     * Read JSON as String.
     */
    public static String readJsonAsString(String fileName) {

        try (InputStream inputStream = getFile(fileName)) {

            return new String(inputStream.readAllBytes(),
                    StandardCharsets.UTF_8);

        } catch (IOException e) {

            throw new RuntimeException("Unable to read JSON : "
                    + fileName, e);

        }
    }

    /**
     * Read JSON Object as Map.
     */
    public static Map<String, Object> readJsonObject(String fileName) {

        try (InputStream inputStream = getFile(fileName)) {

            return mapper.readValue(
                    inputStream,
                    new TypeReference<Map<String, Object>>() {
                    });

        } catch (IOException e) {

            throw new RuntimeException("Unable to parse JSON Object : "
                    + fileName, e);

        }
    }

    /**
     * Read JSON Array.
     */
    public static List<Map<String, Object>> readJsonArray(String fileName) {

        try (InputStream inputStream = getFile(fileName)) {

            return mapper.readValue(
                    inputStream,
                    new TypeReference<List<Map<String, Object>>>() {
                    });

        } catch (IOException e) {

            throw new RuntimeException("Unable to parse JSON Array : "
                    + fileName, e);

        }
    }

    /**
     * Read JSON as POJO.
     */
    public static <T> T readJsonAsPojo(String fileName,
                                       Class<T> clazz) {

        try (InputStream inputStream = getFile(fileName)) {

            return mapper.readValue(inputStream, clazz);

        } catch (IOException e) {

            throw new RuntimeException("Unable to parse JSON POJO : "
                    + fileName, e);

        }
    }

    /**
     * Read JSON using Generic TypeReference.
     */
    public static <T> T readJson(String fileName,
                                 TypeReference<T> typeReference) {

        try (InputStream inputStream = getFile(fileName)) {

            return mapper.readValue(inputStream, typeReference);

        } catch (IOException e) {

            throw new RuntimeException("Unable to parse JSON : "
                    + fileName, e);

        }
    }

    /**
     * Read JSON as JsonNode.
     */
    public static JsonNode readJsonNode(String fileName) {

        try (InputStream inputStream = getFile(fileName)) {

            return mapper.readTree(inputStream);

        } catch (IOException e) {

            throw new RuntimeException("Unable to parse JsonNode : "
                    + fileName, e);

        }
    }

    /**
     * Get single value from JSON Object.
     */
    public static Object getValue(String fileName,
                                  String key) {

        return readJsonObject(fileName).get(key);

    }

}