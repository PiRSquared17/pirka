package org.pirkaengine.core;

import static org.pirkaengine.core.TestUtil.*;

import org.junit.Test;
import org.pirkaengine.core.PirkaLoadException;
import org.pirkaengine.core.Template;
import org.pirkaengine.core.TemplateNotFoundException;
import org.pirkaengine.core.parser.ParseException;

public class RenderTest_sample extends RenderTest {
    
    private void setupViewModel() {
        Category category = new Category();
        category.name = "カテゴリ１";
        Item item1 = new Item();
        item1.name = "ｘｘｘ";
        item1.price = 200;
        item1.description = "あいうえおかきくけこさしすせそ";
        Item item2 = new Item();
        item2.name = "ｙｙｙ";
        item2.price = 500;
        item2.description = "あいうえおか";
        viewModel.put("category", category);
        viewModel.put("items", new Item[] { item1, item2 });
    }

    @Test
    public void render_Sample_ItemList() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        this.setupViewModel();
        String templateName = "Sample_ItemList";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

    @Test
    public void render_Sample_ItemListCR() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        this.setupViewModel();
        String templateName = "Sample_ItemListCR";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }
    
    @Test
    public void render_Sample_ItemListLF() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        this.setupViewModel();
        String templateName = "Sample_ItemListLF";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }
}
