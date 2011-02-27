package org.pirkaengine.core;

import static org.pirkaengine.core.TestUtil.*;

import java.nio.charset.Charset;

import org.junit.Test;
import org.pirkaengine.core.parser.ParseException;

/**
 * @author shuji.w6e
 * @since 0.2.0
 */
public class RenderTest_Shift_JIS extends RenderTest {
    
    /**
     * SJISとして読込をテストする
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test
    public void render() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "SJIS";
        Template tmpl = loader.load(getTemplateFileName(templateName), Charset.forName("Windows-31J"));
        viewModel.put("text", "文字コード");
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual, "html", "MS932");
    }

}
