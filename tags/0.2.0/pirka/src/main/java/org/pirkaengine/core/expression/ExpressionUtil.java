package org.pirkaengine.core.expression;

import java.util.ArrayList;

/**
 * 評価式ユーティリティ.
 * @author shuji.w6e
 * @since 0.1.0
 */
public class ExpressionUtil {

    /**
     * 評価式を分割する.
     * <p>
     * 評価式は.（ピリオド）で分割される。
     * ただし、括弧で囲まれた文字列を含む場合、括弧内の文字列を含めて１つの文字列として扱う。
     * すなわち、<br/>
     * hoge.huga は hogeとhugaに分割され、<br/>
     * foo.bar(huga)は、fooとbar(huga)に分割される。<br/>
     * また、括弧内の評価式に.ピリオドが含まれている場合も分割しない。
     * </p>
     * @param expression
     * @return 分割後の評価式
     */
    public static String[] split(String expression) {
        if (expression == null) throw new IllegalArgumentException("expression == null");
        StringBuilder buf = new StringBuilder(expression);
        ArrayList<String> list = new ArrayList<String>();
        boolean inFunc = false;
        int lastIdx = 0;
        int len = buf.length();
        for (int i = 0; i < len; i++) {
            char c = buf.charAt(i);
            switch (c) {
            case '.':
                if (!inFunc) {
                    list.add(buf.substring(lastIdx, i));
                    lastIdx = i + 1;
                }
                break;
            case '(':
                inFunc = true;
                break;
            case ')':
                inFunc = false;
                break;
            default:
                break;
            }
        }
        list.add(buf.substring(lastIdx));
        return list.toArray(new String[list.size()]);
    }
}
