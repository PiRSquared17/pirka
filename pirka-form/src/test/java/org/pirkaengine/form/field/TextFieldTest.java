package org.pirkaengine.form.field;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.pirkaengine.form.field.TextField;

public class TextFieldTest {

    TextField target;

    @Before
    public void setup() {
        target = new TextField();
    }

    @Test
    public void convert() {
        assertEquals("Hello", target.convert("Hello"));
    }

    @Test
    public void convert_empty_str() {
        assertEquals("", target.convert(""));
    }

    @Test
    public void convert_null() {
        assertEquals(null, target.convert(null));
    }

    @Test
    public void toString_test() {
        assertEquals("Hello", target.toString("Hello"));
    }

    @Test
    public void toString_empty_str() {
        assertEquals("", target.toString(""));
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
        target.setRawText("AAA");
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
    public void clean_required_ng2() {
        target.required = true;
        target.setRawText("");
        assertEquals(false, target.clean());
        assertEquals(true, target.hasError());
    }

    @Test
    public void default_value() {
        target = new TextField("Hello");
        assertEquals("Hello", target.getValue());
        assertEquals("Hello", target.getRawText());
    }
}
