package org.pirkaengine.core;

import static org.pirkaengine.core.TestUtil.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.pirkaengine.core.parser.ParseException;

/**
 * For属性のテストケース
 * @author shuji
 */
public class RenderTest_loop extends RenderTest {

    @Test
    public void render_Loop_Array() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "Loop";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        viewModel.put("items", new String[] { "Apple", "Orange", "Pine" });
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

    @Test
    public void render_Loop_Array_By_Map() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "Loop";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        Map<String, Object> apple = new HashMap<String, Object>();
        apple.put("toString", "Apple");
        Map<String, Object> orange = new HashMap<String, Object>();
        orange.put("toString", "Orange");
        Map<String, Object> pine = new HashMap<String, Object>();
        pine.put("toString", "Pine");
        viewModel.put("items", new Map[]{ apple, orange, pine });
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }
    
    @Test
    public void render_Loop_List() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "Loop";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        List<String> list = new ArrayList<String>();
        list.add("Apple");
        list.add("Orange");
        list.add("Pine");
        viewModel.put("items", list);
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

    @Test
    public void render_LoopItem_List() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "LoopItem";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        List<Item> list = new ArrayList<Item>();
        Item apple = new Item();
        apple.name = "Apple";
        list.add(apple);
        Item orange = new Item();
        orange.name = "Orange";
        list.add(orange);
        Item pine = new Item();
        pine.name = "Pine";
        list.add(pine);
        viewModel.put("items", list);
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }
    
    @Test
    public void render_LoopItems_List() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "LoopItems";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        List<Item> list = new ArrayList<Item>();
        Item apple = new Item();
        apple.name = "Apple";
        apple.price = 120;
        list.add(apple);
        Item orange = new Item();
        orange.name = "Orange";
        orange.price = 200;
        list.add(orange);
        Item pine = new Item();
        pine.name = "Pine";
        pine.price = 500;
        list.add(pine);
        viewModel.put("items", list);
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }
    @Test
    public void render_LoopWithIndex_List() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "LoopWithIndex";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        List<String> list = new ArrayList<String>();
        list.add("Apple");
        list.add("Orange");
        list.add("Pine");
        viewModel.put("items", list);
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }

    @Test(expected = RenderException.class)
    public void render_LoopWithoutIn() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "LoopWithoutIn";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        tmpl.generate().render();
    }

    @Test(expected = RenderException.class)
    public void render_Loop_NOT_Iteratable() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "Loop";
        viewModel.put("items", new Object());
        Template tmpl = loader.load(getTemplateFileName(templateName));
        tmpl.generate(viewModel).render();
    }

    @Test(expected = RenderException.class)
    public void render_Loop_DUPLICATED_itemName() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "Loop";
        List<String> list = new ArrayList<String>();
        list.add("Apple");
        list.add("Orange");
        list.add("Pine");
        viewModel.put("items", list);
        viewModel.put("item", new Object());
        Template tmpl = loader.load(getTemplateFileName(templateName));
        tmpl.generate(viewModel).render();
    }

    @Test(expected = RenderException.class)
    public void render_Loop_DUPLICATED_itemIndex() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "LoopWithIndex";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        List<String> list = new ArrayList<String>();
        list.add("Apple");
        list.add("Orange");
        list.add("Pine");
        viewModel.put("items", list);
        viewModel.put("i", "test");
        tmpl.generate(viewModel).render();
    }

    @Test(expected = RenderException.class)
    public void render_Loop_LoopInvalidedFormat() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "LoopInvalidedFormat";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        tmpl.generate(viewModel).render();
    }

    @Test(expected = RenderException.class)
    public void render_Loop_Array_By_Map_without_toString() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "Loop";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        Map<String, Object> apple = new HashMap<String, Object>();
        Map<String, Object> orange = new HashMap<String, Object>();
        Map<String, Object> pine = new HashMap<String, Object>();
        viewModel.put("items", new Map[]{ apple, orange, pine });
        tmpl.generate(viewModel).render();
    }

    /**
     * ループ時のネストタグテスト
     * @throws ParseException
     * @throws PirkaLoadException
     * @throws TemplateNotFoundException
     */
    @Test
    public void render_LoopItemNestTag_List() throws ParseException, PirkaLoadException, TemplateNotFoundException  {
        String templateName = "LoopItemNestTag";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        List<Item> list = new ArrayList<Item>();
        Item apple = new Item();
        apple.name = "Apple";
        list.add(apple);
        Item orange = new Item();
        orange.name = "Orange";
        list.add(orange);
        Item pine = new Item();
        pine.name = "Pine";
        list.add(pine);
        viewModel.put("items", list);
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
    }
}
