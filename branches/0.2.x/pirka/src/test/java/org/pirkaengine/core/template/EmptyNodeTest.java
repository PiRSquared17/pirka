package org.pirkaengine.core.template;

import static org.junit.Assert.*;

import java.util.HashMap;


import org.junit.Before;
import org.junit.Test;
import org.pirkaengine.core.expression.Function;
import org.pirkaengine.core.template.EmptyNode;
import org.pirkaengine.core.template.EndTagNode;
import org.pirkaengine.core.template.Node;
import org.pirkaengine.core.template.StartTagNode;
import org.pirkaengine.core.template.TextNode;

public class EmptyNodeTest {
    private EmptyNode target;
    
    /**
     * setup.
     */
    @Before
    public void setup() {
        Node[] nodes = new Node[1];
        nodes[0] = new TextNode("hoge");
        target = new EmptyNode(new StartTagNode("<p>", "prk:mock", "true"), new EndTagNode("</p>"), nodes);
    }
    
    @Test
    public void getText() {
        assertEquals("", target.getText(new HashMap<String, Object>(), new HashMap<String, Function>()));
    }

    @Test
    public void equals_null() {
        assertFalse(target.equals(null));
    }
    
    @Test
    public void equals_illegalClass() {
        assertFalse(target.equals(new Object()));
    }

    @Test
    public void equals_same() {
        assertTrue(target.equals(target));
    }

    @Test
    public void equals_true() {
        Node[] nodes = new Node[1];
        nodes[0] = new TextNode("hoge");
        EmptyNode node = new EmptyNode(new StartTagNode("<p>", "prk:mock", "true"), new EndTagNode("</p>"), nodes);
        assertEquals(node.hashCode(), target.hashCode());
        assertTrue(node.equals(target));
    }
    

}
