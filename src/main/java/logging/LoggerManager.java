package logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Logger Manager
 *
 * Provides Log4j2 logger instances.
 */
public final class LoggerManager {

	private LoggerManager() {
		// Prevent instantiation
	}

	/**
	 * Returns logger for the given class.
	 *
	 * @param clazz Class object
	 * @return Logger instance
	 */
	public static Logger getLogger(Class<?> clazz) {
		return LogManager.getLogger(clazz);
	}

}