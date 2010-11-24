package org.pirkaengine.core.util;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.pirkaengine.core.ModelDeficientPropertyException;
import org.pirkaengine.core.Template;
import org.pirkaengine.core.template.XhtmlTemplate;
import org.pirkaengine.core.util.ViewModel;

/**
 * ViewModelのユーティリティテスト.
 * @author shuji
 * @since 0.1
 */
public class ViewModelTest {
    
    /**
     * public field.
     * @throws ModelDeficientPropertyException
     */
    @Test
    public void create_public_field() throws ModelDeficientPropertyException {
        
        @SuppressWarnings("serial")
        Template template = new XhtmlTemplate("test") {
            @Override
            public Map<String, Object> createViewModel() {
                HashMap<String, Object> model = new HashMap<String, Object>();
                model.put("name", new Object());
                return model;
            }
        };
        Object model = new Object() {
            @SuppressWarnings("unused")
            public String name = "Pirka";
        };
        HashMap<String, Object> expected = new HashMap<String, Object>();
        expected.put("name", "Pirka");
        Assert.assertEquals(expected, ViewModel.create(template, model));
    }

    /**
     * getter method.
     * @throws ModelDeficientPropertyException
     */
    @Test
    public void create_getter() throws ModelDeficientPropertyException {
        
        @SuppressWarnings("serial")
        Template template = new XhtmlTemplate("test") {
            @Override
            public Map<String, Object> createViewModel() {
                HashMap<String, Object> model = new HashMap<String, Object>();
                model.put("name", new Object());
                return model;
            }
        };
        Object model = new Object() {
            @SuppressWarnings("unused")
            public String getName() {return "Pirka"; }
        };
        HashMap<String, Object> expected = new HashMap<String, Object>();
        expected.put("name", "Pirka");
        Assert.assertEquals(expected, ViewModel.create(template, model));
    }

    /**
     * getter method.
     * @throws ModelDeficientPropertyException
     */
    @Test
    public void create_method() throws ModelDeficientPropertyException {
        
        @SuppressWarnings("serial")
        Template template = new XhtmlTemplate("test") {
            @Override
            public Map<String, Object> createViewModel() {
                HashMap<String, Object> model = new HashMap<String, Object>();
                model.put("name", new Object());
                return model;
            }
        };
        Object model = new Object() {
            @SuppressWarnings("unused")
            public String name() {return "Pirka"; }
        };
        HashMap<String, Object> expected = new HashMap<String, Object>();
        expected.put("name", "Pirka");
        Assert.assertEquals(expected, ViewModel.create(template, model));
    }
    
    
    /**
     * public field.
     * @throws ModelDeficientPropertyException
     */
    @Test
    public void create_composite() throws ModelDeficientPropertyException {
        
        @SuppressWarnings("serial")
        Template template = new XhtmlTemplate("test") {
            @Override
            public Map<String, Object> createViewModel() {
                HashMap<String, Object> model = new HashMap<String, Object>();
                model.put("name", new Object());
                model.put("price", new Object());
                model.put("desc", new Object());
                return model;
            }
        };
        Object model = new Object() {
            @SuppressWarnings("unused")
            public String name = "Pirka";
            @SuppressWarnings("unused")
            public Integer getPrice() { return 0; }
            @SuppressWarnings("unused")
            public String desc() { return "Template Engine"; }
        };
        HashMap<String, Object> expected = new HashMap<String, Object>();
        expected.put("name", "Pirka");
        expected.put("price", 0);
        expected.put("desc", "Template Engine");
        Assert.assertEquals(expected, ViewModel.create(template, model));
    }    
    
    /**
     * public field.
     * @throws ModelDeficientPropertyException
     */
    @Test
    public void create_nest() throws ModelDeficientPropertyException {
        
        @SuppressWarnings("serial")
        Template template = new XhtmlTemplate("test") {
            @Override
            public Map<String, Object> createViewModel() {
                HashMap<String, Object> nest = new HashMap<String, Object>();
                nest.put("name", new Object());
                HashMap<String, Object> model = new HashMap<String, Object>();
                model.put("item", nest);
                model.put("active", new Object());
                return model;
            }
        };
        Object model = new Object() {
            @SuppressWarnings("unused")
            public boolean active = true;
            @SuppressWarnings("unused")
            public Object item = new Object() {
                @SuppressWarnings("unused")
                public String name = "Pirka";
            };
        };
        HashMap<String, Object> nest = new HashMap<String, Object>();
        nest.put("name", "Pirka");
        HashMap<String, Object> expected = new HashMap<String, Object>();
        expected.put("item", nest);
        expected.put("active", true);
        Assert.assertEquals(expected, ViewModel.create(template, model));
    }    
    
    /**
     * プロパティが不足した場合
     * @throws ModelDeficientPropertyException
     */
    @Test(expected = ModelDeficientPropertyException.class)
    public void create_no_property() throws ModelDeficientPropertyException {
        @SuppressWarnings("serial")
        Template template = new XhtmlTemplate("test") {
            @Override
            public Map<String, Object> createViewModel() {
                HashMap<String, Object> model = new HashMap<String, Object>();
                model.put("name", new Object());
                return model;
            }
        };
        Object model = new Object();
        ViewModel.create(template, model);
    }

}
