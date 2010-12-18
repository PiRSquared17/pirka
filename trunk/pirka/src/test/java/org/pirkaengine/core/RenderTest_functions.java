package org.pirkaengine.core;

import static org.pirkaengine.core.TestUtil.*;

import java.util.Calendar;

import org.junit.Test;
import org.pirkaengine.core.PirkaLoadException;
import org.pirkaengine.core.Template;
import org.pirkaengine.core.TemplateNotFoundException;
import org.pirkaengine.core.parser.ParseException;

public class RenderTest_functions extends RenderTest {

    @Test
    public void render_PrkFunctions_paddingZero() throws ParseException, PirkaLoadException, TemplateNotFoundException {
        String templateName = "PrkFunctions_paddingZero";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("value", 127);
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

    @Test
    public void render_PrkFunctions_format() throws ParseException, PirkaLoadException, TemplateNotFoundException {
        String templateName = "PrkFunctions_format";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        Item item = new Item();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2010);
        cal.set(Calendar.MONTH, 3);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 30);
        item.date = cal.getTime();
        viewModel.put("x", item);
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

    @Test
    public void render_PrkFunctions_formatName() throws ParseException, PirkaLoadException, TemplateNotFoundException {
        String templateName = "PrkFunctions_formatName";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        Item item = new Item();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2010);
        cal.set(Calendar.MONTH, 3);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 30);
        item.date = cal.getTime();
        viewModel.put("x", item);
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

    @Test
    public void render_PrkFunctions_cond_true() throws ParseException, PirkaLoadException, TemplateNotFoundException {
        String templateName = "PrkFunctions_cond_true";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("flg", true);
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

    @Test
    public void render_PrkFunctions_cond_false() throws ParseException, PirkaLoadException, TemplateNotFoundException {
        String templateName = "PrkFunctions_cond_false";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("flg", false);
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }
}
