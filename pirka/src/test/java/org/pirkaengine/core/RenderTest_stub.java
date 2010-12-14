package org.pirkaengine.core;

import static org.pirkaengine.core.TestUtil.*;

import org.junit.Test;
import org.pirkaengine.core.PirkaLoadException;
import org.pirkaengine.core.Template;
import org.pirkaengine.core.TemplateNotFoundException;
import org.pirkaengine.core.parser.ParseException;

/**
 * prk:stub属性のテストケース
 * @author shuji
 *
 */
public class RenderTest_stub extends RenderTest {
    
    /**
     * 表示されないこと
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test
    public void render_Stub() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "Stub";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        String actual = tmpl.generate().render();
        assertRenderEquals(templateName, actual);
    }

}
