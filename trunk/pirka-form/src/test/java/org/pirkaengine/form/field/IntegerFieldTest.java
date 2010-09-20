package org.pirkaengine.form.field;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.pirkaengine.form.exception.ConvertException;
import org.pirkaengine.form.field.IntegerField;

public class IntegerFieldTest {
    IntegerField target;

    @Before
    public void setup() {
        target = new IntegerField();
    }

    @Test
    public void getFieldType() throws Exception {
        assertThat(target.getFieldType(), is(sameInstance(Integer.class)));
    }
    
    @Test
    public void convert() {
        assertEquals(Integer.valueOf(200), target.convert("200"));
        assertEquals(Integer.valueOf(-10), target.convert("-10"));
    }

    @Test(expected = ConvertException.class)
    public void convert_float() {
        target.convert("10.0");
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
        assertEquals("100", target.toString(100));
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
        target.setRawText("50");
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
        target = new IntegerField(10);
        assertEquals(Integer.valueOf(10), target.getValue());
        assertEquals("10", target.getRawText());
    }

}
