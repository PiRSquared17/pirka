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
public class RenderTest_noDocType extends RenderTest {

    /**
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test
    public void render_no_DocType() throws ParseException, PirkaLoadException, TemplateNotFoundException {
        String templateName = "NoDocType";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("text", "置換されました。");
        String actual = tmpl.generate(viewModel).render();
        System.out.println(actual);
        assertRenderEquals(templateName, actual);
    }

}
