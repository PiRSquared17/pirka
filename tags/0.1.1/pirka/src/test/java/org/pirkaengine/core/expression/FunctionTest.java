package org.pirkaengine.core.expression;

import static org.junit.Assert.*;

import org.junit.Test;
import org.pirkaengine.core.IllegalFormatException;
import org.pirkaengine.core.expression.Function;
import org.pirkaengine.core.expression.ScriptFunction;

public class FunctionTest {

    @Test
    public void create_one_param() {
        Function actual = ScriptFunction.create("javascript", "hoge(foo)", "return 0;");
        Function expected = new Function("javascript", "return 0;", "hoge", new String[] { "foo" });
        assertEquals(expected, actual);
    }

    @Test
    public void create_two_params() {
        Function actual = ScriptFunction.create("javascript", "hoge(foo,bar)", "return 0;");
        Function expected = new Function("javascript", "return 0;", "hoge", new String[] { "foo", "bar" });
        assertEquals(expected, actual);
    }

    @Test
    public void create_no_params() {
        Function actual = ScriptFunction.create("groovy", "huga()", "return 1;");
        Function expected = new Function("groovy", "return 1;", "huga");
        assertEquals(expected, actual);
    }

    @Test(expected = IllegalFormatException.class)
    public void create_format_error() {
        ScriptFunction.create("groovy", "huga", "return 1;");
    }

    @Test
    public void equals_null() {
        ScriptFunction func = ScriptFunction.create("groovy", "huga()", "return 1;");
        assertFalse(func.equals(null));
    }

    @Test
    public void equals_same() {
        ScriptFunction func = ScriptFunction.create("groovy", "huga()", "return 1;");
        assertTrue(func.equals(func));
    }

    @Test
    public void equals_true() {
        ScriptFunction func = ScriptFunction.create("groovy", "huga()", "return 1;");
        Function func2 = ScriptFunction.create("groovy", "huga()", "return 1;");
        assertEquals(func.hashCode(), func2.hashCode());
        assertTrue(func.equals(func2));
    }

    @Test
    public void equals_lang() {
        ScriptFunction func = ScriptFunction.create("groovy", "huga()", "return 1;");
        ScriptFunction func2 = ScriptFunction.create("javascript", "huga()", "return 1;");
        assertFalse(func.equals(func2));
    }

    @Test
    public void equals_name() {
        ScriptFunction func = ScriptFunction.create("groovy", "huga()", "return 1;");
        ScriptFunction func2 = ScriptFunction.create("groovy", "hoga()", "return 1;");
        assertFalse(func.equals(func2));
    }

    @Test
    public void equals_params() {
        ScriptFunction func = ScriptFunction.create("groovy", "huga(bar)", "return 0;");
        ScriptFunction func2 = ScriptFunction.create("groovy", "huga()", "return 0;");
        assertFalse(func.equals(func2));
    }

    @Test
    public void equals_script() {
        ScriptFunction func = ScriptFunction.create("groovy", "huga()", "return 0;");
        ScriptFunction func2 = ScriptFunction.create("groovy", "hoga()", "return 1;");
        assertFalse(func.equals(func2));
    }

}
