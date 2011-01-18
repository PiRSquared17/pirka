package org.pirkaengine.core.parser;

import static org.pirkaengine.core.parser.Fragment.*;
import static org.pirkaengine.core.util.Logger.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.pirkaengine.core.PrkAttribute;
import org.pirkaengine.core.PrkElement;
import org.pirkaengine.core.PrkNameSpace;
import org.pirkaengine.core.parser.Fragment.Attribute;
import org.pirkaengine.core.parser.Fragment.Builder;

/**
 * StAXXmlParser.
 * <p>
 * パーサーではXMLを解析し、論理的な断片（Fragment）に分割する。
 * 分割された断片からXML構造を組み立てる時、断片を後ろから処理すると都合が良いため、
 * 断片は逆順に積む実装とする。
 * </p>
 * @author shuji.w6e
 * @since 0.1.0
 * @see Fragment
 */
public class StAXXmlParser implements XmlParser {

    private static final Set<String> ATTR_NAMES = new HashSet<String>();
    static {
        for (PrkAttribute attr : PrkAttribute.values()) {
            ATTR_NAMES.add(attr.name);
        }
    }

    private final Pattern expressionPattern = Pattern.compile("\\$\\_?\\{[^\\}]+\\}");
    /** テキストのバッファ */
    private final StringBuilder text = new StringBuilder();
    /** 継承用のテンプレートファイル */
    private String baseTemplate = null;

    /**
     * コンストラクタ.
     */
    public StAXXmlParser() {
    }
    
    /*
     * (non-Javadoc)
     * @see org.pirkaengine.core.parser.XmlParser#parse(java.io.InputStream, java.nio.charset.Charset)
     */
    @Override
    public XhtmlStruct parse(InputStream input, Charset charset) throws ParseException {
        if (input == null) throw new IllegalArgumentException("input == null.");
        if (charset == null) throw new IllegalArgumentException("charset == null.");
        return parse(new InputStreamReader(input, charset));
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.core.parser.XmlParser#parse(java.io.Reader)
     */
    public XhtmlStruct parse(Reader input) throws ParseException {
        if (input == null) throw new IllegalArgumentException("input == null.");
        if (isDebugEnabled()) debug("parse: " + input);
        text.setLength(0);
        XMLStreamReader reader = null;
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            factory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
            factory.setProperty(XMLInputFactory.IS_COALESCING, false);
            factory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, false);
            reader = factory.createXMLStreamReader(createProxy(input));
            FragmentParser parser = new FragmentParser(reader);
            parser.parseFragments();
            LinkedList<Fragment> fragments = new LinkedList<Fragment>();
            Fragment preFragment = null;
            for (Fragment fragment : parser.fragments) {
                // サイズ0の断片は圧縮
                if (preFragment != null && preFragment.offset == fragment.offset) {
                    // Empty Element Tag
                    if (preFragment.type == Fragment.Type.TAG_END && fragment.type == Fragment.Type.TAG_START) {
                        fragments.removeLast();
                        String prkKey = fragment.prkKey == null ? PrkAttribute.ATTR.name : fragment.prkKey;
                        String prkValue = fragment.prkValue == null ? PrkAttribute.ATTR.name : fragment.prkValue;
                        fragments.add(Fragment.create(fragment.offset, Fragment.Type.TAG_EMPTY_ELEMENTS)
                                .append(prkKey, prkValue).copy(fragment).build());
                    }
                    continue;
                }
                preFragment = fragment;
                fragments.add(fragment);
            }
            return new XhtmlStruct(text, fragments, baseTemplate);
        } catch (XMLStreamException e) {
            throw new ParseException("Failed when parse xhtml.", e);
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (XMLStreamException e) {
                throw new ParseException(e);
            }
        }
    }

    private Reader createProxy(final Reader reader) {
        return new Reader() {

            @Override
            public void close() throws IOException {
                reader.close();
            }

            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                int result = reader.read(cbuf, off, len);
                if (result < 0) {
                    return result;
                }
                text.append(cbuf, off, result);
                if (isTraceEnabled()) trace(text);
                return result;
            }
        };
    }

    private class FragmentParser {

        private XMLStreamReader reader;
        private LinkedList<Fragment> fragments = new LinkedList<Fragment>();
        // タグの深さ
        private Depth depth = new Depth();
        // イベント種別
        private int event;
        private int coursor = -1;
        private int offsetTagStart = 0;
        private int offsetTagEnd = 0;
        private int offsetTextStart = 0;
        private int offsetTextEnd = 0;
        private boolean resolveEmptyTag = false;
        // テキストと評価式が混在する場合のテキストの開始位置を保持する
        private int textLastOffset = -1;

        private FragmentParser(XMLStreamReader reader) {
            this.reader = reader;
        }

        private void parseFragments() throws XMLStreamException, ParseException {
            fragments.addFirst(Fragment.create(0, Fragment.Type.TEXT).build());
            while (reader.hasNext()) {
                parse();
            }
        }

        private void parse() throws XMLStreamException, ParseException {
            event = next();
            switch (event) {
            case XMLStreamReader.START_ELEMENT:
                handleStartElm();
                break;
            case XMLStreamReader.END_ELEMENT:
                handleEndElm();
                break;
            case XMLStreamReader.CDATA:
            case XMLStreamReader.COMMENT:
            case XMLStreamReader.CHARACTERS:
                handleCharsElm();
                break;
            case XMLStreamReader.END_DOCUMENT:
                // 空のテキストノード等が含まれている場合に削除
                if (fragments.getFirst().offset == StAXXmlParser.this.text.length()) {
                    fragments.removeFirst();
                }
            default:
                break;
            }
        }

        private int next() throws XMLStreamException {
            int event = reader.next();
            switch (event) {
            case XMLStreamReader.START_ELEMENT:
                String tagStart = "<" + getTagName();
                offsetTagStart = text.indexOf(tagStart, coursor + 1);
                offsetTagEnd = text.indexOf(">", offsetTagStart);
                assert offsetTagStart != -1 : getTagName();
                coursor = offsetTagStart;
                resolveEmptyTag = false;
                break;
            case XMLStreamReader.END_ELEMENT:
                // START = END, such as: <aaa />
                if (!resolveEmptyTag && text.charAt(offsetTagEnd - 1) == '/') {
                    resolveEmptyTag = true;
                    break;
                }
                resolveEmptyTag = false;
                offsetTagStart = text.indexOf("</" + getTagName(), coursor + 1);
                offsetTagEnd = text.indexOf(">", offsetTagStart);
                assert offsetTagStart != -1 : getTagName();
                coursor = offsetTagStart;
                break;
            case XMLStreamReader.CDATA:
            case XMLStreamReader.COMMENT:
            case XMLStreamReader.CHARACTERS:
                offsetTextStart = offsetTagEnd + 1;
                offsetTextEnd = reader.getLocation().getCharacterOffset();
                offsetTextEnd = text.indexOf("<", offsetTextStart);
                coursor = offsetTextStart;
                break;
            default:
                break;
            }
            return event;
        }

        private String getTagName() {
            return reader.getPrefix().length() == 0 ? reader.getLocalName() : reader.getPrefix() + ":"
                    + reader.getLocalName();
        }

        /**
         * タグの開始イベントの処理
         *  prk属性のあるタグの場合、tagDepthが１に設定される
         */
        private void handleStartElm() throws ParseException, XMLStreamException {
            depth.up();
            QName qname = reader.getName();
            if (isTraceEnabled()) trace(offsetTagStart + ":START:" + qname.getPrefix() + ":" + qname.getLocalPart());
            if (qname.getPrefix().equals(PrkNameSpace.PREFIX)) { // <prk:def> or
                // <prk:replace>
                handlePrkTag(qname);
                return;
            }
            int attrCount = reader.getAttributeCount();
            boolean isLoop = false;
            String key = null;
            String value = null;
            ArrayList<Fragment.Attribute> attrs = new ArrayList<Fragment.Attribute>();
            ArrayList<Fragment.Attribute> prkAttrs = new ArrayList<Fragment.Attribute>();
            ArrayList<Fragment.Attribute> pathAttrs = new ArrayList<Fragment.Attribute>();
            for (int i = 0; i < attrCount; i++) { // <x prk:aaa="value">
                QName attrName = reader.getAttributeName(i);
                String localAttrName = attrName.getLocalPart();
                if (!attrName.getPrefix().equals("prk")) {
                    attrs.add(attr(localAttrName, reader.getAttributeValue(i)));
                    continue;
                }
                if (localAttrName.equals(PrkAttribute.ATTR.name)) throw new ParseException(
                        "prk:attr must has attr_name: prk:attr.attr_name");
                if (localAttrName.equals(PrkAttribute.PATH.name)) throw new ParseException(
                        "prk:path must has attr_name: prk:path.attr_name");
                if (ATTR_NAMES.contains(localAttrName)) {
                    if (key != null) throw new ParseException("Cant define prk:tags expected prk:attr.xxx");
                    key = localAttrName;
                    value = reader.getAttributeValue(i);
                    if (localAttrName.equals(PrkAttribute.FOR.name) || localAttrName.equals(PrkAttribute.REPEAT.name)) {
                        isLoop = true;
                    }
                } else if (localAttrName.startsWith(PrkAttribute.ATTR.name)) {
                    prkAttrs.add(attr(localAttrName, reader.getAttributeValue(i)));
                } else if (localAttrName.startsWith(PrkAttribute.PATH.name)) {
                    pathAttrs.add(attr(localAttrName, reader.getAttributeValue(i)));
                } else {
                    throw new ParseException("不正なタグ:" + attrName);
                }
            }
            if (prkAttrs.isEmpty() && pathAttrs.isEmpty() && key == null) return;
            if ((!prkAttrs.isEmpty() || !pathAttrs.isEmpty()) && key == null) {
                key = PrkAttribute.ATTR.name;
                value = PrkAttribute.ATTR.name;
            }
            if (key != null) {
                // ルート要素に属性が付いている場合は最初に設定したダミーのFragmentを削除しておく
                if (offsetTagStart == 0) fragments.clear();
                fragments.addFirst(new Fragment(offsetTagStart, Fragment.Type.TAG_START, key, value, attrs, prkAttrs,
                        pathAttrs));
                if (isLoop || key.equals(PrkAttribute.BLOCK.name)) {
                    fragments.addFirst(Fragment.create(offsetTagEnd + 1, Fragment.Type.TEXT).build());
                }
                depth.nest();
                // template baseの処理
                if (key.equals(PrkAttribute.EXTENDS.name)) {
                    if (baseTemplate != null) throw new ParseException("テンプレートの多重継承はできません");
                    baseTemplate = value;
                }
            }
        }

        /** prk:def / prk:replace / prk:component / prk:include / prk:extends / prk:functions の処理 */
        private void handlePrkTag(QName qname) throws ParseException, XMLStreamException {
            String localName = qname.getLocalPart();
            if (localName.equals(PrkElement.DEF.name)) { // <prk:def />
                int attrCount = reader.getAttributeCount();
                String language = null;
                String type = "function";
                String name = null;
                for (int i = 0; i < attrCount; i++) {
                    String attrName = reader.getAttributeName(i).getLocalPart();
                    if (attrName.equals("language")) language = reader.getAttributeValue(i);
                    if (attrName.equals("type")) type = reader.getAttributeValue(i);
                    if (attrName.equals("name")) name = reader.getAttributeValue(i);
                }
                // TODO name, language などのリテラル
                if (language == null) throw new ParseException("format error: language is null.");
                if (name == null) throw new ParseException("format error: name is null.");
                fragments.addFirst(Fragment.create(offsetTagStart, Fragment.Type.DEF_START)
                        .appendPrkAttrs(attr("language", language), attr("type", type), attr("name", name)).build());
                int defBodyOffset = offsetTagEnd + 1;
                event = next();
                if (event != XMLStreamReader.CHARACTERS) throw new ParseException("format error: ");
                fragments.addFirst(Fragment.create(defBodyOffset, Fragment.Type.DEF_CDATA).build());
                while (event == XMLStreamReader.CHARACTERS) {
                    event = next();
                }
                if (event != XMLStreamReader.END_ELEMENT) throw new ParseException("format error: ");
                if (!reader.getPrefix().equals("prk") || !reader.getLocalName().equals("def")) {
                    throw new ParseException("format error: ");
                }
                fragments.addFirst(Fragment.create(offsetTagStart, Fragment.Type.DEF_END).build());
                fragments.addFirst(Fragment.create(offsetTagEnd + 1, Fragment.Type.TEXT).build());
            } else if (localName.equals(PrkElement.FUNCTIONS.name)) { // <prk:functions
                String className = null;
                String name = "";
                for (int i = 0; i < reader.getAttributeCount(); i++) {
                    String attrName = reader.getAttributeName(i).toString();
                    if (attrName.equals("class")) {
                        className = reader.getAttributeValue(i);
                    } else if (attrName.equals("name")) {
                        name = reader.getAttributeValue(i);
                    } else {
                        throw new ParseException("attribute must be only 'class' or 'name' in prk:functions");
                    }
                }
                if (className == null) throw new ParseException("attribute must be contain 'class' in prk:functions");
                fragments.addFirst(Fragment.create(offsetTagStart, Fragment.Type.FUNCTIONS)
                        .appendPrkAttrs(attr("class", className), attr("name", name)).build());
                event = next();
                if (event != XMLStreamReader.END_ELEMENT) throw new ParseException("prk:functions can't has an element");
                fragments.addFirst(Fragment.create(offsetTagEnd + 1, Fragment.Type.TEXT).build());
            } else if (localName.equals(PrkElement.VAL.name)) { // <prk:val />
                String name = find("name");
                if (name == null) throw new ParseException("name is not found in prk:component");
                String value = find("value");
                if (value == null) throw new ParseException("value is not found in prk:component");
                fragments.addFirst(Fragment.create(offsetTagStart, Fragment.Type.TAG_EMPTY_ELEMENTS)
                        .append(PrkElement.VAL.name, name).appendAttrs(Fragment.attr("value", value)).build());
                fragments.addFirst(Fragment.create(offsetTagEnd + 1, Fragment.Type.TEXT).build());
            } else if (localName.equals(PrkElement.COMPONENT.name)) { // <prk:component />
                String type = find("type");
                int attrCount = reader.getAttributeCount();
                ArrayList<Attribute> attrs = new ArrayList<Attribute>();
                for (int i = 0; i < attrCount; i++) {
                    String key = reader.getAttributeName(i).getLocalPart();
                    if (key.equals("type")) continue;
                    attrs.add(Fragment.attr(key, reader.getAttributeValue(i)));
                }
                if (type == null) throw new ParseException("type is not found in prk:component");
                int compTagOffset = offsetTagStart;
                event = next();
                if (event == XMLStreamReader.END_ELEMENT) {
                    Builder frg = Fragment.create(compTagOffset, Fragment.Type.TAG_EMPTY_ELEMENTS).append(
                            PrkElement.COMPONENT.name, type);
                    fragments.addFirst(frg.appendAttrs(attrs).build());
                    fragments.addFirst(Fragment.create(offsetTagEnd + 1, Fragment.Type.TEXT).build());
                    return;
                }
                // <prk:param>
                while (event != XMLStreamReader.START_ELEMENT && event != XMLStreamReader.END_ELEMENT) {
                    event = next();
                }
                outer: while (true) {
                    // <prk:param key="xxx" value="xxx" />
                    if (event != XMLStreamReader.START_ELEMENT) throw new ParseException("format error: ");
                    if (!reader.getPrefix().equals("prk") || !reader.getLocalName().equals("param")) {
                        throw new ParseException("format error: ");
                    }
                    String key = find("key");
                    if (key == null) throw new ParseException("key is not found in prk:param");
                    String value = find("value");
                    if (value == null) throw new ParseException("value is not found in prk:param");
                    attrs.add(Fragment.attr(key, value));
                    event = next();
                    if (event != XMLStreamReader.END_ELEMENT) {
                        throw new ParseException("format error: ");
                    }
                    // componentの終わりか次のparamまで読み飛ばす
                    while (true) {
                        event = next();
                        if (isEndPrkComponent()) {
                            // </prk:component>
                            break outer;
                        } else if (isStartPrkParam()) {
                            // <prk:param />
                            continue outer;
                        }
                    }
                }
                fragments.addFirst(Fragment.create(compTagOffset, Fragment.Type.TAG_EMPTY_ELEMENTS)
                        .append(PrkElement.COMPONENT.name, type).appendAttrs(attrs).build());
                fragments.addFirst(Fragment.create(offsetTagEnd + 1, Fragment.Type.TEXT).build());
            } else if (localName.equals(PrkElement.REPLACE.name)) { // <prk:replace>
                String value = find("value");
                if (value == null) throw new ParseException("value is not found in prk:replace");
                fragments.addFirst(Fragment.create(offsetTagStart, Fragment.Type.TAG_START)
                        .append(PrkElement.REPLACE.name, value).build());
                depth.nest();
            } else if (localName.equals(PrkElement.FOR.name)) { // <prk:for>
                String value = find("loop");
                if (value == null) throw new ParseException("loop is not found in prk:for");
                fragments.addFirst(Fragment.create(offsetTagStart, Fragment.Type.TAG_START)
                        .append(PrkElement.FOR.name, value).build());
                fragments.addFirst(Fragment.create(offsetTagEnd + 1, Fragment.Type.TEXT).build());
                depth.nest();
            } else if (localName.equals(PrkElement.INCLUDE.name)) { // <prk:include  file="..." >
                String file = find("file");
                if (file == null) throw new ParseException("file is not found in prk:include");
                fragments.addFirst(Fragment.create(offsetTagStart, Fragment.Type.TAG_EMPTY_ELEMENTS)
                        .append(PrkElement.INCLUDE.name, file).build());
                fragments.addFirst(Fragment.create(offsetTagEnd + 1, Fragment.Type.TEXT).build());
            } else {
                throw new ParseException("Unkown Element: " + qname);
            }
        }

        private boolean isEndPrkComponent() {
            return reader.isEndElement() && reader.getPrefix().equals("prk")
                    && reader.getLocalName().equals("component");
        }

        private boolean isStartPrkParam() {
            return reader.isStartElement() && reader.getPrefix().equals("prk") && reader.getLocalName().equals("param");
        }

        private String find(String key) throws ParseException {
            int attrCount = reader.getAttributeCount();
            for (int i = 0; i < attrCount; i++) {
                if (reader.getAttributeName(i).getLocalPart().equals(key)) {
                    return reader.getAttributeValue(i);
                }
            }
            return null;
        }

        // タグの終了イベントの処理
        private void handleEndElm() throws ParseException, XMLStreamException {
            if (isTraceEnabled()) trace(offsetTagStart + ":  END:" + reader.getName());
            if (depth.isNest()) {
                depth.down();
                if (depth.lastValue() == 0) {
                    fragments.addFirst(Fragment.create(offsetTagStart, Fragment.Type.TAG_END).build());
                    fragments.addFirst(Fragment.create(offsetTagEnd + 1, Fragment.Type.TEXT).build());
                    depth.release();
                }
            }
        }

        private void handleCharsElm() throws ParseException, XMLStreamException {
            int textStart = Math.max(textLastOffset, offsetTagEnd + 1);
            int textEnd = offsetTextEnd;
            String string;
            if (textEnd < 0) {
                string = text.substring(textStart);
            } else {
                string = text.substring(textStart, textEnd);
            }
            if (isTraceEnabled()) trace(textStart + ": TEXT:" + string);
            if (fragments.getFirst().type == Fragment.Type.TAG_START) {
                fragments.addFirst(Fragment.create(textStart, Fragment.Type.TAG_BODY).build());
            }
            Matcher matcher = expressionPattern.matcher(string);
            while (matcher.find()) {
                textLastOffset = textStart + matcher.end();
                int start = matcher.start();
                if (StAXXmlParser.this.text.charAt(textStart + start - 1) == '$') { // Escape
                    // Character
                    fragments.addFirst(Fragment.create(textStart + start - 1, Fragment.Type.ESCAPE).build());
                    fragments.addFirst(Fragment.create(textStart + start + 1, Fragment.Type.TEXT).build());
                    continue;
                }
                // TODO 不正な式言語フォーマット検知
                fragments.addFirst(Fragment.create(textStart + start, Fragment.Type.EXPRESSION).build());
                fragments.addFirst(Fragment.create(textStart + matcher.end(), Fragment.Type.TEXT).build());
            }
        }
    }

    private static class Depth {
        private LinkedList<Integer> stack = new LinkedList<Integer>();

        private Depth() {
            stack.add(1);
        }

        private boolean isNest() {
            return 1 < stack.size();
        }

        private void release() {
            stack.removeLast();
            this.down();
        }

        private void nest() {
            stack.add(1);
        }

        private int lastValue() {
            return stack.getLast();
        }

        private void up() {
            int last = stack.removeLast();
            stack.add(last + 1);
        }

        private void down() {
            int last = stack.removeLast();
            stack.add(last - 1);
        }
    }

}
