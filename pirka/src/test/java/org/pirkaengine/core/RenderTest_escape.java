package org.pirkaengine.core;

import static org.pirkaengine.core.TestUtil.*;

import org.junit.Test;
import org.pirkaengine.core.PirkaLoadException;
import org.pirkaengine.core.Template;
import org.pirkaengine.core.TemplateNotFoundException;
import org.pirkaengine.core.parser.ParseException;

public class RenderTest_escape extends RenderTest {
    
    @Test
    public void render_Escape() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "Escape";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        String actual = tmpl.generate().render();
        assertRenderEquals(templateName, actual);
    }
}
