package validators;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.temporal.Temporal;
import java.util.Collection;
import java.util.Map;

/**
 * Utility methods used by the enterprise validation framework.
 */
public final class ValidationUtils {

	private ValidationUtils() {
	}

	public static boolean isSimpleType(Class<?> clazz) {

		return clazz.isPrimitive() || clazz == String.class || Number.class.isAssignableFrom(clazz)
				|| clazz == Boolean.class || clazz == Character.class || clazz == Byte.class || clazz == Short.class
				|| clazz == Integer.class || clazz == Long.class || clazz == Float.class || clazz == Double.class
				|| clazz == BigDecimal.class || clazz == BigInteger.class || clazz.isEnum()
				|| Temporal.class.isAssignableFrom(clazz);
	}

	public static boolean isCollection(Class<?> clazz) {
		return Collection.class.isAssignableFrom(clazz);
	}

	public static boolean isMap(Class<?> clazz) {
		return Map.class.isAssignableFrom(clazz);
	}

	public static boolean isArray(Class<?> clazz) {
		return clazz.isArray();
	}
}
