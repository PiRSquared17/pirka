package org.pirkaengine.form;

import static org.junit.Assert.*;

import org.junit.Test;
import org.pirkaengine.form.field.DateField;
import org.pirkaengine.form.field.SelectEnumField;
import org.pirkaengine.form.field.TextField;

public class BaseFormTest {

    public static enum Options {
        A, B;
    }

    public static class Entity {

    }

    public static class MyForm extends BaseForm<Entity> {

        public TextField field1 = new TextField();

        @Label("フィールド２")
        @Required(true)
        public TextField field2 = new TextField();

        public DateField field3 = new DateField();

        public SelectEnumField<Options> field4 = new SelectEnumField<Options>(Options.class, Options.values());
        
        public String notField;
        
        @SuppressWarnings("unused")
        private TextField priveteField;
        
        @Override
        public void fill(Entity entity) {
        }

        @Override
        public void setupEntity(Entity entity) {
        }

    }

    MyForm target;

    @Test
    public void newForm_fields() {
        MyForm form = MyForm.newForm(MyForm.class);
        assertEquals(4, form.fields.size());
    }

    @Test
    public void newForm_field1() {
        MyForm form = MyForm.newForm(MyForm.class);
        assertEquals("field1", form.field1.name);
        assertEquals("field1", form.field1.label);
        assertEquals(false, form.field1.isRequired());
    }

    @Test
    public void newForm_field2() {
        MyForm form = MyForm.newForm(MyForm.class);
        assertEquals("field2", form.field2.name);
        assertEquals("フィールド２", form.field2.label);
        assertEquals(true, form.field2.isRequired());
    }

    @Test
    public void newForm_field3() {
        MyForm form = MyForm.newForm(MyForm.class);
        assertEquals("field3", form.field3.name);
        assertEquals("field3", form.field3.label);
        assertEquals(false, form.field3.isRequired());
    }

    @Test
    public void newForm_field4() {
        MyForm form = MyForm.newForm(MyForm.class);
        assertEquals("field4", form.field4.name);
        assertEquals("field4", form.field4.label);
        assertEquals(false, form.field4.isRequired());
    }

}
