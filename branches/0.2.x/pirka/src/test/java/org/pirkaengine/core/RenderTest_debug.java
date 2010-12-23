package org.pirkaengine.core;

import static org.pirkaengine.core.TestUtil.*;

import org.junit.Test;
import org.pirkaengine.core.PirkaContext;
import org.pirkaengine.core.PirkaLoadException;
import org.pirkaengine.core.Template;
import org.pirkaengine.core.TemplateNotFoundException;
import org.pirkaengine.core.parser.ParseException;

/**
 * prk:debug テスト
 * @author shuji
 */
public class RenderTest_debug extends RenderTest {
    
    /**
     * デバック時、表示される
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test
    public void render_prk_debug_true() throws ParseException, PirkaLoadException, TemplateNotFoundException {
        PirkaContext.getInstance().setEnableDebug(true);
        String templateName = "Debug";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        String actual = tmpl.generate().render();
        assertRenderEquals(templateName + ".true", actual);
    }

    /**
     * 非デバック時、表示されない
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test
    public void render_prk_debug_false() throws ParseException, PirkaLoadException, TemplateNotFoundException {
        PirkaContext.getInstance().setEnableDebug(false);
        String templateName = "Debug";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        String actual = tmpl.generate().render();
        assertRenderEquals(templateName + ".false", actual);
    }
}
