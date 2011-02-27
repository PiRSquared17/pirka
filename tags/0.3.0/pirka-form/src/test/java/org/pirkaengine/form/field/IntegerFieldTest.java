package org.pirkaengine.form.field;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.pirkaengine.form.FormMessage;
import org.pirkaengine.form.ResourceBundleFormMessage;
import org.pirkaengine.form.annotation.RangeIntegerImpl;
import org.pirkaengine.form.annotation.RequiredImpl;
import org.pirkaengine.form.exception.ConvertException;
import org.pirkaengine.form.validator.RangeIntegerValidator;
import org.pirkaengine.form.validator.Validator;

public class IntegerFieldTest {
    IntegerField target;
    FormMessage formMessage;

    @Before
    public void setup() {
        target = new IntegerField();
        formMessage = new ResourceBundleFormMessage();
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
    public void apply_RangeInteger() throws Exception {
        target.apply("name", formMessage, new RangeIntegerImpl(1, 10));
        assertThat(target.name, is("name"));
        assertThat(target.label, is("name"));
        assertThat(target.isRequired(), is(false));
        assertThat(target.validators.size(), is(1));
        assertThat(target.validators.get(0), is((Validator<Integer>) new RangeIntegerValidator(1, 10)));
    }

    @Test
    public void apply_RangeInteger_messageKey() throws Exception {
        target.apply("name", formMessage, new RangeIntegerImpl(0, 100, "custom_message"));
        assertThat(target.name, is("name"));
        assertThat(target.label, is("name"));
        assertThat(target.isRequired(), is(false));
        assertThat(target.validators.size(), is(1));
        assertThat(target.validators.get(0),
                is((Validator<Integer>) new RangeIntegerValidator(0, 100, "custom_message")));
    }
}
