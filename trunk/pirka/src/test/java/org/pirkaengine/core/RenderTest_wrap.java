package org.pirkaengine.core;

import static org.pirkaengine.core.TestUtil.*;

import org.junit.Test;
import org.pirkaengine.core.PirkaLoadException;
import org.pirkaengine.core.Template;
import org.pirkaengine.core.TemplateNotFoundException;
import org.pirkaengine.core.parser.ParseException;

/**
 * prk:wrap テスト
 * @author shuji
 */
public class RenderTest_wrap extends RenderTest {
    
    /**
     * 真の時、外側のタグがレンダリングされる
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test
    public void render_prk_wrap_true() throws ParseException, PirkaLoadException, TemplateNotFoundException {
        String templateName = "Wrap";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("is_new", true);
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName + "True", actual);
    }

    /**
     * 偽の時、外側のタグがレンダリングされない
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test
    public void render_prk_wrap_false() throws ParseException, PirkaLoadException, TemplateNotFoundException {
        String templateName = "Wrap";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("is_new", false);
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName + "False", actual);
    }
}
