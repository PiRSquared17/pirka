package org.pirkaengine.core;

import static org.pirkaengine.core.TestUtil.*;

import java.util.ArrayList;


import org.junit.Test;
import org.pirkaengine.core.PirkaLoadException;
import org.pirkaengine.core.Template;
import org.pirkaengine.core.TemplateNotFoundException;
import org.pirkaengine.core.parser.ParseException;

/**
 * prk:if テスト
 * @author shuji
 */
public class RenderTest_condition extends RenderTest {
    
    @Test
    public void render_ConditionTrue() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "ConditionTrue";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("display", true);
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

    @Test
    public void render_ConditionFalse() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "ConditionFalse";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("display", false);
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

    @Test
    public void render_ConditionNotTrue() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "ConditionNotTrue";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("display", true);
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

    @Test
    public void render_ConditionNotFalse() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "ConditionNotFalse";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("display", false);
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }
    
    @Test
    public void render_ConditionEmptyTrue_List() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "ConditionEmptyTrue";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("items", new ArrayList<String>());
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

    @Test
    public void render_ConditionEmptyFalse_List() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "ConditionEmptyFalse";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        ArrayList<String> list = new ArrayList<String>();
        list.add("Item");
        viewModel.put("items", list);
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }
    
    @Test
    public void render_ConditionEmptyTrue_Array() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "ConditionEmptyTrue";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("items", new String[0]);
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

    @Test
    public void render_ConditionEmptyFalse_Array() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "ConditionEmptyFalse";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("items", new String[] {"Item"});
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }
    
    @Test
    public void render_ConditionNotEmptyTrue_List() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "ConditionNotEmptyTrue";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        ArrayList<String> list = new ArrayList<String>();
        list.add("Item");
        viewModel.put("items", list);
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

    @Test
    public void render_ConditionNotEmptyFalse_List() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "ConditionNotEmptyFalse";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("items", new ArrayList<String>());
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }
    
    @Test
    public void render_ConditionNotEmptyTrue_Array() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "ConditionNotEmptyTrue";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("items", new String[] {"Item"});
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

    @Test
    public void render_ConditionNotEmptyFalse_Array() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "ConditionNotEmptyFalse";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("items", new String[0]);
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }
    
    /**
     * ネスとしたprk:if テスト
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test
    public void render_ConditionNest() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "ConditionNest";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("display", true);
        viewModel.put("title", "レンダリングテスト");
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

}
