package org.pirkaengine.core.template;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import org.pirkaengine.core.template.ConditionNode;
import org.pirkaengine.core.template.EndTagNode;
import org.pirkaengine.core.template.Node;
import org.pirkaengine.core.template.StartTagNode;
import org.pirkaengine.core.template.TextNode;

public class ConditionNodeTest {

    private ConditionNode target;

    @Before
    public void setup() {
        Node[] nodes = new Node[1];
        nodes[0] = new TextNode("text");
        target = new ConditionNode(new StartTagNode("<p>", "prk:if", "display"), new EndTagNode("</p>"), nodes);
    }

    @Test
    public void equals_null() {
        assertFalse(target.equals(null));
    }

    @Test
    public void equals_illegalClass() {
        assertFalse(target.equals(new Object()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_constructor() {
        Node[] nodes = new Node[1];
        nodes[0] = new TextNode("text");
        new ConditionNode(null, new EndTagNode("</p>"), nodes);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_constructor2() {
        Node[] nodes = new Node[1];
        nodes[0] = new TextNode("text");
        new ConditionNode(new StartTagNode("<p>", "prk:if", "display"), null, nodes);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_constructor3() {
        new ConditionNode(new StartTagNode("<p>", "prk:if", "display"), new EndTagNode("</p>"), null);
    }
}
