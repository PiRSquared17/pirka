package org.pirkaengine.form.field;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.pirkaengine.form.exception.ConvertException;
import org.pirkaengine.form.field.FloatField;

public class FloatFieldTest {
    FloatField target;

    @Before
    public void setup() {
        target = new FloatField();
    }

    @Test
    public void convert() {
        assertEquals(Float.valueOf(50.2f), target.convert("50.2"));
        assertEquals(Float.valueOf(10f), target.convert("10"));
    }

    @Test(expected = ConvertException.class)
    public void convert_wrong_format() {
        target.convert("*");
    }

    @Test
    public void convert_empty_str() {
        assertEquals(null, target.convert(""));
    }

    @Test
    public void convert_null() {
        assertEquals(null, target.convert(null));
    }

    @Test
    public void toString_test() {
        assertEquals("50.2", target.toString(50.2f));
        assertEquals("100.0", target.toString(100f));
    }

    @Test
    public void toString_null() {
        assertEquals("", target.toString(null));
    }

    @Test
    public void clean_required_false() {
        target.required = false;
        assertEquals(true, target.clean());
        assertEquals(false, target.hasError());
    }

    @Test
    public void clean_required_ok() {
        target.required = true;
        target.setRawText("5.1");
        assertEquals(true, target.clean());
        assertEquals(false, target.hasError());
    }

    @Test
    public void clean_required_ng() {
        target.required = true;
        assertEquals(false, target.clean());
        assertEquals(true, target.hasError());
    }

    @Test
    public void default_value() {
        target = new FloatField(10.2f);
        assertEquals(Float.valueOf(10.2f), target.getValue());
        assertEquals("10.2", target.getRawText());
    }
}
