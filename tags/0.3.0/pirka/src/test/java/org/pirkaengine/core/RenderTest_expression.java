package org.pirkaengine.core;

import static org.pirkaengine.core.TestUtil.*;

import org.junit.Test;
import org.pirkaengine.core.parser.ParseException;

public class RenderTest_expression extends RenderTest {

    @Test
    public void render_Simple() throws ParseException, PirkaLoadException, TemplateNotFoundException {
        String templateName = "Simple";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        String actual = tmpl.generate().render();
        assertRenderEquals(templateName, actual);
    }

    @Test
    public void render_VariableString() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "VariableString";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("title", "テストテストテスト");
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

    @Test
    public void render_VariableStrings() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "VariableStrings";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("title", "てすとなのです。");
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

    @Test
    public void render_整数変数() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "VariableInt";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("value", 2008);
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

    @Test
    public void render_複数の変数() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "Variables";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("title", "計算結果表示");
        viewModel.put("result", 200 + 300);
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

    @Test
    public void render_変数と文字列() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "VariableAndText";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("x", 50);
        viewModel.put("y", 10);
        viewModel.put("value", 60);
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

    @Test
    public void render_expression() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "Expression";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        Item item = new Item();
        item.name = "商品";
        item.price = 3000;
        viewModel.put("item", item);
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

    @Test
    public void render_br() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "Br";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("text", "テスト");
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

}
