package org.pirkaengine.core.template;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.pirkaengine.core.template.TextNode;

public class TextNodeTest {

    private TextNode target;

    @Before
    public void setup() {
        target = new TextNode("test");
    }

    @Test
    public void equals() {
        TextNode other = new TextNode("test");
        assertEquals(target.hashCode(), other.hashCode());
        assertTrue(target.equals(other));
    }

    @Test
    public void equals_false() {
        TextNode other = new TextNode("test2");
        assertFalse(target.equals(other));
    }

    @Test
    public void equals_null() {
        assertFalse(target.equals(null));
    }

    @Test
    public void equals_same() {
        assertTrue(target.equals(target));
    }

}
