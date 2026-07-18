package validators;

import reporting.ExtentTestManager;

import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import logging.LoggerManager;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
/**
 * Enterprise Reflection Comparator
 *
 * Features:
 * ✔ Recursive POJO comparison
 * ✔ Nested Objects
 * ✔ Collections
 * ✔ Arrays
 * ✔ Maps
 * ✔ Ignore fields
 * ✔ Circular reference detection
 */
public final class ReflectionComparator {

    private static final Logger LOGGER =
            LoggerManager.getLogger(ReflectionComparator.class);

    private ReflectionComparator() {
    }

    public static void compare(Object actual,
            Object expected) {

compare(
actual,
expected,
Collections.emptySet(),
new HashSet<>());

LOGGER.info("POJO Comparison Passed");

ExtentTestManager.pass("POJO Comparison Passed");

}

    public static void compare(Object actual,
            Object expected,
            Set<String> ignoreFields) {

compare(
actual,
expected,
ignoreFields == null
     ? Collections.emptySet()
     : ignoreFields,
new HashSet<>());

LOGGER.info("POJO Comparison Passed");

ExtentTestManager.pass("POJO Comparison Passed");

}

    private static void compare(Object actual,
                                Object expected,
                                Set<String> ignoreFields,
                                Set<VisitedPair> visitedPairs) {

        if (actual == null && expected == null) return;

        Assert.assertNotNull(actual, "Actual object is null");
        Assert.assertNotNull(expected, "Expected object is null");

        Assert.assertEquals(actual.getClass(), expected.getClass(), "Object type mismatch");

        VisitedPair pair = new VisitedPair(actual, expected);
        if (!visitedPairs.add(pair)) return;

        Class<?> clazz = actual.getClass();

        if (ValidationUtils.isSimpleType(clazz)) {
            Assert.assertEquals(actual, expected, "Value mismatch");
            return;
        }

        if (Collection.class.isAssignableFrom(clazz)) {
            compareCollections((Collection<?>) actual, (Collection<?>) expected, ignoreFields, visitedPairs);
            return;
        }

        if (clazz.isArray()) {
            compareArrays(actual, expected, ignoreFields, visitedPairs);
            return;
        }

        if (Map.class.isAssignableFrom(clazz)) {
            compareMaps((Map<?, ?>) actual, (Map<?, ?>) expected, ignoreFields, visitedPairs);
            return;
        }

        compareFields(actual, expected, clazz, ignoreFields, visitedPairs);
    }

    private static void compareCollections(Collection<?> actual,
                                           Collection<?> expected,
                                           Set<String> ignoreFields,
                                           Set<VisitedPair> visitedPairs) {
        Assert.assertEquals(actual.size(), expected.size(), "Collection size mismatch");

        Iterator<?> actualIterator = actual.iterator();
        Iterator<?> expectedIterator = expected.iterator();

        while (actualIterator.hasNext() && expectedIterator.hasNext()) {
            compare(actualIterator.next(), expectedIterator.next(), ignoreFields, visitedPairs);
        }
    }

    private static void compareArrays(Object actual,
                                      Object expected,
                                      Set<String> ignoreFields,
                                      Set<VisitedPair> visitedPairs) {
        int actualLength = Array.getLength(actual);
        int expectedLength = Array.getLength(expected);

        Assert.assertEquals(actualLength, expectedLength, "Array length mismatch");

        for (int index = 0; index < actualLength; index++) {
            compare(Array.get(actual, index), Array.get(expected, index), ignoreFields, visitedPairs);
        }
    }

    private static void compareMaps(Map<?, ?> actual,
                                    Map<?, ?> expected,
                                    Set<String> ignoreFields,
                                    Set<VisitedPair> visitedPairs) {
        Assert.assertEquals(actual.size(), expected.size(), "Map size mismatch");

        for (Map.Entry<?, ?> entry : actual.entrySet()) {
            Object key = entry.getKey();
            Assert.assertTrue(expected.containsKey(key), "Missing key : " + key);

            compare(entry.getValue(), expected.get(key), ignoreFields, visitedPairs);
        }
    }

    private static void compareFields(Object actual,
                                      Object expected,
                                      Class<?> clazz,
                                      Set<String> ignoreFields,
                                      Set<VisitedPair> visitedPairs) {
    	for (Field field : getAllFields(clazz)) {

    	    if (ignoreFields.contains(field.getName())) {
    	        continue;
    	    }

    	    if (Modifier.isStatic(field.getModifiers())) {
    	        continue;
    	    }

    	    if (field.isSynthetic()) {
    	        continue;
    	    }

            field.setAccessible(true);
            try {
                compare(field.get(actual), field.get(expected), ignoreFields, visitedPairs);
            } catch (IllegalAccessException exception) {
                throw new ValidationException("Unable to compare field : " + field.getName(), exception);
            }
        }
    }

    private static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null && clazz != Object.class) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    private static final class VisitedPair {
        private final Object actual;
        private final Object expected;

        private VisitedPair(Object actual, Object expected) {
            this.actual = actual;
            this.expected = expected;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof VisitedPair)) return false;
            VisitedPair pair = (VisitedPair) object;
            return actual == pair.actual && expected == pair.expected;
        }

        @Override
        public int hashCode() {
            return Objects.hash(System.identityHashCode(actual), System.identityHashCode(expected));
        }
    }
}
