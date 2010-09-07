package org.pirkaengine.core;

import static org.pirkaengine.core.TestUtil.*;

import org.junit.Test;
import org.pirkaengine.core.PirkaLoadException;
import org.pirkaengine.core.Template;
import org.pirkaengine.core.TemplateNotFoundException;
import org.pirkaengine.core.parser.ParseException;

public class RenderTest_script extends RenderTest {
    
    @Test
    public void render_prkDefGroovy_getFileType() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "PrkDefGroovy_getFileType";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("name", "Sample.doc");
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

    @Test
    public void render_prkDefJavaScript_average() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "PrkDefJavaScript_average";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        Integer[] scores = {10, 8, 9, 10, 8};
        viewModel.put("scores", scores);
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }
    
    @Test
    public void render_prkDefGroovy_trim() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "PrkDefGroovy_trim";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("text1", "あいうえおかきくけ");
        viewModel.put("text2", "あいうえおかきくけこ");
        viewModel.put("text3", "あいうえおかきくけこさし");
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

    @Test
    public void render_prkDefRuby_format() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "PrkDefRuby_format";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("price", 1234567890);
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }
}
