package org.pirkaengine.core;

import static org.pirkaengine.core.TestUtil.*;

import org.junit.Test;
import org.pirkaengine.core.PirkaLoadException;
import org.pirkaengine.core.Template;
import org.pirkaengine.core.TemplateNotFoundException;
import org.pirkaengine.core.parser.ParseException;

/**
 * XInclude テスト
 * @author shuji
 */
public class RenderTest_include extends RenderTest {

    /**
     * prk:include
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test
    public void render_include() throws ParseException, PirkaLoadException, TemplateNotFoundException {
        String templateName = "Include";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("text", "Hello World");
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

}
