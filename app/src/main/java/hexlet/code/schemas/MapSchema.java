package hexlet.code.schemas;

import java.util.Map;

@SuppressWarnings("rawtypes")
public final class MapSchema extends BaseSchema<Map<?, ?>> {
    private Map shapeSchemas;

    public MapSchema required() {
        this.isRequired = true;
        checks.put("required", value -> value != null);
        return this;
    }

    public MapSchema sizeof(int size) {
        checks.put("sizeof", value -> value != null && value.size() == size);
        return this;
    }

    public MapSchema shape(Map schemas) {
        this.shapeSchemas = schemas;

        checks.put("shape", value -> {
            if (value == null) {
                return false;
            }
            if (shapeSchemas == null) {
                return true;
            }

            for (Object obj : shapeSchemas.entrySet()) {
                Map.Entry entry = (Map.Entry) obj;
                Object key = entry.getKey();
                BaseSchema schema = (BaseSchema) entry.getValue();

                Object actualValue = value.get(key);

                if (!schema.isValid(actualValue)) {
                    return false;
                }
            }
            return true;
        });

        return this;
    }
}
