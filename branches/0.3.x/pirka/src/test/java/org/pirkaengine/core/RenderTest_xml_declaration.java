package org.pirkaengine.core;

import static org.pirkaengine.core.TestUtil.*;

import org.junit.Test;
import org.pirkaengine.core.parser.ParseException;

/**
 * xml 宣言テスト
 * @author shuji.w6e
 */
public class RenderTest_xml_declaration extends RenderTest {

    @Test
    public void render_xml() throws ParseException, PirkaLoadException, TemplateNotFoundException {
        String templateName = "XmlDeclaration";
        viewModel.put("body", "Body Text");
        Template tmpl = loader.load(getTemplateFileName(templateName));
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

}
