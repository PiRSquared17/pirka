package org.pirkaengine.form.field;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.pirkaengine.form.exception.ConvertException;

public class SelectEnumFieldTest {
    SelectEnumField<Options> target;

    public static enum Options {
        A, B, C;
    }
    
    @Before
    public void setup() {
        target = new SelectEnumField<Options>(Options.class, Options.values());
    }

    @Test
    public void getFieldType() throws Exception {
        assertThat(target.getFieldType(), is(sameInstance(Options.class)));
    }
    
    @Test
    public void convert() {
        assertSame(Options.A, target.convert("A"));
        assertSame(Options.B, target.convert("B"));
        assertSame(Options.C, target.convert("C"));
    }

    @Test
    public void convert_empty_str() {
        assertSame(null, target.convert(""));
    }

    @Test
    public void convert_null() {
        assertSame(null, target.convert(null));
    }

    @Test(expected = ConvertException.class)
    public void convert_error() {
        target.convert("X");
    }

    @Test
    public void toString_test() {
        assertEquals("A", target.toString(Options.A));
        assertEquals("B", target.toString(Options.B));
        assertEquals("C", target.toString(Options.C));
    }

    @Test
    public void toString_null() {
        assertEquals("", target.toString(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void toString_arg0_null() {
        new SelectEnumField<Options>(null, Options.values());
    }

    @Test(expected = IllegalArgumentException.class)
    public void toString_arg1_null() {
        new SelectEnumField<Options>(Options.class, null);
    }

    @Test
    public void options() {
        assertArrayEquals(Options.values(), target.options());
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
        target.setRawText("A");
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
        target = new SelectEnumField<Options>(Options.class, Options.values(), Options.B);
        assertEquals(Options.B, target.getValue());
        assertEquals("B", target.getRawText());
    }
    
}
