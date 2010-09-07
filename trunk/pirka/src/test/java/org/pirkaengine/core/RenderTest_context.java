package org.pirkaengine.core;

import static org.pirkaengine.core.TestUtil.*;

import org.junit.Test;
import org.pirkaengine.core.PirkaLoadException;
import org.pirkaengine.core.Template;
import org.pirkaengine.core.TemplateNotFoundException;
import org.pirkaengine.core.parser.ParseException;

/**
 * prk:context テスト
 * @author shuji
 */
public class RenderTest_context extends RenderTest {

    @Test
    public void render_prk_content() throws ParseException, PirkaLoadException, TemplateNotFoundException {
        String templateName = "Content";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("text", "置換されました。");
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

}
