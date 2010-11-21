package org.pirkaengine.core.parser;

/**
 * 改行文字列挙.
 * @author shuji.w6e
 * @since 0.1.0
 */
public enum LineBreak {

    /** CRLF */
    CRLF("\r\n"),
    /** CR */
    CR("\r"),
    /** LF */
    LF("\n");

    /** 改行文字 */
    public final String code;

    /**
     * コンストラクタ
     * @param code
     */
    private LineBreak(String code) {
        assert code != null;
        this.code = code;
    }

    /**
     * テキストから改行文字を判定する.
     * <p>
     * 改行文字（CR、LF、CRLF）が混在している場合にはUnkownLineBreakExceptionをthrowする
     * 改行文字を含まない場合、nullを返す
     * </p>
     * @param text
     * @return 改行文字、含まない場合null
     * @throws UnkownLineBreakException 改行文字（CR、LF、CRLF）が混在している場合
     */
    public static final LineBreak loolup(String text) {
        if (text == null) throw new IllegalArgumentException("text == null");
        return loolup(text.toCharArray());
    }

    /**
     * テキストから改行文字を判定する.
     * <p>
     * 改行文字（CR、LF、CRLF）が混在している場合にはUnkownLineBreakExceptionをthrowする
     * 改行文字を含まない場合、nullを返す
     * </p>
     * @param chars
     * @return 改行文字、含まない場合null
     * @throws UnkownLineBreakException 改行文字（CR、LF、CRLF）が混在している場合
     */
    public static final LineBreak loolup(char[] chars) {
        if (chars == null) throw new IllegalArgumentException("chars == null");
        int len = chars.length;
        LineBreak lb = null;
        for (int i = 0; i < len; i++) {
            switch (chars[i]) {
            case '\n':
                if (lb == null) {
                    lb = LF;
                } else if (lb != LF) {
                    throw new UnkownLineBreakException("has deference line break code.");
                }
                break;
            case '\r':
                if (i + 1 < len && chars[i + 1] == '\n') {
                    i++;
                    if (lb == null) {
                        lb = CRLF;
                    } else if (lb != CRLF) {
                        throw new UnkownLineBreakException("has deference line break code.");
                    }
                } else {
                    if (lb == null) {
                        lb = CR;
                    } else if (lb != CR) {
                        throw new UnkownLineBreakException("has deference line break code.");
                    }
                }
                break;
            default:
                break;
            }
        }
        return lb;
    }

}
