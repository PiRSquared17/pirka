package org.pirkaengine.form.field;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.pirkaengine.form.FormMessage;
import org.pirkaengine.form.ResourceBundleFormMessage;
import org.pirkaengine.form.annotation.RangeFloatImpl;
import org.pirkaengine.form.annotation.RequiredImpl;
import org.pirkaengine.form.exception.ConvertException;
import org.pirkaengine.form.validator.RangeFloatValidator;
import org.pirkaengine.form.validator.Validator;

public class FloatFieldTest {
    FloatField target;
    FormMessage formMessage;

    @Before
    public void setup() {
        target = new FloatField();
        formMessage = new ResourceBundleFormMessage();
    }

    @Test
    public void getFieldType() throws Exception {
        assertThat(target.getFieldType(), is(sameInstance(Float.class)));
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
    
    @Test
    public void apply_Required() throws Exception {
        target.apply("name", formMessage, new RequiredImpl());
        assertThat(target.name, is("name"));
        assertThat(target.label, is("name"));
        assertThat(target.isRequired(), is(true));
        assertThat(target.validators.size(), is(0));
        assertThat(target.requiredMessageKey, is("validator.required"));
    }

    @Test
    public void apply_Required_messageKey() throws Exception {
        target.apply("name", formMessage, new RequiredImpl("custom_message"));
        assertThat(target.name, is("name"));
        assertThat(target.label, is("name"));
        assertThat(target.isRequired(), is(true));
        assertThat(target.validators.size(), is(0));
        assertThat(target.requiredMessageKey, is("custom_message"));
    }

    @Test
    public void apply_RangeFloat() throws Exception {
        target.apply("name", formMessage, new RangeFloatImpl(1f, 10f));
        assertThat(target.name, is("name"));
        assertThat(target.label, is("name"));
        assertThat(target.isRequired(), is(false));
        assertThat(target.validators.size(), is(1));
        assertThat(target.validators.get(0), is((Validator<Float>) new RangeFloatValidator(1, 10)));
    }

    @Test
    public void apply_RangeFloat_messageKey() throws Exception {
        target.apply("name", formMessage, new RangeFloatImpl(0f, 100f, "custom_message"));
        assertThat(target.name, is("name"));
        assertThat(target.label, is("name"));
        assertThat(target.isRequired(), is(false));
        assertThat(target.validators.size(), is(1));
        assertThat(target.validators.get(0),
                is((Validator<Float>) new RangeFloatValidator(0f, 100f, "custom_message")));
    }
}
