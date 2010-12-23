package org.pirkaengine.core;

import static org.pirkaengine.core.TestUtil.*;

import org.junit.Test;
import org.pirkaengine.core.PirkaLoadException;
import org.pirkaengine.core.Template;
import org.pirkaengine.core.TemplateNotFoundException;
import org.pirkaengine.core.parser.ParseException;

/**
 * レイアウト機能（継承）のテスト
 * @author shuji
 */
public class RenderTest_extends extends RenderTest {
    
    /**
     * 基本パターン
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test
    public void render_Extends() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "Extends";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        String actual = tmpl.generate().render();
        assertRenderEquals(templateName, actual);
    }
    
    /**
     * prk:contentの場合
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test
    public void render_Extends_content() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "Extends.content";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("message", "Hello Pirka");
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }
    
    /**
     * 評価式の場合
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test
    public void render_Extends_expression() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "Extends.expression";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("value", 2010);
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }
    
    /**
     * ループのあるケース
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test
    public void render_Extends_loop() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "Extends.loop";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("items", new String[] { "Apple", "Orange", "Pine" });
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

    /**
     * 一部、オーバーライドされないケース
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test
    public void render_Extends_default() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "Extends.default";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        String actual = tmpl.generate().render();
        assertRenderEquals(templateName, actual);
    }
    
    /**
     * prk:includeを含むケース
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test
    public void render_Extends_include() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "Extends.include";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("text", "XML Template Engine");
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }
    
    /**
     * if属性のある場合
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test
    public void render_Extends_if() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "Extends.if";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("flg", true);
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

    /**
     * for属性のある場合
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test
    public void render_Extends_for() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "Extends.for";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("items", new String[] {"Hello", "World"} );
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }


    /**
     * ifとfor属性のある場合
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test
    public void render_Extends_if_for() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "Extends.if_for";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("flg", true);
        viewModel.put("items", new String[] {"Hello", "World"} );
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }


    /**
     * for属性が２回ある場合
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test
    public void render_Extends_for2() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "Extends.for2";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("items", new String[] {"Hello", "World"} );
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

    /**
     * attr属性のある場合
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test
    public void render_Extends_attr() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "Extends.attr";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("style", "news");
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

    /**
     * 空要素のある場合
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test
    public void render_Extends_empty_tag() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "Extends.empty_tag";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("value", "aaa");
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }
}
