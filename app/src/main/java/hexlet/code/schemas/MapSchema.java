package hexlet.code.schemas;

import java.util.Map;

public final class MapSchema extends BaseSchema<Map<?, ?>> {
    private Map<?, BaseSchema> shapeSchemas;

    public MapSchema required() {
        this.isRequired = true;
        checks.put("required", value -> value != null);
        return this;
    }

    public MapSchema sizeof(int size) {
        checks.put("sizeof", value -> value != null && value.size() == size);
        return this;
    }

    public MapSchema shape(Map<?, BaseSchema> schemas) {
        this.shapeSchemas = schemas;

        checks.put("shape", value -> {
            if (value == null) {
                return false;
            }
            if (shapeSchemas == null) {
                return true;
            }

            // Проходим по всем правилам, которые задали в shape
            for (Map.Entry<?, BaseSchema> entry : shapeSchemas.entrySet()) {
                Object key = entry.getKey();
                BaseSchema schema = entry.getValue();

                // Получаем значение из проверяемой карты по этому ключу
                Object actualValue = value.get(key);

                // Запускаем валидацию этого значения встроенной схемой
                if (!schema.isValid(actualValue)) {
                    return false;
                }
            }
            return true;
        });

        return this;
    }
}
