package org.pirkaengine.core.parser;

import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;

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

    /**
     * parse xhtml, and return fragment list.
     * @param input
     * @param charset
     * @return XHTML構造
     * @throws ParseException パースに失敗した場合
     * @since 0.3.0
     */
    XhtmlStruct parse(InputStream input, Charset charset) throws ParseException;
}
