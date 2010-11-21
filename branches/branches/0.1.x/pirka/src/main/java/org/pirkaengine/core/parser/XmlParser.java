package org.pirkaengine.core.parser;

import java.io.Reader;

/**
 * XHTML Parser.
 * @author shuji.w6e
 * @since 0.1.0
 */
public interface XmlParser {

    /**
     * parse xhtml, and return fragment list.
     * @param input
     * @return XHTML構造
     * @throws ParseException パースに失敗した場合
     */
    XhtmlStruct parse(Reader input) throws ParseException;
}
