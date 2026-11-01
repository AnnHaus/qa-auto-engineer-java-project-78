package hexlet.code.schemas;

import java.util.Map;

@SuppressWarnings("rawtypes")
public final class MapSchema extends BaseSchema<Map<?, ?>> {
    // Используем сырой тип Map, чтобы Java не ругалась на несовместимость дженериков
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

            // Проходим по всем правилам, которые задали в shape
            for (Object obj : shapeSchemas.entrySet()) {
                Map.Entry entry = (Map.Entry) obj;
                Object key = entry.getKey();
                BaseSchema schema = (BaseSchema) entry.getValue();

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
