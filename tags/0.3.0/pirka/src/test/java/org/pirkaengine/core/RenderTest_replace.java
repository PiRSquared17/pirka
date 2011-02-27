package org.pirkaengine.core;

import static org.pirkaengine.core.TestUtil.*;

import org.junit.Test;
import org.pirkaengine.core.PirkaLoadException;
import org.pirkaengine.core.Template;
import org.pirkaengine.core.TemplateNotFoundException;
import org.pirkaengine.core.parser.ParseException;

/**
 * prk:replace テスト
 * @author shuji
 */
public class RenderTest_replace extends RenderTest {

    /**
     * 正常系
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test
    public void render_prk_replace() throws ParseException, PirkaLoadException, TemplateNotFoundException {
        String templateName = "Replace";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("text", "置換テキスト");
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

    /**
     * タグ版
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test
    public void render_prk_replace_tag() throws ParseException, PirkaLoadException, TemplateNotFoundException {
        String templateName = "ReplaceTag";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("text", "置換テキスト");
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

    /**
     * タグ版
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test(expected = ParseException.class)
    public void render_prk_replace_tag_no_value() throws ParseException, PirkaLoadException, TemplateNotFoundException {
        String templateName = "ReplaceTagNoValue";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("text", "置換テキスト");
        tmpl.generate(viewModel).render();
    }

    /**
     * 空タグ
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test
    public void render_prk_replace_empty_tag() throws ParseException, PirkaLoadException, TemplateNotFoundException {
        String templateName = "ReplaceEmptyTag";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("text", "テキスト");
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }
}
