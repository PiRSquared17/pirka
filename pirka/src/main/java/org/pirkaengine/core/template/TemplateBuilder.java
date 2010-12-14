package org.pirkaengine.core.template;

import static org.pirkaengine.core.util.Logger.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;

import org.pirkaengine.core.PirkaLoader;
import org.pirkaengine.core.PrkComponent;
import org.pirkaengine.core.PrkElement;
import org.pirkaengine.core.PrkNameSpace;
import org.pirkaengine.core.expression.ScriptFunction;
import org.pirkaengine.core.parser.Fragment;
import org.pirkaengine.core.parser.ParseException;
import org.pirkaengine.core.parser.StAXXmlParser;
import org.pirkaengine.core.parser.XhtmlStruct;
import org.pirkaengine.core.parser.XmlParser;


/**
 * テンプレートビルダ.
 * <p>
 * テンプレートをビルドする。<br />
 * テンプレートは、XHTMLを読み込み、断片（Fragment）化された後、
 * 適切なNodeクラスへと変換され、XhtmlTemplateクラスが生成される。
 * </p>
 * <code><pre>
 * TemplateBuilder builder = new TemplateBuilder();
 * XhtmlTemplate template = builder.parse(file);
 * </pre></code>
 * @author shuji.w6e
 * @since 0.1.0
 * @see Fragment
 * @see XhtmlTemplate
 */
public class TemplateBuilder {
    final PirkaLoader loader;
    final Map<String, BlockNode> blocks = new HashMap<String, BlockNode>();

    /**
     * コンストラクタ.
     * @param loader PirkaLoader
     */
    public TemplateBuilder(PirkaLoader loader) {
        if (loader == null) throw new IllegalArgumentException("loader == null");
        this.loader = loader;
    }

    /**
     * テンプレートをビルドする.
     * @param templateName テンプレート名
     * @param file File
     * @return テンプレートオブジェクト
     * @throws FileNotFoundException 
     * @throws ParseException 
     */
    public XhtmlTemplate build(String templateName, File file) throws ParseException, FileNotFoundException {
        if (file == null) throw new IllegalArgumentException("file is null.");
        if (isDebugEnabled()) debug("build: " + file.getAbsolutePath());
        XhtmlTemplate template = new XhtmlTemplate(templateName);
        template.setTimeStamp(file.lastModified());
        XhtmlStruct struct = parse(file);
        // extendsの処理
        if (struct.getBaseTemplate() != null) {
            try {
                // 継承テンプレート
                // block部分を抽出する（this.blocksに格納される）
                XhtmlTemplate subTemplate = new XhtmlTemplate(templateName);
                buildTemplate(subTemplate, struct, null);
                // TODO 相対パスの解決
                File baseFile = loader.toFile(struct.getBaseTemplate(), templateName);
                // ベースのテンプレート
                XhtmlStruct baseStruct = parse(baseFile);
                buildTemplate(template, baseStruct, blocks);
                template.components.addAll(subTemplate.components);
                for (String name : subTemplate.functions.keySet()) {
                    template.functions.put(name, subTemplate.functions.get(name));
                }
                return template;
            } catch (Exception e) {
                throw new ParseException(e);
            }
        } else {
            buildTemplate(template, struct, null);
            return template;
        }
    }

    private XhtmlStruct parse(File file) throws ParseException, FileNotFoundException {
        try {
            XmlParser parser = new StAXXmlParser();
            return parser.parse(new InputStreamReader(new FileInputStream(file), loader.getCharset()));
        } catch (UnsupportedEncodingException e) {
            throw new ParseException(e);
        }
    }

    void buildTemplate(XhtmlTemplate template, XhtmlStruct struct, Map<String, BlockNode> blocks) throws ParseException {
        StringBuilder text = struct.getText();
        ListIterator<Fragment> iter = struct.getFragments().listIterator();
        assert iter.hasNext();
        while (iter.hasNext()) {
            Fragment fragment = iter.next();
            if (PrkElement.COMPONENT.name.equals(fragment.prkKey)) {
                PrkComponent comp = new PrkComponent();
                comp.setType(fragment.prkValue);
                for (String key : fragment.attributes().keySet()) {
                    comp.putAttr(key, fragment.attributes().get(key));
                }
                template.add(comp);
                text.delete(fragment.offset, text.length());
                continue;
            }
            Node node = toNode(template, text, fragment, iter, blocks);
            if (isDebugEnabled()) debug(node);
            if (node instanceof ScriptNode) {
                template.addScript((ScriptNode) node);
            } else if (node instanceof FunctionsNode) {
                template.addFunction((FunctionsNode) node);
            } else {
                template.stack(node);
            }
        }
    }

    // FragmentをNodeに変換する
    Node toNode(XhtmlTemplate template, StringBuilder text, Fragment fragment, ListIterator<Fragment> iter,
            Map<String, BlockNode> replaces) throws ParseException {
        String str = text.substring(fragment.offset);
        text.delete(fragment.offset, text.length());
        switch (fragment.type) {
        case TEXT:
            // Fragmentsは逆順、先頭要素のみ処理
            if (iter.hasPrevious()) {
                if (template.getName().endsWith("xml")) { // xmls:prkを削除
                    str = str.replaceAll("xmlns:prk=\\\".+\\\"", "");
                } else { // xmlsを削除
                    str = str.replaceAll("xmlns(:.+)?=\\\".+\\\"", "");
                }
            }
            return new TextNode(str);
        case EXPRESSION:
            if (str.startsWith("$_{")) {
                String expression = str.substring(3, str.length() - 1);
                return new ExpressionNode(expression, false);
            } else if (str.startsWith("${")) {
                String expression = str.substring(2, str.length() - 1);
                return new ExpressionNode(expression);
            }
            throw new AssertionError("EXPRESSION: " + str);
        case TAG_START:
            String tagText = getTagText(str);
            return new StartTagNode(tagText, fragment.prkKey, fragment.prkValue, fragment.prkAttributes(), fragment
                .pathAttributes(), fragment.attributes());
        case TAG_BODY:
            return new TextNode(str);
        case TAG_END:
            // TAG_ENDは対応するTAG_STARTが発生するまで、ノードをスタックする
            EndTagNode end = new EndTagNode(str);
            LinkedList<Node> nodes = new LinkedList<Node>();
            Node nextNode = null;
            while (true) {
                if (!iter.hasNext()) throw new TemplateBuildFailedException("format error"); // TODO
                // message
                Fragment nextFrag = iter.next();
                nextNode = this.toNode(template, text, nextFrag, iter, replaces); // 再帰
                if (nextNode instanceof StartTagNode) break; // 再帰終了
                nodes.addFirst(nextNode);
            }
            StartTagNode start = (StartTagNode) nextNode;
            Node compositeNode = CompositeNode.newNode(start, end, nodes.toArray(new Node[nodes.size()]));
            if (compositeNode instanceof BlockNode) {
                BlockNode block = (BlockNode) compositeNode;
                String id = block.startTagNode.attrValue;
                if (replaces == null) {
                    // 置換対象がない場合は積み上げる
                    this.blocks.put(id, block);
                } else if (replaces.containsKey(id)) {
                    // 置換対象があれば置換する
                    return new BlockNode(start, end, replaces.get(id).nodes);
                } else {
                    // そのまま使うのでなにもしない
                }
            }
            return compositeNode;
        case TAG_EMPTY_ELEMENTS:
            if (PrkElement.REPLACE.name.equals(fragment.prkKey)) {
                tagText = getTagText(str);
                start =
                    new StartTagNode(tagText, fragment.prkKey, fragment.prkValue, fragment.prkAttributes(), fragment
                        .pathAttributes(), fragment.attributes());
                end = new EndTagNode(str);
                return new ReplaceNode(start, end, new Node[] {});
            } else if (PrkElement.INCLUDE.name.equals(fragment.prkKey)) {
                // prk: include
                // TODO 相対パスの解決
                try {
                    XhtmlTemplate includeTemplate = new XhtmlTemplate(fragment.prkValue);
                    File includeFile = loader.toFile(fragment.prkValue, template.templateName);
                    XhtmlStruct includeStruct = parse(includeFile);
                    buildTemplate(includeTemplate, includeStruct, null);
                    // IncludeNode node = new IncludeNode(includeTemplate);
                    // template.stack(node);
                    template.components.addAll(includeTemplate.components);
                    for (String name : includeTemplate.functions.keySet()) {
                        template.functions.put(name, includeTemplate.functions.get(name));
                    }
                    // text.delete(fragment.offset, text.length());
                    return new IncludeNode(includeTemplate);
                } catch (Exception e) {
                    throw new ParseException(e);
                }
            } else {
                String tagName = getTagText(str);
                return new EmptyElementTagNode(
                    tagName,
                    fragment.prkKey,
                    fragment.prkValue,
                    fragment.prkAttributes(),
                    fragment.pathAttributes(),
                    fragment.attributes());
            }
        case DEF_START:
            throw new AssertionError();
        case DEF_CDATA:
            throw new AssertionError();
        case DEF_END:
            // ENDは無視
            if (!iter.hasNext()) throw new TemplateBuildFailedException("format error"); // TODO
            // message
            Fragment cdataFrag = iter.next();
            if (cdataFrag.type != Fragment.Type.DEF_CDATA) throw new TemplateBuildFailedException("format error"); // TODO
            // message
            StringBuilder script = new StringBuilder(text.substring(cdataFrag.offset));
            script.delete(0, script.indexOf("<![CDATA[") + "<![CDATA[".length());
            script.setLength(script.lastIndexOf("]]>"));
            text.delete(cdataFrag.offset, text.length());
            if (!iter.hasNext()) throw new TemplateBuildFailedException("format error"); // TODO
            // message
            Fragment startFrag = iter.next();
            if (startFrag.type != Fragment.Type.DEF_START) throw new TemplateBuildFailedException("format error"); // TODO
            // message
            text.delete(startFrag.offset, text.length());
            Fragment.Attribute[] flgAttrs = startFrag.attributeArray();
            assert flgAttrs.length == 3 : startFrag;
            assert flgAttrs[0].key.equals("language") : startFrag;
            assert flgAttrs[1].key.equals("type") : startFrag;
            assert flgAttrs[1].value.equals("function") : startFrag;
            assert flgAttrs[2].key.equals("name") : startFrag;
            return new ScriptNode(ScriptFunction.create(flgAttrs[0].value, flgAttrs[2].value, script.toString()));
        case FUNCTIONS:
            Fragment.Attribute[] attrs = fragment.attributeArray();
            assert attrs.length == 2 : fragment;
            assert attrs[0].key.equals("class") : fragment;
            assert attrs[1].key.equals("name") : fragment;
            try {
                return new FunctionsNode(attrs[0].value, attrs[1].value);
            } catch (ClassNotFoundException e) {
                throw new TemplateBuildFailedException(e);
            }
        case ESCAPE:
            return new EscapeNode(str);
        default:
            throw new AssertionError(str);
        }
    }

    /** タグからprk属性を除去したタグを返す. */
    private String getTagText(String str) {
        if (str.startsWith("<prk")) return str;
        int from = str.indexOf(PrkNameSpace.PREFIX) - 1;
        if (from < 0) return str;
        int to = str.indexOf('"', from);
        to = str.indexOf('"', to + 1) + 1;
        String tagText = new StringBuilder(str).delete(from, to).toString();
        return getTagText(tagText);
    }

}
