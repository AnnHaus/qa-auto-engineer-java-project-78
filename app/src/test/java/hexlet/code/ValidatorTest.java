package hexlet.code;

import hexlet.code.schemas.StringSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.BaseSchema;
import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.HashMap;
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

    @Test
    public void testMapValidator() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        assertTrue(schema.isValid(null));

        schema.required();
        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(new HashMap<>()));

        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        assertTrue(schema.isValid(data));

        schema.sizeof(2);
        assertFalse(schema.isValid(data));

        data.put("key2", "value2");
        assertTrue(schema.isValid(data));

        MapSchema shapeSchema = v.map();

        Map<String, BaseSchema> schemas = new HashMap<>();
        schemas.put("firstName", v.string().required());
        schemas.put("lastName", v.string().required().minLength(2));

        shapeSchema.shape(schemas);

        Map<String, String> human1 = new HashMap<>();
        human1.put("firstName", "John");
        human1.put("lastName", "Smith");
        assertTrue(shapeSchema.isValid(human1));

        Map<String, String> human2 = new HashMap<>();
        human2.put("firstName", "John");
        human2.put("lastName", null);
        assertFalse(shapeSchema.isValid(human2));

        Map<String, String> human3 = new HashMap<>();
        human3.put("firstName", "Anna");
        human3.put("lastName", "B");
        assertFalse(shapeSchema.isValid(human3));
    }
    @Test
    void testStringSchemaUpdate() {
        Validator v = new Validator();
        var schema = v.string();
        schema.minLength(5);
        assertFalse(schema.isValid("cat"));
        schema.minLength(2);
        assertTrue(schema.isValid("cat"));
    }

    @Test
    void testNumberSchemaUpdate() {
        Validator v = new Validator();
        var schema = v.number();
        schema.range(5, 10);
        assertTrue(schema.isValid(5));
        assertTrue(schema.isValid(10));
        assertFalse(schema.isValid(4));
        schema.range(4, 10);
        assertTrue(schema.isValid(4));
    }

    @Test
    void testMapSchemaShapeUpdate() {
        Validator v = new Validator();
        var schema = v.map();

        // Сначала правило: имя должно быть строкой длиной от 2 символов
        java.util.Map<String, BaseSchema> schemas1 = new java.util.HashMap<>();
        schemas1.put("name", v.string().minLength(2));
        schema.shape(schemas1);

        java.util.Map<String, Object> data = new java.util.HashMap<>();
        data.put("name", "Anna");
        assertTrue(schema.isValid(data)); // "Anna" подходит (длина 4)

        // Обновляем shape: теперь минимальная длина должна быть 10 символов!
        java.util.Map<String, BaseSchema> schemas2 = new java.util.HashMap<>();
        schemas2.put("name", v.string().minLength(10));
        schema.shape(schemas2);

        // Теперь "Anna" (длина 4) не должна проходить проверку, так как нужно 10!
        assertFalse(schema.isValid(data));
    }

}
