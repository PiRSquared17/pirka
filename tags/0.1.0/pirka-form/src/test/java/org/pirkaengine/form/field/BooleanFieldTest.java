package org.pirkaengine.form.field;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.pirkaengine.form.exception.ConvertException;

public class BooleanFieldTest {
    BooleanField target;

    @Before
    public void setup() {
        target = new BooleanField();
    }

    @Test
    public void getFieldType() throws Exception {
        assertThat(target.getFieldType(), is(sameInstance(Boolean.class)));
    }

    @Test
    public void convert() {
        assertEquals(Boolean.TRUE, target.convert("true"));
        assertEquals(Boolean.TRUE, target.convert("TRUE"));
        assertEquals(Boolean.FALSE, target.convert("false"));
        assertEquals(Boolean.FALSE, target.convert("FALSE"));
        assertEquals(Boolean.FALSE, target.convert(null));
    }

    @Test
    public void toString_test() {
        assertEquals("true", target.toString(Boolean.TRUE));
        assertEquals("false", target.toString(Boolean.FALSE));
    }

}
