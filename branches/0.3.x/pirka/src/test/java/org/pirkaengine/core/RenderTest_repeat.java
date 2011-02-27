package org.pirkaengine.core;

import static org.pirkaengine.core.TestUtil.*;

import org.junit.Test;
import org.pirkaengine.core.parser.ParseException;

/**
 * Repeat属性テスト
 * @author shuji
 * @since 1.0
 */
public class RenderTest_repeat extends RenderTest {

    @Test
    public void render_Repeat() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "Repeat";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("rate", 4);
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

    @Test
    public void render_Repeat_num() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "RepeatNum";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        String actual = tmpl.generate().render();
        assertRenderEquals(templateName, actual);
    }

    @Test(expected = RenderException.class)
    public void render_Repeat_NOT_num() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "Repeat";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("rate", "string");
        tmpl.generate(viewModel).render();
    }

    @Test(expected = RenderException.class)
    public void render_Repeat_NOT_EXIST() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "Repeat";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        tmpl.generate().render();
    }
}
