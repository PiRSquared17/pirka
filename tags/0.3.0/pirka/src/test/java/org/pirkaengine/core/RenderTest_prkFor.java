package org.pirkaengine.core;

import static org.pirkaengine.core.TestUtil.*;

import org.junit.Test;
import org.pirkaengine.core.parser.ParseException;

/**
 * prk:Forのテストケース
 * @author shuji.w6e
 */
public class RenderTest_prkFor extends RenderTest {

    @Test
    public void render_PrkFor_Array() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "PrkFor";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("items", new String[] { "Apple", "Orange", "Pine" });
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }
}
