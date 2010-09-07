package org.pirkaengine.core.template;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import org.pirkaengine.core.template.ContentNode;
import org.pirkaengine.core.template.EndTagNode;
import org.pirkaengine.core.template.Node;
import org.pirkaengine.core.template.StartTagNode;
import org.pirkaengine.core.template.TextNode;

/**
 * ContentNodeTest
 * @author shuji
 */
public class ContentNodeTest {
    private ContentNode target;
    
    /**
     * setup.
     */
    @Before
    public void setup() {
        Node[] nodes = new Node[1];
        nodes[0] = new TextNode("hoge");
        target = new ContentNode(new StartTagNode("<p>", "prk:content", "text"), new EndTagNode("</p>"), nodes);
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
        ContentNode node = new ContentNode(new StartTagNode("<p>", "prk:content", "text"), new EndTagNode("</p>"), nodes);
        assertEquals(node.hashCode(), target.hashCode());
        assertTrue(node.equals(target));
    }



}
