package org.pirkaengine.core;

import static org.pirkaengine.core.TestUtil.*;

import org.junit.Test;
import org.pirkaengine.core.PirkaLoadException;
import org.pirkaengine.core.Template;
import org.pirkaengine.core.TemplateNotFoundException;
import org.pirkaengine.core.parser.ParseException;

/**
 * prk:path テスト
 * @author shuji
 */
public class RenderTest_path extends RenderTest {

    /**
     * prk:path.herf が正しく置換されること 
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test
    public void render_prk_path_href() throws ParseException, PirkaLoadException, TemplateNotFoundException {
        String templateName = "Path.href";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        String actual = tmpl.generate().render();
        assertRenderEquals(templateName, actual);
    }
    
    /**
     * prk:path はParseException
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test(expected = ParseException.class)
    public void render_prk_path() throws ParseException, PirkaLoadException, TemplateNotFoundException {
        String templateName = "Path";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        tmpl.generate().render();
    }
}
