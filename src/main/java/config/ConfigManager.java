package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigManager {

	private static final Properties properties = new Properties();
	private static boolean loaded = false;

	private ConfigManager() {
		// Prevent object creation
	}

	/**
	 * Load config.properties
	 */
	public static synchronized void loadProperties() {

		if (loaded) {
			return;
		}

		try (InputStream inputStream = ConfigManager.class.getClassLoader()
				.getResourceAsStream("config/config-dev.properties")) {

			if (inputStream == null) {
				throw new RuntimeException("config.properties not found");
			}

			properties.load(inputStream);

			loaded = true;

		} catch (IOException e) {

			throw new RuntimeException("Unable to load config.properties", e);

		}
	}

	/**
	 * Get property value by key
	 */
	public static String getProperty(String key) {

		String value = properties.getProperty(key);

		if (value == null || value.trim().isEmpty()) {
			throw new RuntimeException("Property not found : " + key);
		}

		return value.trim();
	}

	/**
	 * Get Integer Property
	 */
	public static int getIntProperty(String key) {

		return Integer.parseInt(getProperty(key));

	}

	/**
	 * Get Boolean Property
	 */
	public static boolean getBooleanProperty(String key) {

		return Boolean.parseBoolean(getProperty(key));

	}

}