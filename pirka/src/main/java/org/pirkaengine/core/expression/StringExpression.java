package org.pirkaengine.core.expression;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * 文字列式クラス.
 * @author shuji.w6e
 * @since 0.1.0
 */
public class StringExpression {
    
    private static final Pattern STR_EXPRESSION_PATTERN = Pattern.compile(".*\\$\\{.+\\}.*");
    
    /**
     * 評価式のパターンに一致する場合にtrueを返す
     * @param text 評価文字列
     * @return 評価式のパターンに一致する場合にtrue
     */
    public static boolean isExpression(String text) {
        return STR_EXPRESSION_PATTERN.matcher(text).matches();
    }
    
    /**
     * 評価式が文字列式であるかを判定する。
     * @param expression 評価式
     * @return 中括弧で囲まれた部分が評価式に含まれる場合、true
     */
    public static StringExpression createOrNull(String expression) {
        if (expression == null) throw new IllegalArgumentException("expression == null");
        if (!isExpression(expression)) return null;
        int begin = 0;
        int last = 0;
        int count = 0;
        String[] expressions = new String[32]; // TODO 固定でいいと思う
        StringBuilder strExp = new StringBuilder();
        while(0 <= (begin = expression.indexOf("${", last))) {
            strExp.append(expression.substring(last, begin)).append("%s");
            last = expression.indexOf('}', begin) + 1;
            expressions[count] = expression.substring(begin + 2, last - 1);
            count++;
        }
        strExp.append(expression.substring(last));
        return new StringExpression(strExp.toString(), Arrays.copyOfRange(expressions, 0, count));
    }
    
    final String format;
    final String[] expressions;
    
    StringExpression(String format, String[] expressions) {
        assert format != null;
        assert expressions != null;
        this.format = format;
        this.expressions = expressions;
    }
    
    /**
     * 文字列式をフォーマットする
     * @param args
     * @return フォーマットされた文字列
     */
    public String format(Object... args) {
        return String.format(format, args);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof StringExpression)) return false;
        StringExpression se = (StringExpression) obj;
        if (!se.format.equals(this.format)) return false;
        if (se.expressions.length != this.expressions.length) return false;
        for (int i = 0; i < se.expressions.length; i++) {
            if (!(se.expressions[i].equals(this.expressions[i]))) return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        int hash = this.format.hashCode();
        for (int i = 0; i < this.expressions.length; i++) {
            hash = 17 * hash + expressions[i].hashCode();
        }
        return hash;
    }
    
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("StringExpression[");
        str.append("format=").append(format);
        str.append(", expressions=");
        for (String exp : this.expressions) {
            str.append(exp).append(", ");
        }
        str.append("]");
        return str.toString();
    }
}
