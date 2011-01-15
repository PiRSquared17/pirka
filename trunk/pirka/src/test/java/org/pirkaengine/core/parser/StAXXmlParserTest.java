package org.pirkaengine.core.parser;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.pirkaengine.core.PrkAttribute;
import org.pirkaengine.core.PrkElement;

/**
 * パーサテスト.
 * 
 * @author shuji
 */
public class StAXXmlParserTest {

    private StAXXmlParser target;

    @Before
    public void setup() {
        target = new StAXXmlParser();
    }

    @Test
    public void parse_Text() throws ParseException {
        String text = "<xhtml><body>Text テキスト</body></xhtml>";
        XhtmlStruct actual = target.parse(getInput(text));
        assertThat(actual.getFragments(), is(expected(Fragment.create(0, Fragment.Type.TEXT).build())));
        assertThat(actual.getText().toString(), is(text));
    }

    @Test
    public void parse_Text_ShiftJIS() throws ParseException, UnsupportedEncodingException {
        Charset charset = Charset.forName("Windows-31J");
        String text = "<xhtml><body>Text テキスト</body></xhtml>";
        ByteArrayInputStream input = new ByteArrayInputStream(text.getBytes(charset));
        XhtmlStruct actual = target.parse(input, charset);
        assertThat(actual.getFragments(), is(expected(Fragment.create(0, Fragment.Type.TEXT).build())));
        assertThat(actual.getText().toString(), is(text));
    }

    @Test
    public void parse_Expression() throws ParseException {
        String text = "<xhtml><body>${hoge}</body></xhtml>";
        XhtmlStruct actual = target.parse(getInput(text));
        assertThat(
                actual.getFragments(),
                is(expected(Fragment.create(20, Fragment.Type.TEXT).build(),
                        Fragment.create(13, Fragment.Type.EXPRESSION).build(), Fragment.create(0, Fragment.Type.TEXT)
                                .build())));
        assertThat(actual.getText().toString(), is(text));
    }

    @Test
    public void parse_Expression_mix() throws ParseException {
        String text = "<xhtml><body>xxx<a>${hoge}</a></body></xhtml>";
        XhtmlStruct actual = target.parse(getInput(text));
        assertThat(
                actual.getFragments(),
                is(expected(Fragment.create(26, Fragment.Type.TEXT).build(),
                        Fragment.create(19, Fragment.Type.EXPRESSION).build(), Fragment.create(0, Fragment.Type.TEXT)
                                .build())));
        assertThat(actual.getText().toString(), is(text));
    }

    @Test
    public void parse_Expression_mix2() throws ParseException {
        String text = "<xhtml><body>\r\n01234<a href=\"aa\">aaa</a>${hoge}</body></xhtml>";
        XhtmlStruct actual = target.parse(getInput(text));
        assertThat(
                actual.getFragments(),
                is(expected(Fragment.create(47, Fragment.Type.TEXT).build(),
                        Fragment.create(40, Fragment.Type.EXPRESSION).build(), Fragment.create(0, Fragment.Type.TEXT)
                                .build())));
        assertThat(actual.getText().toString(), is(text));
    }

    @Test
    public void parse_Expression_mix3() throws ParseException {
        String text = "<xhtml><body>\r\n${hoge}<a>aaa</a>ccc</body></xhtml>";
        XhtmlStruct actual = target.parse(getInput(text));
        assertThat(
                actual.getFragments(),
                is(expected(Fragment.create(22, Fragment.Type.TEXT).build(),
                        Fragment.create(15, Fragment.Type.EXPRESSION).build(), Fragment.create(0, Fragment.Type.TEXT)
                                .build())));
        assertThat(actual.getText().toString(), is(text));
    }

    @Test
    public void parse_Content() throws ParseException {
        String text = "<html xmlns:prk=\"http://www.hoge/prk\"><body><div prk:content=\"text\">aaaaaaaaaaa</div></body></html>";
        XhtmlStruct actual = target.parse(getInput(text));
        assertThat(
                actual.getFragments(),
                is(expected(Fragment.create(85, Fragment.Type.TEXT).build(), Fragment.create(79, Fragment.Type.TAG_END)
                        .build(), Fragment.create(68, Fragment.Type.TAG_BODY).build(),
                        Fragment.create(44, Fragment.Type.TAG_START).append("content", "text").build(), Fragment
                                .create(0, Fragment.Type.TEXT).build())));
        assertThat(actual.getText().toString(), is(text));
    }

    @Test
    public void parse_If() throws ParseException {
        String text = "<html xmlns:prk=\"http://www.hoge/prk\"><body><p prk:if=\"foo\">bar</p></body></html>";
        XhtmlStruct actual = target.parse(getInput(text));
        assertThat(
                actual.getFragments(),
                is(expected(Fragment.create(67, Fragment.Type.TEXT).build(), Fragment.create(63, Fragment.Type.TAG_END)
                        .build(), Fragment.create(60, Fragment.Type.TAG_BODY).build(),
                        Fragment.create(44, Fragment.Type.TAG_START).append("if", "foo").build(),
                        Fragment.create(0, Fragment.Type.TEXT).build())));
        assertThat(actual.getText().toString(), is(text));
    }

    @Test
    public void parse_If_Expression() throws ParseException {
        String text = "<html xmlns:prk=\"http://www.hoge/prk\"><body><p prk:if=\"foo\">${bar}</p></body></html>";
        XhtmlStruct actual = target.parse(getInput(text));
        assertThat(
                actual.getFragments(),
                is(expected(Fragment.create(70, Fragment.Type.TEXT).build(), Fragment.create(66, Fragment.Type.TAG_END)
                        .build(), Fragment.create(60, Fragment.Type.EXPRESSION).build(),
                        Fragment.create(44, Fragment.Type.TAG_START).append("if", "foo").build(),
                        Fragment.create(0, Fragment.Type.TEXT).build())));
        assertThat(actual.getText().toString(), is(text));
    }

    @Test
    public void parse_For_Expression() throws ParseException {
        String text = "<html xmlns:prk=\"http://www.hoge/prk\"><body><p prk:for=\"item in items\">${item}</p></body></html>";
        XhtmlStruct actual = target.parse(getInput(text));
        assertThat(
                actual.getFragments(),
                is(expected(Fragment.create(82, Fragment.Type.TEXT).build(), Fragment.create(78, Fragment.Type.TAG_END)
                        .build(), Fragment.create(71, Fragment.Type.EXPRESSION).build(),
                        Fragment.create(44, Fragment.Type.TAG_START).append("for", "item in items").build(), Fragment
                                .create(0, Fragment.Type.TEXT).build())));
        assertThat(actual.getText().toString(), is(text));
    }

    @Test
    public void parse_For2_Expression() throws ParseException {
        String text = "<html xmlns:prk=\"http://www.hoge/prk\"><body><p prk:for=\"item in items\"><span>${item.name}</span><span>${item.price}</span></p></body></html>";
        XhtmlStruct actual = target.parse(getInput(text));
        assertThat(
                actual.getFragments(),
                is(expected(Fragment.create(126, Fragment.Type.TEXT).build(),
                        Fragment.create(122, Fragment.Type.TAG_END).build(), Fragment.create(115, Fragment.Type.TEXT)
                                .build(), Fragment.create(102, Fragment.Type.EXPRESSION).build(),
                        Fragment.create(89, Fragment.Type.TEXT).build(), Fragment.create(77, Fragment.Type.EXPRESSION)
                                .build(), Fragment.create(71, Fragment.Type.TEXT).build(),
                        Fragment.create(44, Fragment.Type.TAG_START).append("for", "item in items").build(), Fragment
                                .create(0, Fragment.Type.TEXT).build())));
        assertThat(actual.getText().toString(), is(text));
    }

    @Test
    public void parse_Escape() throws ParseException {
        String text = "<html><body>AA$${name}AA</body></html>";
        XhtmlStruct actual = target.parse(getInput(text));
        assertThat(
                actual.getFragments(),
                is(expected(Fragment.create(16, Fragment.Type.TEXT).build(), Fragment.create(14, Fragment.Type.ESCAPE)
                        .build(), Fragment.create(0, Fragment.Type.TEXT).build())));
        assertThat(actual.getText().toString(), is(text));
    }

    @Test
    public void parse_Escape_WithHTMLEscape() throws ParseException {
        String text = "<html><body>&lt;p&gt;$${name}&lt;/p&gt;</body></html>";
        XhtmlStruct actual = target.parse(getInput(text));
        assertThat(
                actual.getFragments(),
                is(expected(Fragment.create(23, Fragment.Type.TEXT).build(), Fragment.create(21, Fragment.Type.ESCAPE)
                        .build(), Fragment.create(0, Fragment.Type.TEXT).build())));
        assertThat(actual.getText().toString(), is(text));
    }

    @Test
    public void parse_EmptyTag() throws ParseException {
        String text = "<html><body><br/>${test}</body></html>";
        XhtmlStruct actual = target.parse(getInput(text));
        assertThat(
                actual.getFragments(),
                is(expected(Fragment.create(24, Fragment.Type.TEXT).build(),
                        Fragment.create(17, Fragment.Type.EXPRESSION).build(), Fragment.create(0, Fragment.Type.TEXT)
                                .build())));
        assertThat(actual.getText().toString(), is(text));
    }

    @Test(expected = ParseException.class)
    public void parse_InvalidXhtml() throws ParseException {
        String text = "<xhtml><body>Text テキスト<body></xhtml>";
        target.parse(getInput(text));
    }

    @Test(expected = ParseException.class)
    public void parse_InvalidprkDef() throws ParseException {
        String text = "<html xmlns:prk=\"http://www.pirkaengine.org\"><prk:def /></html>";
        target.parse(getInput(text));
    }

    @Test(expected = ParseException.class)
    public void parse_InvalidprkDef_language無し() throws ParseException {
        String text = "<html xmlns:prk=\"http://www.pirkaengine.org\"><prk:def name=\"hoge\"><![CDATA[//]]></prk:def></html>";
        target.parse(getInput(text));
    }

    @Test(expected = ParseException.class)
    public void parse_InvalidprkDef_name無し() throws ParseException {
        String text = "<html xmlns:prk=\"http://www.pirkaengine.org\"><prk:def language=\"hoge\"><![CDATA[//]]></prk:def></html>";
        target.parse(getInput(text));
    }

    @Test(expected = ParseException.class)
    public void parse_InvalidprkDef_ネストタグ() throws ParseException {
        String text = "<html xmlns:prk=\"http://www.pirkaengine.org\"><prk:def language=\"hoge\" name=\"hoge\"><b></b></prk:def></html>";
        target.parse(getInput(text));
    }

    /**
     * ネストしたfor
     * 
     * @throws ParseException
     */
    @Test
    public void parse_ForNest() throws ParseException {
        String text = "<html xmlns:prk=\"http://www.pirkaengine.org\"><body><p prk:for=\"item in items\"><span prk:content=\"item\">xx</span></p></body></html>";
        XhtmlStruct actual = target.parse(getInput(text));
        assertThat(
                actual.getFragments(),
                is(expected(Fragment.create(116, Fragment.Type.TEXT).build(),
                        Fragment.create(112, Fragment.Type.TAG_END).build(), Fragment
                                .create(105, Fragment.Type.TAG_END).build(),
                        Fragment.create(103, Fragment.Type.TAG_BODY).build(),
                        Fragment.create(78, Fragment.Type.TAG_START).append("content", "item").build(), Fragment
                                .create(51, Fragment.Type.TAG_START).append("for", "item in items").build(), Fragment
                                .create(0, Fragment.Type.TEXT).build())));
        assertThat(actual.getText().toString(), is(text));
    }

    /**
     * ネストした属性
     * 
     * @throws ParseException
     */
    @Test
    public void parse_NestAttrs() throws ParseException {
        String text = "<html xmlns:prk=\"http://www.pirkaengine.org\"><body><p prk:if=\"is_login\" prk:attr.class=\"welcome_class\">ようこそ</p></body></html>";
        XhtmlStruct actual = target.parse(getInput(text));
        assertThat(
                actual.getFragments(),
                is(expected(
                        Fragment.create(111, Fragment.Type.TEXT).build(),
                        Fragment.create(107, Fragment.Type.TAG_END).build(),
                        Fragment.create(103, Fragment.Type.TAG_BODY).build(),
                        Fragment.create(51, Fragment.Type.TAG_START).append("if", "is_login")
                                .appendPrkAttrs(Fragment.attr("attr.class", "welcome_class")).build(),
                        Fragment.create(0, Fragment.Type.TEXT).build())));
        assertThat(actual.getText().toString(), is(text));
    }

    /**
     * 多重属性
     * 
     * @throws ParseException
     */
    @Test
    public void parse_MultiAttrs() throws ParseException {
        String text = "<html xmlns:prk=\"http://www.pirkaengine.org\"><a prk:attr.href=\"url\" prk:attr.title=\"title\"></a></html>";
        XhtmlStruct actual = target.parse(getInput(text));
        assertThat(
                actual.getFragments(),
                is(expected(
                        Fragment.create(95, Fragment.Type.TEXT).build(),
                        Fragment.create(91, Fragment.Type.TAG_END).build(),
                        Fragment.create(45, Fragment.Type.TAG_START)
                                .append(PrkAttribute.ATTR.name, PrkAttribute.ATTR.name)
                                .appendPrkAttrs(Fragment.attr("attr.href", "url"), Fragment.attr("attr.title", "title"))
                                .build(), Fragment.create(0, Fragment.Type.TEXT).build())));
        assertThat(actual.getText().toString(), is(text));
    }

    /**
     * path属性
     * 
     * @throws ParseException
     */
    @Test
    public void parse_Path() throws ParseException {
        String text = "<html xmlns:prk=\"http://www.pirkaengine.org\"><a prk:path.href=\"index.html\">xxx</a></html>";
        XhtmlStruct actual = target.parse(getInput(text));
        assertThat(
                actual.getFragments(),
                is(expected(
                        Fragment.create(82, Fragment.Type.TEXT).build(),
                        Fragment.create(78, Fragment.Type.TAG_END).build(),
                        Fragment.create(75, Fragment.Type.TAG_BODY).build(),
                        Fragment.create(45, Fragment.Type.TAG_START)
                                .append(PrkAttribute.ATTR.name, PrkAttribute.ATTR.name)
                                .appendPathAttrs(Fragment.attr("path.href", "index.html")).build(),
                        Fragment.create(0, Fragment.Type.TEXT).build())));
        assertThat(actual.getText().toString(), is(text));
    }

    /**
     * 空要素
     * 
     * @throws ParseException
     */
    @Test
    public void parse_empty_element() throws ParseException {
        String text = "<html xmlns:prk=\"http://www.pirkaengine.org\"><img prk:attr.src=\"img_src\" /></html>";
        XhtmlStruct actual = target.parse(getInput(text));
        assertThat(
                actual.getFragments(),
                is(expected(
                        Fragment.create(75, Fragment.Type.TEXT).build(),
                        Fragment.create(45, Fragment.Type.TAG_EMPTY_ELEMENTS)
                                .append(PrkAttribute.ATTR.name, PrkAttribute.ATTR.name)
                                .appendPrkAttrs(Fragment.attr("attr.src", "img_src")).build(),
                        Fragment.create(0, Fragment.Type.TEXT).build())));
        assertThat(actual.getText().toString(), is(text));
    }

    /**
     * 不正なprkタグ
     * 
     * @throws ParseException
     */
    @Test(expected = ParseException.class)
    public void parse_Invalid_prkTag() throws ParseException {
        String text = "<html xmlns:prk=\"http://www.pirkaengine.org\"><body><prk:poo></prk:poo></body></html>";
        target.parse(getInput(text));
    }

    @Test
    public void parse_complexAttr() throws ParseException {
        String text = "<html xmlns:prk=\"http://www.pirkaengine.org\"><a prk:attr.href=\"update/${value}\"></a></html>";
        XhtmlStruct actual = target.parse(getInput(text));
        assertThat(
                actual.getFragments(),
                is(expected(
                        Fragment.create(84, Fragment.Type.TEXT).build(),
                        Fragment.create(80, Fragment.Type.TAG_END).build(),
                        Fragment.create(45, Fragment.Type.TAG_START)
                                .append(PrkAttribute.ATTR.name, PrkAttribute.ATTR.name)
                                .appendPrkAttrs(Fragment.attr("attr.href", "update/${value}")).build(), Fragment
                                .create(0, Fragment.Type.TEXT).build())));
        assertThat(actual.getText().toString(), is(text));
    }

    @Test
    public void parse_attr_when() throws ParseException {
        String text = "<html xmlns:prk=\"http://www.pirkaengine.org\"><option selected=\"selected\" prk:attr.selected.when=\"cond\" /></html>";
        XhtmlStruct actual = target.parse(getInput(text));
        assertThat(
                actual.getFragments(),
                is(expected(
                        Fragment.create(105, Fragment.Type.TEXT).build(),
                        Fragment.create(45, Fragment.Type.TAG_EMPTY_ELEMENTS)
                                .append(PrkAttribute.ATTR.name, PrkAttribute.ATTR.name)
                                .appendPrkAttrs(Fragment.attr("attr.selected.when", "cond"))
                                .appendAttrs(Fragment.attr("selected", "selected")).build(),
                        Fragment.create(0, Fragment.Type.TEXT).build())));
        assertThat(actual.getText().toString(), is(text));
    }

    @Test
    public void parse_prk_component() throws ParseException {
        String text = "<html xmlns:prk=\"http://www.pirkaengine.org\"><prk:component type=\"plugin\" /></html>";
        XhtmlStruct actual = target.parse(getInput(text));
        assertThat(
                actual.getFragments(),
                is(expected(
                        Fragment.create(76, Fragment.Type.TEXT).build(),
                        Fragment.create(45, Fragment.Type.TAG_EMPTY_ELEMENTS)
                                .append(PrkElement.COMPONENT.name, "plugin").build(),
                        Fragment.create(0, Fragment.Type.TEXT).build())));
        assertThat(actual.getText().toString(), is(text));
    }

    @Test
    public void parse_prk_component_withParam() throws ParseException {
        String text = "<html xmlns:prk=\"http://www.pirkaengine.org\"><prk:component type=\"plugin\" ><prk:param key=\"name\" value=\"FooPlugin\" /></prk:component></html>";
        XhtmlStruct actual = target.parse(getInput(text));
        assertThat(
                actual.getFragments(),
                is(expected(
                        Fragment.create(133, Fragment.Type.TEXT).build(),
                        Fragment.create(45, Fragment.Type.TAG_EMPTY_ELEMENTS)
                                .append(PrkElement.COMPONENT.name, "plugin")
                                .appendAttrs(Fragment.attr("name", "FooPlugin")).build(),
                        Fragment.create(0, Fragment.Type.TEXT).build())));
        assertThat(actual.getText().toString(), is(text));
    }

    @Test
    public void parse_prk_component_withParams() throws ParseException {
        String text = "<html xmlns:prk=\"http://www.pirkaengine.org\"><prk:component type=\"plugin\" ><prk:param key=\"name\" value=\"FooPlugin\" /><prk:param key=\"var\" value=\"foo\" /></prk:component></html>";
        XhtmlStruct actual = target.parse(getInput(text));
        assertThat(
                actual.getFragments(),
                is(expected(
                        Fragment.create(168, Fragment.Type.TEXT).build(),
                        Fragment.create(45, Fragment.Type.TAG_EMPTY_ELEMENTS)
                                .append(PrkElement.COMPONENT.name, "plugin")
                                .appendAttrs(Fragment.attr("name", "FooPlugin"), Fragment.attr("var", "foo")).build(),
                        Fragment.create(0, Fragment.Type.TEXT).build())));
        assertThat(actual.getText().toString(), is(text));
    }

    @Test
    public void parse_prk_component_withParams2() throws ParseException {
        String text = "<html xmlns:prk=\"http://www.pirkaengine.org\"><prk:component type=\"plugin\" >  <prk:param key=\"name\" value=\"FooPlugin\" />  <prk:param key=\"var\" value=\"foo\" /></prk:component></html>";
        XhtmlStruct actual = target.parse(getInput(text));
        assertThat(
                actual.getFragments(),
                is(expected(
                        Fragment.create(172, Fragment.Type.TEXT).build(),
                        Fragment.create(45, Fragment.Type.TAG_EMPTY_ELEMENTS)
                                .append(PrkElement.COMPONENT.name, "plugin")
                                .appendAttrs(Fragment.attr("name", "FooPlugin"), Fragment.attr("var", "foo")).build(),
                        Fragment.create(0, Fragment.Type.TEXT).build())));
        assertThat(actual.getText().toString(), is(text));
    }

    @Test
    public void parse_prk_component_withParams3() throws ParseException {
        String text = "<html xmlns:prk=\"http://www.pirkaengine.org\"><prk:component type=\"plugin\" name=\"FooPlugin\" var=\"foo\" /></html>";
        XhtmlStruct actual = target.parse(getInput(text));
        assertThat(
                actual.getFragments(),
                is(expected(
                        Fragment.create(103, Fragment.Type.TEXT).build(),
                        Fragment.create(45, Fragment.Type.TAG_EMPTY_ELEMENTS)
                                .append(PrkElement.COMPONENT.name, "plugin")
                                .appendAttrs(Fragment.attr("name", "FooPlugin"), Fragment.attr("var", "foo")).build(),
                        Fragment.create(0, Fragment.Type.TEXT).build())));
        assertThat(actual.getText().toString(), is(text));
    }

    @Test
    public void parse_prk_component_withParams_and_for() throws ParseException {
        String text = "<html xmlns:prk=\"http://www.pirkaengine.org\"><prk:component type=\"plugin\" ><prk:param key=\"name\" value=\"FooPlugin\" /></prk:component><p>AAA</p><p prk:for=\"item in items\">${item}</p></html>";
        XhtmlStruct actual = target.parse(getInput(text));
        assertThat(
                actual.getFragments(),
                is(expected(
                        Fragment.create(181, Fragment.Type.TEXT).build(),
                        Fragment.create(177, Fragment.Type.TAG_END).build(),
                        Fragment.create(170, Fragment.Type.EXPRESSION).build(),
                        Fragment.create(143, Fragment.Type.TAG_START).append("for", "item in items").build(),
                        Fragment.create(133, Fragment.Type.TEXT).build(),
                        Fragment.create(45, Fragment.Type.TAG_EMPTY_ELEMENTS)
                                .append(PrkElement.COMPONENT.name, "plugin")
                                .appendAttrs(Fragment.attr("name", "FooPlugin")).build(),
                        Fragment.create(0, Fragment.Type.TEXT).build())));
        assertThat(actual.getText().toString(), is(text));
    }

    @Test
    public void parse_prk_include() throws ParseException {
        String text = "<html xmlns:prk=\"http://www.pirkaengine.org\"><prk:include file=\"foo.html\" /></html>";
        XhtmlStruct actual = target.parse(getInput(text));
        assertThat(
                actual.getFragments(),
                is(expected(
                        Fragment.create(76, Fragment.Type.TEXT).build(),
                        Fragment.create(45, Fragment.Type.TAG_EMPTY_ELEMENTS)
                                .append(PrkElement.INCLUDE.name, "foo.html").build(),
                        Fragment.create(0, Fragment.Type.TEXT).build())));
        assertThat(actual.getText().toString(), is(text));
    }

    @Test(expected = ParseException.class)
    public void parse_prk_include_without_file() throws ParseException {
        String text = "<html xmlns:prk=\"http://www.pirkaengine.org\"><prk:include /></html>";
        target.parse(getInput(text));
    }

    @Test
    public void parse_prk_extends() throws ParseException {
        String text = "<html xmlns:prk=\"http://www.pirkaengine.org\" prk:extends=\"foo.html\" >aaa</html>";
        XhtmlStruct actual = target.parse(getInput(text));
        assertThat(
                actual.getFragments(),
                is(expected(Fragment.create(72, Fragment.Type.TAG_END).build(),
                        Fragment.create(69, Fragment.Type.TAG_BODY).build(), Fragment
                                .create(0, Fragment.Type.TAG_START).append(PrkAttribute.EXTENDS.name, "foo.html")
                                .build())));
        assertThat(actual.getText().toString(), is(text));
    }

    /**
     * prk:functionsのパーステスト
     * 
     * @throws ParseException
     */
    @Test
    public void parse_prk_functions() throws ParseException {
        String text = "<html xmlns:prk=\"http://www.pirkaengine.org\"><prk:functions class=\"foo.bar.Poo\" /></html>";
        XhtmlStruct actual = target.parse(getInput(text));
        assertThat(
                actual.getFragments(),
                is(expected(
                        Fragment.create(82, Fragment.Type.TEXT).build(),
                        Fragment.create(45, Fragment.Type.FUNCTIONS)
                                .appendPrkAttrs(Fragment.attr("class", "foo.bar.Poo"), Fragment.attr("name", ""))
                                .build(), Fragment.create(0, Fragment.Type.TEXT).build())));
        assertThat(actual.getText().toString(), is(text));
    }

    /**
     * prk:functionsのパーステスト
     * 
     * @throws ParseException
     */
    @Test(expected = ParseException.class)
    public void parse_prk_functions_noClass() throws ParseException {
        String text = "<html xmlns:prk=\"http://www.pirkaengine.org\"><prk:functions /></html>";
        target.parse(getInput(text));
    }

    /**
     * prk:functionsのパーステスト
     * 
     * @throws ParseException
     */
    @Test(expected = ParseException.class)
    public void parse_prk_functions_hasElements() throws ParseException {
        String text = "<html xmlns:prk=\"http://www.pirkaengine.org\"><prk:functions class=\"foo.bar.Poo\" >aaa</prk:functions></html>";
        target.parse(getInput(text));
    }

    @Test
    public void parse_prk_block() throws ParseException {
        String text = "<html xmlns:prk=\"http://www.pirkaengine.org\"><div prk:block=\"header\"></div></html>";
        XhtmlStruct actual = target.parse(getInput(text));
        assertThat(
                actual.getFragments(),
                is(expected(Fragment.create(75, Fragment.Type.TEXT).build(), Fragment.create(69, Fragment.Type.TAG_END)
                        .build(), Fragment.create(45, Fragment.Type.TAG_START)
                        .append(PrkAttribute.BLOCK.name, "header").build(), Fragment.create(0, Fragment.Type.TEXT)
                        .build())));
        assertThat(actual.getText().toString(), is(text));
    }

    @Test
    public void parse_Expression_and_prk_block() throws ParseException {
        String text = "<html xmlns:prk=\"http://www.pirkaengine.org\"><body prk:block=\"id\"><p>${hoge}</p></body></html>";
        XhtmlStruct actual = target.parse(getInput(text));
        assertThat(
                actual.getFragments(),
                is(expected(Fragment.create(87, Fragment.Type.TEXT).build(), Fragment.create(80, Fragment.Type.TAG_END)
                        .build(), Fragment.create(76, Fragment.Type.TEXT).build(),
                        Fragment.create(69, Fragment.Type.EXPRESSION).build(), Fragment.create(66, Fragment.Type.TEXT)
                                .build(),
                        Fragment.create(45, Fragment.Type.TAG_START).append(PrkAttribute.BLOCK.name, "id").build(),
                        Fragment.create(0, Fragment.Type.TEXT).build())));
        assertThat(actual.getText().toString(), is(text));
    }

    @Test
    public void parse_prk_for() throws ParseException {
        String text = "<html xmlns:prk=\"http://www.pirkaengine.org\"><prk:for loop=\"item in items\"><span>XXX</span></prk:for></html>";
        XhtmlStruct actual = target.parse(getInput(text));
        assertThat(
                actual.getFragments(),
                is(expected(Fragment.create(101, Fragment.Type.TEXT).build(), Fragment
                        .create(91, Fragment.Type.TAG_END).build(), Fragment.create(75, Fragment.Type.TEXT).build(),
                        Fragment.create(45, Fragment.Type.TAG_START).append(PrkAttribute.FOR.name, "item in items")
                                .build(), Fragment.create(0, Fragment.Type.TEXT).build())));
        assertThat(actual.getText().toString(), is(text));
    }

    @Test
    public void parse_prk_val() throws ParseException {
        String text = "<html xmlns:prk=\"http://www.pirkaengine.org\"><prk:val name=\"foo\" value=\"xxx\" /></html>";
        XhtmlStruct actual = target.parse(getInput(text));
        assertThat(
                actual.getFragments(),
                is(expected(Fragment.create(79, Fragment.Type.TEXT).build(),
                        Fragment.create(45, Fragment.Type.TAG_EMPTY_ELEMENTS).append(PrkElement.VAL.name, "foo")
                                .appendAttrs(Fragment.attr("value", "xxx")).build(),
                        Fragment.create(0, Fragment.Type.TEXT).build())));
        assertThat(actual.getText().toString(), is(text));
    }

    @Test(expected = ParseException.class)
    public void parse_prk_val_without_name() throws ParseException {
        String text = "<html xmlns:prk=\"http://www.pirkaengine.org\"><prk:val value=\"xxx\" /></html>";
        target.parse(getInput(text));
    }

    @Test(expected = ParseException.class)
    public void parse_prk_val_without_value() throws ParseException {
        String text = "<html xmlns:prk=\"http://www.pirkaengine.org\"><prk:val name=\"foo\" /></html>";
        target.parse(getInput(text));
    }

    @Test(expected = IllegalArgumentException.class)
    public void parse_null() throws ParseException {
        target.parse(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parse_null_reader() throws ParseException {
        target.parse(null, Charset.forName("Windows-31J"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void parse_null_charset() throws ParseException {
        target.parse(new ByteArrayInputStream(new byte[0]), null);
    }

    private StringReader getInput(String text) {
        return new StringReader(text);
    }

    private List<Fragment> expected(Fragment... fragments) {
        LinkedList<Fragment> list = new LinkedList<Fragment>();
        for (Fragment fragment : fragments) {
            list.add(fragment);
        }
        return list;
    }
}
