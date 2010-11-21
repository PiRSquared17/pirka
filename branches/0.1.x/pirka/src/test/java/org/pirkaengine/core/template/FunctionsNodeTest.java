package org.pirkaengine.core.template;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.pirkaengine.core.expression.ScriptFunction;
import org.pirkaengine.core.template.ScriptNode;

public class FunctionsNodeTest {

    private ScriptFunction func;

    @Before
    public void setup() {
        func = ScriptFunction.create("javascript", "hoge(foo)", "return 0;");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ScriptNode_null() {
        new ScriptNode(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getText() {
        ScriptNode node = new ScriptNode(func);
        node.getText(null, null);
    }

    @Test
    public void equals_null() {
        ScriptNode node = new ScriptNode(func);
        assertFalse(node.equals(null));
    }

    @Test
    public void equals_same() {
        ScriptNode node = new ScriptNode(func);
        assertTrue(node.equals(node));
    }

    @Test
    public void equals_true() {
        ScriptNode node = new ScriptNode(func);
        ScriptNode node2 = new ScriptNode(func);
        assertEquals(node.hashCode(), node2.hashCode());
        assertTrue(node.equals(node2));
    }

    @Test
    public void equals_func() {
        ScriptNode node = new ScriptNode(ScriptFunction.create("groovy", "huga()", "return 1;"));
        ScriptNode node2 = new ScriptNode(ScriptFunction.create("javascript", "huga()", "return 1;"));
        assertFalse(node.equals(node2));
    }

}
