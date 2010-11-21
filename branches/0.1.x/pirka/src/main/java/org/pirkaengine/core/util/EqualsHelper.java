package org.pirkaengine.core.util;

/**
 * 同値比較用ヘルパクラス.
 * @author shuji.w6e
 * @since 0.1.0
 */
public class EqualsHelper {

    /**
     * オブジェクトの比較を行い、一致する場合にtrueを返す.
     * @param obj1
     * @param obj2
     * @return オブジェクトが一致する場合にtrue
     */
    public static boolean isEquals(Object obj1, Object obj2) {
        if (obj1 == null && obj2 == null) return true;
        if (obj1 != null) {
            return obj1.equals(obj2);
        } else {
            return obj2.equals(obj1);
        }
    }
    
    public static boolean equals(Object obj1, Object obj2) {
        return isEquals(obj1, obj2);
    }

    /**
     * Booleanの比較を行い、一致する場合にtrueを返す.
     * @param b1
     * @param b2
     * @return 引数が一致する場合にtrue
     */
    public static boolean isEquals(boolean b1, boolean b2) {
        if (b1 && b2) return true;
        if (!b1 && !b2) return true;
        return false;
    }
    public static boolean boolEquals(boolean b1, boolean b2) {
        return isEquals(b1, b2);
    }
    
    /**
     * 文字列配列の比較を行い、一致する場合にtrueを返す.
     * @param array1
     * @param array2
     * @return 文字列配列が一致する場合にtrue
     */
    public static boolean equals(String[] array1, String[] array2) {
        if (array1 == null && array2 == null) return true;
        if (array1 == null || array2 == null) return false;
        if (array1.length != array2.length) return false;
        for (int i = 0; i < array1.length; i++) {
            if (!equals(array1[i], array2[i])) return false;
        }
        return true;
    }

    /**
     * 文字列のHash値を計算する.
     * @param strs
     * @return 文字列のハッシュ値
     */
    public static int getHashCode(String... strs) {
        int hash = 31;
        for (int i = 0; i < strs.length; i++) {
            hash = hash + (strs[i] != null ? 17 * strs[i].hashCode() : 0);
        }
        return hash;
    }
    public static int strHashCode(String... strs) {
        return getHashCode(strs);
    }

    /**
     * BooleanのHash値を計算する.
     * @param b
     * @return Booleanのハッシュ値
     */
    public static int boolHashCode(boolean b) {
        if (b) return 1;
        return 0;
    }
}
