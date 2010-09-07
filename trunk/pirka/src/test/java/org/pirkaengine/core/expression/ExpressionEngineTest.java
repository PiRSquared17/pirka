package org.pirkaengine.core.expression;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.pirkaengine.core.TestUtil.*;

import java.util.ArrayList;
import java.util.HashMap;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.pirkaengine.core.Category;
import org.pirkaengine.core.Item;
import org.pirkaengine.core.expression.EvalException;
import org.pirkaengine.core.expression.ExpressionEngine;

public class ExpressionEngineTest {

    private ExpressionEngine target;

    @Before
    public void setup() {
        target = new ExpressionEngine();
    }

    @Test(expected = IllegalArgumentException.class)
    public void eval_null() {
        target.eval(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void eval_nullModel() {
        target.eval("", null);
    }

    @Test
    public void eval_empty_expression() {
        Assert.assertEquals("", target.eval(""));
    }

    @Test
    public void eval_single_value() {
        HashMap<String, Object> model = getModel("title", "タイトル");
        Assert.assertEquals("タイトル", target.eval("title", model));
    }

    @Test
    public void eval_getter() {
        Category category = new Category();
        category.id = 10;
        Item item = new Item();
        item.name = "名称";
        item.category = category;
        HashMap<String, Object> model = getModel("item", item);
        Assert.assertEquals("名称", target.eval("item.name", model));
        Assert.assertEquals("10", target.eval("item.category.id", model));
    }

    @Test
    public void eval_getter_null() {
        Item item = new Item();
        HashMap<String, Object> model = getModel("item", item);
        Assert.assertEquals("", target.eval("item.nullValue", model));
    }

    @Test
    public void eval_public_property() {
        Item item = new Item();
        item.price = 3500;
        HashMap<String, Object> model = getModel("item", item);
        Assert.assertEquals("3,500", target.eval("item.labeledPrice", model));
    }

    @Test
    public void eval_public_property_null() {
        Item item = new Item();
        item.name = null;
        HashMap<String, Object> model = getModel("item", item);
        Assert.assertEquals("", target.eval("item.name", model));
    }

    @Test
    public void eval_public_method() {
        Item item = new Item();
        item.id = 1;
        HashMap<String, Object> model = getModel("item", item);
        Assert.assertEquals("1", target.eval("item.id", model));
    }

    @Test
    public void eval_public_method_null() {
        Item item = new Item();
        HashMap<String, Object> model = getModel("item", item);
        Assert.assertEquals("", target.eval("item.methodNull", model));
    }

    @Test
    public void eval_map() {
        HashMap<String, Object> item = new HashMap<String, Object>();
        item.put("name", "名称");
        item.put("price", 3500);
        HashMap<String, Object> model = getModel("item", item);
        Assert.assertEquals("名称", target.eval("item.name", model));
        Assert.assertEquals("3500", target.eval("item.price", model));
    }

    @Test(expected = EvalException.class)
    public void eval_public_none_property() {
        Item item = new Item();
        HashMap<String, Object> model = getModel("item", item);
        target.eval("item.xx", model);
    }

    @Test(expected = EvalException.class)
    public void eval_private_property() {
        Item item = new Item();
        HashMap<String, Object> model = getModel("item", item);
        target.eval("item.privateValue", model);
    }

    @Test(expected = EvalException.class)
    public void eval_private_getter() {
        Item item = new Item();
        HashMap<String, Object> model = getModel("item", item);
        target.eval("item.privateValue", model);
    }

    @Test(expected = EvalException.class)
    public void eval_map_not_found() {
        HashMap<String, Object> item = new HashMap<String, Object>();
        HashMap<String, Object> model = getModel("item", item);
        target.eval("item.name", model);
    }

    @Test
    public void eval_string_expression_back() {
        HashMap<String, Object> model = getModel("value", 1);
        Assert.assertEquals("value=1", target.eval("value=${value}", model));
    }

    @Test
    public void eval_string_expression_front() {
        HashMap<String, Object> model = getModel("value", 1);
        Assert.assertEquals("1=value", target.eval("${value}=value", model));
    }

    @Test
    public void eval_string_expression_double() {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("foo", "FOO");
        model.put("bar", "BAR");
        Assert.assertEquals("FOO,BAR", target.eval("${foo},${bar}", model));
    }

    /**
     * #で始まる場合は単純な文字列として扱う
     */
    @Test
    public void eval_string_hello() {
        Assert.assertEquals("hello", target.eval("#hello"));
    }

    /**
     * #で始まる場合は単純な文字列として扱う
     */
    @Test
    public void eval_string_123() {
        Assert.assertEquals("123", target.eval("#123"));
    }

    /**
     * #で始まる場合は単純な文字列として扱う
     */
    @Test
    public void eval_string_sharponly() {
        Assert.assertEquals("", target.eval("#"));
    }

    /**
     */
    @Test
    public void eval_empty() {
        Assert.assertEquals("", target.eval(""));
    }

    /**
     * 真偽値評価で空文字列はfalse
     */
    @Test
    public void evalBoolean_emptyStr() {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("foo", "");
        assertThat(target.evalBoolean("foo", model), is(false));
    }

    /**
     * falseという文字列はfalse
     */
    @Test
    public void evalBoolean_false() {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("foo", "false");
        assertThat(target.evalBoolean("foo", model), is(false));
    }

    /**
     * FALSEという文字列はfalse
     */
    @Test
    public void evalBoolean_FALSE() {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("foo", "FALSE");
        assertThat(target.evalBoolean("foo", model), is(false));
    }

    /**
     * 真偽値評価で0はfalse
     */
    @Test
    public void evalBoolean_0() {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("foo", 0);
        assertThat(target.evalBoolean("foo", model), is(false));
    }

    /**
     * 真偽値評価で0Lはfalse
     */
    @Test
    public void evalBoolean_0L() {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("foo", new Long(0));
        assertThat(target.evalBoolean("foo", model), is(false));
    }

    /**
     * 真偽値評価で0fはfalse
     */
    @Test
    public void evalBoolean_0F() {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("foo", new Float(0.0f));
        assertThat(target.evalBoolean("foo", model), is(false));
    }

    /**
     * 真偽値評価で0dはfalse
     */
    @Test
    public void evalBoolean_0D() {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("foo", new Double(0.0f));
        assertThat(target.evalBoolean("foo", model), is(false));
    }

    /**
     * 真偽値評価でサイズ1以上の文字列はtrue
     */
    @Test
    public void evalBoolean_str() {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("foo", "_");
        assertThat(target.evalBoolean("foo", model), is(true));
    }

    /**
     * 真偽値評価で1はtrue
     */
    @Test
    public void evalBoolean_1() {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("foo", 1);
        assertThat(target.evalBoolean("foo", model), is(true));
    }

    /**
     * 真偽値評価で1はtrue
     */
    @Test
    public void evalBoolean_1L() {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("foo", new Long(1));
        assertThat(target.evalBoolean("foo", model), is(true));
    }

    /**
     * 真偽値評価で1はtrue
     */
    @Test
    public void evalBoolean_1F() {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("foo", new Float(1.0f));
        assertThat(target.evalBoolean("foo", model), is(true));
    }

    /**
     * 真偽値評価で1はtrue
     */
    @Test
    public void evalBoolean_1D() {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("foo", new Double(1.0d));
        assertThat(target.evalBoolean("foo", model), is(true));
    }

    /**
     * 1==1
     */
    @Test
    public void evalBoolean_equals() {
        assertThat(target.evalBoolean("1==1"), is(true));
    }

    /**
     * 真偽評価：文字列の比較でtrue
     */
    @Test
    public void evalBoolean_equals_string_true() {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("foo", "Hello");
        model.put("bar", "Hello");
        assertThat(target.evalBoolean("foo==bar", model), is(true));
    }

    /**
     * 真偽評価：文字列の比較でfalse
     */
    @Test
    public void evalBoolean_equals_string_false() {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("foo", "Hello");
        model.put("bar", "HELLO");
        assertThat(target.evalBoolean("foo==bar", model), is(false));
    }

    /**
     * 真偽値評価でサイズ0の配列はfalse
     */
    @Test
    public void evalBoolean_array0() {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("foo", new String[0]);
        assertThat(target.evalBoolean("foo", model), is(false));
    }

    /**
     * 真偽値評価でサイズ1の配列はtrue
     */
    @Test
    public void evalBoolean_array1() {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("foo", new String[1]);
        assertThat(target.evalBoolean("foo", model), is(true));
    }

    /**
     * 真偽値評価でサイズ0のListはfalse
     */
    @Test
    public void evalBoolean_list0() {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("foo", new ArrayList<Object>());
        assertThat(target.evalBoolean("foo", model), is(false));
    }

    /**
     * 真偽値評価でサイズ1のListはtrue
     */
    @Test
    public void evalBoolean_list1() {
        ArrayList<Object> list = new ArrayList<Object>();
        list.add(new Object());
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("foo", list);
        assertThat(target.evalBoolean("foo", model), is(true));
    }

    /**
     * 真偽値評価でnullはfalse
     */
    @Test
    public void evalBoolean_null() {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("foo", null);
        assertThat(target.evalBoolean("foo", model), is(false));
    }

    /**
     * 真偽値評価でtoStringがfalseの場合はfalse
     */
    @Test
    public void evalBoolean_toString_false() {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("foo", new Object() {
            @Override
            public String toString() {
                return "false";
            }
        });
        assertThat(target.evalBoolean("foo", model), is(false));
    }
    
    /**
     * 真偽値評価でtoStringがFalseの場合はfalse
     */
    @Test
    public void evalBoolean_toString_False() {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("foo", new Object() {
            @Override
            public String toString() {
                return "False";
            }
        });
        assertThat(target.evalBoolean("foo", model), is(false));
    }
    
    /**
     * 真偽値評価でtoStringが0の場合はfalse
     */
    @Test
    public void evalBoolean_toString_0() {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("foo", new Object() {
            @Override
            public String toString() {
                return "0";
            }
        });
        assertThat(target.evalBoolean("foo", model), is(false));
    }
    
    /**
     * 真偽値評価で非nullはtrue
     */
    @Test
    public void evalBoolean_someObject() {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("foo", new Object());
        assertThat(target.evalBoolean("foo", model), is(true));
    }

    @Test
    public void evalBoolean_objectField() {
        HashMap<String, Object> model = new HashMap<String, Object>();
        Item item = new Item();
        item.sale = true;
        model.put("foo", item);
        assertThat(target.evalBoolean("foo.sale", model), is(true));
    }

    @Test
    public void evalBoolean_objectField_true() {
        HashMap<String, Object> model = new HashMap<String, Object>();
        Item item = new Item();
        item.sale = true;
        model.put("foo", item);
        assertThat(target.evalBoolean("foo.sale", model), is(true));
    }

    @Test
    public void evalBoolean_objectField_false() {
        HashMap<String, Object> model = new HashMap<String, Object>();
        Item item = new Item();
        item.sale = false;
        model.put("foo", item);
        assertThat(target.evalBoolean("foo.sale", model), is(false));
    }

    @Test
    public void evalBoolean_equals_obj_true() {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("name", "Hello");
        Item item = new Item();
        item.name = "Hello";
        model.put("item", item);
        assertThat(target.evalBoolean("name==item.name", model), is(true));
    }

    @Test
    public void evalBoolean_equals_obj_false() {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("name", "Hello");
        Item item = new Item();
        item.name = "HELLO";
        model.put("item", item);
        assertThat(target.evalBoolean("name==item.name", model), is(false));
    }

    /**
     * !=
     */
    @Test
    public void evalBoolean_notequals() {
        Assert.assertEquals(false, target.evalBoolean("1!=1"));
    }

    @Test
    public void evalBoolean_notequals_obj_false() {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("name", "Hello");
        Item item = new Item();
        item.name = "Hello";
        model.put("item", item);
        Assert.assertEquals(false, target.evalBoolean("name!=item.name", model));
    }

    @Test
    public void evalBoolean_notequals_obj_true() {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("name", "Hello");
        Item item = new Item();
        item.name = "HELLO";
        model.put("item", item);
        Assert.assertEquals(true, target.evalBoolean("name!=item.name", model));
    }
}
