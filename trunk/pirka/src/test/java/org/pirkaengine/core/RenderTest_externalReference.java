package org.pirkaengine.core;

import static org.pirkaengine.core.TestUtil.*;

import org.junit.Test;
import org.pirkaengine.core.PirkaLoadException;
import org.pirkaengine.core.Template;
import org.pirkaengine.core.TemplateNotFoundException;
import org.pirkaengine.core.parser.ParseException;

/**
 * 外部参照のあるパターン
 * @author shuji
 */
public class RenderTest_externalReference extends RenderTest {
    
    /**
     * 基本パターン
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test
    public void render_ExternalReference() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "ExternalReference";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        String actual = tmpl.generate().render();
        assertRenderEquals(templateName, actual);
    }
}
