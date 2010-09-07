package org.pirkaengine.core.util;

/**
 * 変数ユーティリティ.
 * @author shuji.w6e
 * @since 0.1.0
 */
public class VariableUtil {

    /**
     * 文字列のHTMLエスケープ処理を行う
     * @param value
     * @return エスケープされた文字列
     */
    public static final String htmlEscape(String value) {
        if (value == null) throw new IllegalArgumentException("value == null");
        StringBuilder buf = new StringBuilder(value);
        for (int index = buf.length() - 1; 0 <= index; index--) {
            switch (buf.charAt(index)) {
            case '<':
                buf.replace(index, index + 1, "&lt;");
                break;
            case '>':
                buf.replace(index, index + 1, "&gt;");
                break;
            case '&':
                buf.replace(index, index + 1, "&amp;");
                break;
            case '"':
                buf.replace(index, index + 1, "&quot;");
                break;
            case '\\':
                buf.replace(index, index + 1, "&#39;");
                break;
            default:
                break;
            }
        }
        return buf.toString();
    }
    
    /**
     * IntegerオブジェクトまたはDoubleオブジェクトに変換する.
     * <p>
     * 変換できない場合はnullを返す。
     * </p>
     * @param text テキスト
     * @return 変換後の数値オブジェクト
     */
    public static final Number toNumber(String text) {
        if (text == null) throw new IllegalArgumentException("text == null");
        if (text.matches("[0-9].*")) {
            if (text.matches(".*\\..*")) return Double.parseDouble(text);
            return Integer.parseInt(text);
        }
        return null;
    }
}
