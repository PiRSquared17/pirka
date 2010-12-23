package org.pirkaengine.core;

import static org.pirkaengine.core.TestUtil.*;

import org.junit.Test;
import org.pirkaengine.core.PirkaLoadException;
import org.pirkaengine.core.Template;
import org.pirkaengine.core.TemplateNotFoundException;
import org.pirkaengine.core.parser.ParseException;

public class RenderTest_method extends RenderTest {
    
    @Test
    public void render_Escape() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "Method";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        Item item = new Item();
        item.price = 98000;
        viewModel.put("item", item);
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }
}
