package hexlet.code.schemas;

import java.util.Map;

public final class MapSchema extends BaseSchema<Map<?, ?>> {

    public MapSchema sizeof(int size) {
        checks.put("sizeof", value -> value != null && value.size() == size);
        return this;
    }
}
