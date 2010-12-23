package org.pirkaengine.core.expression;

/**
 * 関数ユーティリティ
 * @author shuji.w6e
 * @since 0.1.0
 */
class Functions {

    static String getName(String function) {
        return function.substring(0, function.indexOf('('));
    }

    static String[] getParams(String function) {
        String params = function.substring(function.indexOf('(') + 1, function.indexOf(')'));
        if (params.isEmpty()) return new String[0];
        return params.split("(\\s|,)+");
    }
}
