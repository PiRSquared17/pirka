package org.pirkaengine.core;

import static org.pirkaengine.core.TestUtil.*;

import org.junit.Test;
import org.pirkaengine.core.parser.ParseException;

/**
 * prk:valのテストケース
 * @author shuji.w6e
 */
public class RenderTest_prkVal extends RenderTest {

    @Test
    public void render_PrkVal() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "PrkVal";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        String actual = tmpl.generate().render();
        assertRenderEquals(templateName, actual);
    }

    @Test
    public void render_PrkVal_with_Expression() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "PrkVal_with_Expression";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("lang", "Java");
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }
}
