package org.pirkaengine.core.template;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.pirkaengine.core.expression.Function;

/**
 * StartTagNodeTest
 * @author shuji
 */
public class StartTagNodeTest {
    private StartTagNode target;
    private HashMap<String, String> prkAttrs;
    private HashMap<String, String> pathAttrs;
    private HashMap<String, String> attrs;
    private HashMap<String, Object> viewModel;
    private HashMap<String, Function> functions;
    
    /** setup */
    @Before
    public void setup() {
        Node[] nodes = new Node[1];
        nodes[0] = new TextNode("text");
        prkAttrs = new HashMap<String, String>();
        pathAttrs = new HashMap<String, String>();
        attrs = new HashMap<String, String>();
        viewModel = new HashMap<String, Object>();
        functions = new HashMap<String, Function>();
    }
    
    @Test
    public void getText_when_true_widh_attr() {
        prkAttrs.put("attr.selected.when", "cond");
        attrs.put("selected", "selected");
        target = new StartTagNode("<option>", "attr", "attr", prkAttrs, pathAttrs, attrs);
        viewModel.put("cond", true);
        assertEquals("<option selected=\"selected\">", target.getText(viewModel, functions));
    }
    
    @Test
    public void getText_when_true_widh_prkattr() {
        prkAttrs.put("attr.selected.when", "cond");
        prkAttrs.put("attr.selected", "#selected");
        target = new StartTagNode("<option>", "attr", "attr", prkAttrs, pathAttrs, attrs);
        viewModel.put("cond", true);
        assertEquals("<option selected=\"selected\">", target.getText(viewModel, functions));
    }
    
    @Test
    public void getText_when_true_but_no_attr() {
        prkAttrs.put("attr.selected.when", "cond");
        target = new StartTagNode("<option>", "attr", "attr", prkAttrs, pathAttrs, attrs);
        viewModel.put("cond", true);
        assertEquals("<option>", target.getText(viewModel, functions));
    }

    @Test
    public void getText_when_false() {
        prkAttrs.put("attr.selected.when", "cond");
        attrs.put("selected", "selected");
        target = new StartTagNode("<option>", "attr", "attr", prkAttrs, pathAttrs, attrs);
        viewModel.put("cond", false);
        assertEquals("<option>", target.getText(viewModel, functions));
    }
}
