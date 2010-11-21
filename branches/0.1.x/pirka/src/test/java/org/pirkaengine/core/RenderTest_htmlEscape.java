package org.pirkaengine.core;

import static org.pirkaengine.core.TestUtil.*;

import org.junit.Test;
import org.pirkaengine.core.PirkaLoadException;
import org.pirkaengine.core.Template;
import org.pirkaengine.core.TemplateNotFoundException;
import org.pirkaengine.core.parser.ParseException;

public class RenderTest_htmlEscape extends RenderTest {

    @Test
    public void render_HtmlEscape() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "HtmlEscape";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("entry_text", "<b>強調</b>されます。");
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

    @Test
    public void render_NoHtmlEscape() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "NoHtmlEscape";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("entry_text", "<b>強調</b>されます。");
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

}
