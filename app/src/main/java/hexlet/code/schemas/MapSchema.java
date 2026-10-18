package hexlet.code.schemas;

import java.util.Map;

public final class MapSchema extends BaseSchema<Map<?, ?>> {

    public MapSchema sizeof(int size) {
        checks.put("sizeof", value -> value != null && value.size() == size);
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema<String>> schemas) {
        checks.put("shape", value -> {
            if (value == null) {
                return false;
            }

            for (Map.Entry<String, BaseSchema<String>> entry : schemas.entrySet()) {
                String key = entry.getKey();
                BaseSchema<String> innerSchema = entry.getValue();

                Object actualValue = value.get(key);

                if (!innerSchema.isValid((String) actualValue)) {
                    return false;
                }
            }
            return true;
        });
        return this;
    }
}
