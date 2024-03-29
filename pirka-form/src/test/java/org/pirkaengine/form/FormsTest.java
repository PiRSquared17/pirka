package org.pirkaengine.form;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.pirkaengine.form.annotation.Label;
import org.pirkaengine.form.annotation.Required;
import org.pirkaengine.form.field.DateField;
import org.pirkaengine.form.field.SelectEnumField;
import org.pirkaengine.form.field.TextField;

public class FormsTest {

    public static class TextFieldForm extends MyForm {
        public TextField text = new TextField();
    }

    @Test
    public void newForm_TextField() {
        TextFieldForm form = Forms.newForm(TextFieldForm.class);
        assertThat(form.text.getName(), is("text"));
        assertThat(form.text.getLabel(), is("text"));
        assertThat(form.text.isRequired(), is(false));
    }

    public static class RequiredTextFieldForm extends MyForm {
        @Label("Name")
        @Required
        public TextField text = new TextField();
    }

    @Test
    public void newForm_TextField_required() {
        RequiredTextFieldForm form = Forms.newForm(RequiredTextFieldForm.class);
        assertThat(form.text.getName(), is("text"));
        assertThat(form.text.getLabel(), is("Name"));
        assertThat(form.text.isRequired(), is(true));
    }

    public static class DateFieldForm extends MyForm {
        public DateField date = new DateField();
    }

    @Test
    public void newForm_DateField() {
        DateFieldForm form = Forms.newForm(DateFieldForm.class);
        assertEquals("date", form.date.getName());
        assertEquals("date", form.date.getLabel());
        assertEquals(false, form.date.isRequired());
    }

    public static class SelectEnumFieldForm extends MyForm {
        public SelectEnumField<Options> options = new SelectEnumField<Options>(Options.class, Options.values());
    }

    @Test
    public void newForm_SelectEnumField() {
        SelectEnumFieldForm form = Forms.newForm(SelectEnumFieldForm.class);
        assertEquals("options", form.options.getName());
        assertEquals("options", form.options.getLabel());
        assertEquals(false, form.options.isRequired());
    }

    public static abstract class MyForm extends BaseForm<Entity> {
        @Override
        public void fill(Entity entity) {
        }

        @Override
        public void setupEntity(Entity entity) {
        }
    }

    public static enum Options {
        A, B;
    }

    public static class Entity {
    }
}