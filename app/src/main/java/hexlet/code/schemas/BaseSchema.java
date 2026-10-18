package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {
    protected final Map<String, Predicate<T>> checks = new HashMap<>();
    protected boolean isRequired = false;

    public BaseSchema<T> required() {
        this.isRequired = true;
        checks.put("required", value -> value != null);
        return this;
    }

    public final boolean isValid(T value) {
        if (!isRequired && value == null) {
            return true;
        }

        for (Predicate<T> check : checks.values()) {
            if (!check.test(value)) {
                return false;
            }
        }
        return true;
    }
}
