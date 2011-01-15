package org.pirkaengine.core;

import static org.pirkaengine.core.TestUtil.*;

import org.junit.Test;
import org.pirkaengine.core.PirkaLoadException;
import org.pirkaengine.core.Template;
import org.pirkaengine.core.TemplateNotFoundException;
import org.pirkaengine.core.parser.ParseException;

/**
 * @author shuji.w6e
 */
public class RenderTest_DocType extends RenderTest {

    @Test
    public void render_no_DocType() throws ParseException, PirkaLoadException, TemplateNotFoundException {
        String templateName = "DocType";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("body", "DocType");
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

}
