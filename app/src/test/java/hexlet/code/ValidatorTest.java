package hexlet.code;

import hexlet.code.schemas.StringSchema;
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

        schema.minLength(5).contains("hex");
        assertTrue(schema.isValid("hexlet"));
        assertFalse(schema.isValid("hex"));

        schema.contains("let");
        assertTrue(schema.isValid("hexlet"));

        schema.contains("invalid");
        assertFalse(schema.isValid("hexlet"));
    }
}
