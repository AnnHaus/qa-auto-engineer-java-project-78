package hexlet.code.schemas;

public final class StringSchema extends BaseSchema<String> {

    @Override
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
}
