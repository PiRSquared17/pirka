package org.pirkaengine.form.field;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;


import org.junit.Test;
import org.pirkaengine.form.exception.ConvertException;
import org.pirkaengine.form.field.DateField;

public class DateFieldTest {
    DateField target;

    @Test
    public void convert() {
        target = new DateField();
        assertEquals(dateOf(2009, 12, 10), target.convert("2009/12/10"));
    }

    @Test(expected = ConvertException.class)
    public void convert_wrong_format() {
        target = new DateField();
        assertEquals(dateOf(2009, 12, 10), target.convert("2009-12-10"));
    }

    @Test
    public void convert_empty_str() {
        target = new DateField();
        assertEquals(null, target.convert(""));
    }

    @Test
    public void convert_null() {
        target = new DateField();
        assertEquals(null, target.convert(null));
    }

    @Test
    public void toString_test() {
        target = new DateField();
        assertEquals("2009/12/10", target.toString(dateOf(2009, 12, 10)));
    }

    @Test
    public void toString_null() {
        target = new DateField();
        assertEquals("", target.toString(null));
    }

    @Test
    public void clean_required_false() {
        target = new DateField();
        target.required = false;
        assertEquals(true, target.clean());
        assertEquals(false, target.hasError());
    }

    @Test
    public void clean_required_ok() {
        target = new DateField();
        target.required = true;
        target.setRawText("2009/12/10");
        assertEquals(true, target.clean());
        assertEquals(false, target.hasError());
    }

    @Test
    public void clean_required_ng() {
        target = new DateField();
        target.required = true;
        assertEquals(false, target.clean());
        assertEquals(true, target.hasError());
    }

    @Test
    public void convert_custom_pattern() {
        target = new DateField("yyyyMMdd");
        assertEquals(dateOf(2009, 12, 11), target.convert("20091211"));
    }

    @Test
    public void convert_custom_pattern_empty_str() {
        target = new DateField("yyyyMMdd");
        assertEquals(null, target.convert(""));
    }

    @Test
    public void convert_custom_pattern_null() {
        target = new DateField("yyyyMMdd");
        assertEquals(null, target.convert(null));
    }

    @Test
    public void toString_custom_pattern() {
        target = new DateField("yyyyMMdd");
        assertEquals("20091210", target.toString(dateOf(2009, 12, 10)));
    }

    @Test
    public void toString_custom_pattern_null() {
        target = new DateField("yyyyMMdd");
        assertEquals("", target.toString(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void toString_wrong_pattern() {
        target = new DateField("xxxx");
    }

    @Test(expected = IllegalArgumentException.class)
    public void toString_null_pattern() {
        target = new DateField((String) null);
    }
    
    @Test
    public void default_value() {
        target = new DateField(dateOf(2009, 12, 30));
        assertEquals(dateOf(2009, 12, 30), target.getValue());
        assertEquals("2009/12/30", target.getRawText());
    }

    private Date dateOf(int y, int m, int d) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, y);
        cal.set(Calendar.MONTH, m - 1);
        cal.set(Calendar.DATE, d);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
