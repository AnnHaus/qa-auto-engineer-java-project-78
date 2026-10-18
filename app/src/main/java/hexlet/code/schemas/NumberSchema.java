package hexlet.code.schemas;

public final class NumberSchema extends BaseSchema<Integer> {

    @Override
    public NumberSchema required() {
        super.required();
        return this;
    }

    public NumberSchema positive() {
        checks.put("positive", value -> value == null || value > 0);
        return this;
    }

    public NumberSchema range(int min, int max) {
        checks.put("range", value -> value == null || (value >= min && value <= max));
        return this;
    }
}

