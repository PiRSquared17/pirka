package org.pirkaengine.core;

import static org.pirkaengine.core.TestUtil.*;

import org.junit.Test;
import org.pirkaengine.core.PirkaLoadException;
import org.pirkaengine.core.Template;
import org.pirkaengine.core.TemplateNotFoundException;
import org.pirkaengine.core.parser.ParseException;

/**
 * prk:attr テスト
 * @author shuji
 */
public class RenderTest_attr extends RenderTest {

    /**
     * prk:attr.class が正しく置換されること 
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test
    public void render_prk_attr_class() throws ParseException, PirkaLoadException, TemplateNotFoundException {
        String templateName = "Attr.class";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("selected_class", "selected");
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

    /**
     * 多重prk:attr.class が正しく置換されること 
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test
    public void render_prk_attr_multiClass() throws ParseException, PirkaLoadException, TemplateNotFoundException {
        String templateName = "Attr.multiClass";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("_href", "./about.html");
        viewModel.put("_title", "会社概要");
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }


    /**
     * 複合prk:attr.class が正しく置換されること 
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test
    public void render_prk_attr_compositeClass() throws ParseException, PirkaLoadException, TemplateNotFoundException {
        String templateName = "Attr.compositeClass";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("name", "会社概要");
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }
    /**
     * prk:attr.class が正しく置換されること 
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test
    public void render_prk_attr_classReplace() throws ParseException, PirkaLoadException, TemplateNotFoundException {
        String templateName = "Attr.classReplace";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("selected_class", "selected");
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

    /**
     * prk:attr.class が正しく置換されること 
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test
    public void render_prk_attr_onclick() throws ParseException, PirkaLoadException, TemplateNotFoundException {
        String templateName = "Attr.onclick";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("script", "alert();");
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }
    
    /**
     * prk:attr.content が正しく置換されること（空要素タグ） 
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test
    public void render_prk_attr_content() throws ParseException, PirkaLoadException, TemplateNotFoundException {
        String templateName = "Attr.content";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("keywords", "pirka,テンプレートエンジン,Java,Ruby on Rails");
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }
    
    /**
     * prk:attr はParseException
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test(expected = ParseException.class)
    public void render_prk_attr() throws ParseException, PirkaLoadException, TemplateNotFoundException {
        String templateName = "Attr";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("script", "alert();");
        tmpl.generate(viewModel).render();
    }
    
}
