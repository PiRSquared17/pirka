package org.pirkaengine.core.template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import org.junit.Assert;
import org.junit.Test;
import org.pirkaengine.core.KeyNotContainsException;
import org.pirkaengine.core.template.ConditionNode;
import org.pirkaengine.core.template.EndTagNode;
import org.pirkaengine.core.template.ExpressionNode;
import org.pirkaengine.core.template.LoopNode;
import org.pirkaengine.core.template.Node;
import org.pirkaengine.core.template.RepeatNode;
import org.pirkaengine.core.template.StartTagNode;
import org.pirkaengine.core.template.XhtmlTemplate;

/**
 *
 * @author shuji
 */
public class XhtmlTemplateTest {

    @Test
    public void createViewModel_simple() {
        XhtmlTemplate template = new XhtmlTemplate("test");
        template.stack(new ExpressionNode("foo"));
        Map<String, Object> expected = new HashMap<String, Object>();
        expected.put("foo", XhtmlTemplate.NULL_VALUE);
        Assert.assertEquals(expected, template.createViewModel());
    }

    @Test
    public void createViewModel_2_expressions() {
        XhtmlTemplate template = new XhtmlTemplate("test");
        template.stack(new ExpressionNode("foo"));
        template.stack(new ExpressionNode("bar"));
        Map<String, Object> expected = new HashMap<String, Object>();
        expected.put("foo", XhtmlTemplate.NULL_VALUE);
        expected.put("bar", XhtmlTemplate.NULL_VALUE);
        Assert.assertEquals(expected, template.createViewModel());
    }

    @Test
    public void createViewModel_nest_case() {
        XhtmlTemplate template = new XhtmlTemplate("test");
        template.stack(new ExpressionNode("foo.bar"));
        Map<String, Object> expected = new HashMap<String, Object>();
        Map<String, Object> foo = new HashMap<String, Object>();
        foo.put("bar", XhtmlTemplate.NULL_VALUE);
        expected.put("foo", foo);
        Assert.assertEquals(expected, template.createViewModel());
    }

    @Test
    public void createViewModel_composite_case() {
        XhtmlTemplate template = new XhtmlTemplate("test");
        template.stack(new ExpressionNode("foo.bar"));
        template.stack(new ExpressionNode("poo"));
        Map<String, Object> expected = new HashMap<String, Object>();
        Map<String, Object> foo = new HashMap<String, Object>();
        foo.put("bar", XhtmlTemplate.NULL_VALUE);
        expected.put("foo", foo);
        expected.put("poo", XhtmlTemplate.NULL_VALUE);
        Assert.assertEquals(expected, template.createViewModel());
    }

    @Test
    public void createViewModel_array() {
        XhtmlTemplate template = new XhtmlTemplate("test");
        StartTagNode start = new StartTagNode("li", "prk:for", "item in items");
        EndTagNode end = new EndTagNode("li");
        template.stack(new LoopNode(start, end, new Node[0]));
        Map<String, Object> expected = new HashMap<String, Object>();
        expected.put("items", new ArrayList<Map<String, Object>>());
        Assert.assertEquals(expected, template.createViewModel());
        Assert.assertEquals(new HashMap<String, Object>(), template.createArrayItemModel("items"));
    }

    @Test
    public void createViewModel_array_simple() {
        XhtmlTemplate template = new XhtmlTemplate("test");
        StartTagNode start = new StartTagNode("li", "prk:for", "item in items");
        EndTagNode end = new EndTagNode("li");
        ExpressionNode node = new ExpressionNode("item");
        template.stack(new LoopNode(start, end, new Node[] { node }));

        Map<String, Object> expected = new HashMap<String, Object>();
        expected.put("items", new ArrayList<Map<String, Object>>());
        Assert.assertEquals(expected, template.createViewModel());

        Map<String, Object> expectedItem = new HashMap<String, Object>();
        expectedItem.put("toString", XhtmlTemplate.NULL_VALUE);
        Assert.assertEquals(expectedItem, template.createArrayItemModel("items"));
    }

    @Test
    public void createViewModel_array_with_obj() {
        XhtmlTemplate template = new XhtmlTemplate("test");
        StartTagNode start = new StartTagNode("li", "prk:for", "item in items");
        EndTagNode end = new EndTagNode("li");
        ExpressionNode node = new ExpressionNode("item.name");
        template.stack(new LoopNode(start, end, new Node[] { node }));

        Map<String, Object> expected = new HashMap<String, Object>();
        expected.put("items", new ArrayList<Map<String, Object>>());
        Assert.assertEquals(expected, template.createViewModel());

        Map<String, Object> expectedItem = new HashMap<String, Object>();
        expectedItem.put("name", XhtmlTemplate.NULL_VALUE);
        Assert.assertEquals(expectedItem, template.createArrayItemModel("items"));
    }

    @Test
    public void createViewModel() {
        XhtmlTemplate template = new XhtmlTemplate("test");
        StartTagNode start = new StartTagNode("li", "prk:for", "item in items");
        EndTagNode end = new EndTagNode("li");
        ExpressionNode node1 = new ExpressionNode("item.name");
        ExpressionNode node2 = new ExpressionNode("item.price");
        template.stack(new LoopNode(start, end, new Node[] { node1, node2 }));
        template.stack(new ExpressionNode("foo.bar"));
        template.stack(new ExpressionNode("poo"));

        Map<String, Object> expected = new HashMap<String, Object>();
        expected.put("items", new ArrayList<Map<String, Object>>());
        Map<String, Object> foo = new HashMap<String, Object>();
        foo.put("bar", XhtmlTemplate.NULL_VALUE);
        expected.put("foo", foo);
        expected.put("poo", XhtmlTemplate.NULL_VALUE);
        Assert.assertEquals(expected, template.createViewModel());

        Map<String, Object> expectedItem = new HashMap<String, Object>();
        expectedItem.put("name", XhtmlTemplate.NULL_VALUE);
        expectedItem.put("price", XhtmlTemplate.NULL_VALUE);
        Assert.assertEquals(expectedItem, template.createArrayItemModel("items"));
    }

    @Test
    public void createViewModel_condition() {
        XhtmlTemplate template = new XhtmlTemplate("test");
        StartTagNode start = new StartTagNode("div", "prk:if", "cond");
        EndTagNode end = new EndTagNode("div");
        template.stack(new ConditionNode(start, end, new Node[] {}));
        Map<String, Object> expected = new HashMap<String, Object>();
        expected.put("cond", XhtmlTemplate.NULL_VALUE);
        Assert.assertEquals(expected, template.createViewModel());
    }

    @Test
    public void createViewModel_condition_with_expression() {
        XhtmlTemplate template = new XhtmlTemplate("test");
        StartTagNode start = new StartTagNode("div", "prk:if", "cond");
        EndTagNode end = new EndTagNode("div");
        ExpressionNode node = new ExpressionNode("name");
        template.stack(new ConditionNode(start, end, new Node[] { node }));
        Map<String, Object> expected = new HashMap<String, Object>();
        expected.put("cond", XhtmlTemplate.NULL_VALUE);
        expected.put("name", XhtmlTemplate.NULL_VALUE);
        Assert.assertEquals(expected, template.createViewModel());
    }

    @Test
    public void createViewModel_repeat() {
        XhtmlTemplate template = new XhtmlTemplate("test");
        StartTagNode start = new StartTagNode("span", "prk:repeat", "count");
        EndTagNode end = new EndTagNode("span");
        template.stack(new RepeatNode(start, end, new Node[] {}));
        Map<String, Object> expected = new HashMap<String, Object>();
        expected.put("count", XhtmlTemplate.NULL_VALUE);
        Assert.assertEquals(expected, template.createViewModel());
    }

    @Test(expected = KeyNotContainsException.class)
    public void createViewModel_KeyNotContains() {
        XhtmlTemplate template = new XhtmlTemplate("test");
        template.createArrayItemModel("hoge");
    }
}
