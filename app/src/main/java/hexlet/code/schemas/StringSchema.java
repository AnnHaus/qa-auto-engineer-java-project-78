package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public final class StringSchema {
    private final Map<String, Predicate<String>> checks = new HashMap<>();
    private boolean isRequired = false;

    public StringSchema required() {
        this.isRequired = true;
        checks.put("required", value -> value != null && !value.isEmpty());
        return this;
    }

    public StringSchema minLength(int length) {
        checks.put("minLength", value -> value != null && value.length() >= length);
        return this;
    }

    public StringSchema contains(String substring) {
        checks.put("contains", value -> value != null && value.contains(substring));
        return this;
    }

    public boolean isValid(String value) {
        if (!isRequired && (value == null || value.isEmpty())) {
            return true;
        }

        for (Predicate<String> check : checks.values()) {
            if (!check.test(value)) {
                return false;
            }
        }
        return true;
    }
}
