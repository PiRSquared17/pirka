package org.pirkaengine.core;

import static org.pirkaengine.core.TestUtil.*;

import org.junit.Test;
import org.pirkaengine.core.PirkaLoadException;
import org.pirkaengine.core.Template;
import org.pirkaengine.core.TemplateNotFoundException;
import org.pirkaengine.core.parser.ParseException;

/**
 * xml テスト
 * @author shuji
 */
public class RenderTest_xml extends RenderTest {
    
    /**
     * XMLの場合にnamespaceが残ることを確認
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test
    public void render_xml() throws ParseException, PirkaLoadException, TemplateNotFoundException {
        String templateName = "Xml";
        Template tmpl = loader.load(getTemplateFileName(templateName, "xml"));
        viewModel.put("item", "Pirka");
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual, "xml");
    }

}
