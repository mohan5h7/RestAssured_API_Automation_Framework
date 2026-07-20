package validators;

/**
 * Custom exception for validation failures.
 */
public class ValidationException extends RuntimeException {

	/**
	 * Creates a validation exception with message.
	 *
	 * @param message Exception message
	 */
	public ValidationException(String message) {
		super(message);
	}

	/**
	 * Creates a validation exception with message and cause.
	 *
	 * @param message Exception message
	 * @param cause   Root cause
	 */
	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

}