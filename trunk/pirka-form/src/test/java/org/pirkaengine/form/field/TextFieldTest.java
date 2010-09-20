package org.pirkaengine.form.field;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.pirkaengine.form.FormMessage;
import org.pirkaengine.form.ResourceBundleFormMessage;
import org.pirkaengine.form.annotation.LabelImpl;
import org.pirkaengine.form.annotation.RegexImpl;
import org.pirkaengine.form.annotation.RequiredImpl;
import org.pirkaengine.form.annotation.StartWithImpl;
import org.pirkaengine.form.annotation.UriUsableImpl;
import org.pirkaengine.form.validator.RegexValidator;
import org.pirkaengine.form.validator.StartWithValidator;
import org.pirkaengine.form.validator.UriUsableValidator;
import org.pirkaengine.form.validator.Validator;

public class TextFieldTest {

    TextField target;
    FormMessage formMessage;

    @Before
    public void setup() {
        target = new TextField();
        formMessage = new ResourceBundleFormMessage();
    }

    @Test
    public void getFieldType() throws Exception {
        assertThat(target.getFieldType(), is(sameInstance(String.class)));
    }

    @Test
    public void apply_Label() throws Exception {
        target.apply("name", formMessage, new LabelImpl("TextName"));
        assertThat(target.name, is("name"));
        assertThat(target.label, is("TextName"));
        assertThat(target.isRequired(), is(false));
        assertThat(target.validators.size(), is(0));
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
    public void apply_StartWith() throws Exception {
        target.apply("name", formMessage, new StartWithImpl("/"));
        assertThat(target.name, is("name"));
        assertThat(target.label, is("name"));
        assertThat(target.isRequired(), is(false));
        assertThat(target.validators.size(), is(1));
        assertThat(target.validators.get(0), is((Validator<String>) new StartWithValidator("/")));
    }

    @Test
    public void apply_StartWith_messageKey() throws Exception {
        target.apply("name", formMessage, new StartWithImpl("/", "custom_message"));
        assertThat(target.name, is("name"));
        assertThat(target.label, is("name"));
        assertThat(target.isRequired(), is(false));
        assertThat(target.validators.size(), is(1));
        assertThat(target.validators.get(0), is((Validator<String>) new StartWithValidator("/", "custom_message")));
    }

    @Test
    public void apply_Regex() throws Exception {
        target.apply("html", formMessage, new RegexImpl(".*\\.html"));
        assertThat(target.name, is("html"));
        assertThat(target.label, is("html"));
        assertThat(target.isRequired(), is(false));
        assertThat(target.validators.size(), is(1));
        assertThat(target.validators.get(0), is((Validator<String>) new RegexValidator(".*\\.html")));
    }

    @Test
    public void apply_Regex_messageKey() throws Exception {
        target.apply("html", formMessage, new RegexImpl(".*\\.html", "custom_key"));
        assertThat(target.name, is("html"));
        assertThat(target.label, is("html"));
        assertThat(target.isRequired(), is(false));
        assertThat(target.validators.size(), is(1));
        assertThat(target.validators.get(0), is((Validator<String>) new RegexValidator(".*\\.html", "custom_key")));
    }

    @Test
    public void apply_UriUsable() throws Exception {
        target.apply("name", formMessage, new UriUsableImpl());
        assertThat(target.name, is("name"));
        assertThat(target.label, is("name"));
        assertThat(target.validators.size(), is(1));
        assertThat(target.validators.get(0), is((Validator<String>) new UriUsableValidator()));
    }

    @Test
    public void apply_UriUsable_messageKey() throws Exception {
        target.apply("name", formMessage, new UriUsableImpl("custom_key"));
        assertThat(target.name, is("name"));
        assertThat(target.label, is("name"));
        assertThat(target.validators.size(), is(1));
        assertThat(target.validators.get(0), is((Validator<String>) new UriUsableValidator("custom_key")));
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
