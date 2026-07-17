package context;

import java.util.HashMap;
import java.util.Map;

public final class ApiContext {

    private ApiContext() {}

    private static final ThreadLocal<Map<String, Object>> CONTEXT =
            ThreadLocal.withInitial(HashMap::new);

    public static void set(String key, Object value) {
        CONTEXT.get().put(key, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        return (T) CONTEXT.get().get(key);
    }

    public static boolean contains(String key) {
        return CONTEXT.get().containsKey(key);
    }

    public static void remove(String key) {
        CONTEXT.get().remove(key);
    }

    public static void clear() {
        CONTEXT.remove();
    }
}