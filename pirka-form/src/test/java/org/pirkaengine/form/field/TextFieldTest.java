package org.pirkaengine.form.field;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.pirkaengine.form.annotation.LabelImpl;
import org.pirkaengine.form.annotation.RegexImpl;
import org.pirkaengine.form.annotation.RequiredImpl;
import org.pirkaengine.form.annotation.StartWithImpl;
import org.pirkaengine.form.validator.RegexValidator;
import org.pirkaengine.form.validator.StartWithValidator;
import org.pirkaengine.form.validator.Validator;

public class TextFieldTest {

    TextField target;

    @Before
    public void setup() {
        target = new TextField();
    }

    @Test
    public void apply_Label() throws Exception {
        target.apply("name", new LabelImpl("TextName"));
        assertThat(target.name, is("name"));
        assertThat(target.label, is("TextName"));
        assertThat(target.isRequired(), is(false));
        assertThat(target.validators.size(), is(0));
    }

    @Test
    public void apply_Required() throws Exception {
        target.apply("name", new RequiredImpl());
        assertThat(target.name, is("name"));
        assertThat(target.label, is("name"));
        assertThat(target.isRequired(), is(true));
        assertThat(target.validators.size(), is(0));
    }

    @Test
    public void apply_StartWith() throws Exception {
        target.apply("name", new StartWithImpl("/"));
        assertThat(target.name, is("name"));
        assertThat(target.label, is("name"));
        assertThat(target.isRequired(), is(false));
        assertThat(target.validators.size(), is(1));
        assertThat(target.validators.get(0), is((Validator<String>) new StartWithValidator("/")));
    }

    @Test
    public void apply_Regex() throws Exception {
        target.apply("html", new RegexImpl(".*\\.html"));
        assertThat(target.name, is("html"));
        assertThat(target.label, is("html"));
        assertThat(target.isRequired(), is(false));
        assertThat(target.validators.size(), is(1));
        assertThat(target.validators.get(0), is((Validator<String>) new RegexValidator(".*\\.html")));
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
