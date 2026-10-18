package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.StringSchema;
import hexlet.code.schemas.NumberSchema;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public final class ValidatorTest {

    @Test
    public void testStringValidator() {
        Validator v = new Validator();
        StringSchema schema = v.string();

        assertTrue(schema.isValid(""));
        assertTrue(schema.isValid(null));

        schema.required();
        assertFalse(schema.isValid(""));
        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid("hexlet"));

        schema.minLength(5);
        schema.contains("hex");
        assertTrue(schema.isValid("hexlet"));
        assertFalse(schema.isValid("hex"));

        schema.contains("let");
        assertTrue(schema.isValid("hexlet"));
    }

    @Test
    public void testNumberValidator() {
        Validator v = new Validator();
        NumberSchema schema = v.number();

        assertTrue(schema.isValid(null));

        schema.positive();
        assertTrue(schema.isValid(null));

        schema.required();
        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(10));
        assertFalse(schema.isValid(-10));
        assertFalse(schema.isValid(0));

        schema.range(5, 10);
        assertTrue(schema.isValid(5));
        assertTrue(schema.isValid(10));
        assertFalse(schema.isValid(4));
        assertFalse(schema.isValid(11));
    }
}
