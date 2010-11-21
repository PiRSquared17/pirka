package org.pirkaengine.core;

import static org.pirkaengine.core.TestUtil.*;

import org.junit.Test;
import org.pirkaengine.core.PirkaLoadException;
import org.pirkaengine.core.Template;
import org.pirkaengine.core.TemplateNotFoundException;
import org.pirkaengine.core.parser.ParseException;

/**
 * 複合タグ テスト
 * @author shuji
 */
public class RenderTest_composite extends RenderTest {
    
    /**
     * ConditionとAttrの複合タグテスト
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    public void render_CompositeConditionAndAttr() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "CompositeConditionAndAttr";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("is_login", true);
        viewModel.put("msg_class", "msg");
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }
    
    /**
     * ConditionとContentの複合タグテスト
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test(expected = ParseException.class)
    public void render_CompositeConditionAndContent() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "CompositeConditionAndContent";
        loader.load(getTemplateFileName(templateName));
    }
}
