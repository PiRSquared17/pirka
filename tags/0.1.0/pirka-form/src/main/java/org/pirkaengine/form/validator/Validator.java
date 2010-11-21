package org.pirkaengine.form.validator;

/**
 * Validator.
 * @author shuji.w6e
 * @since 0.1.0
 * @param <T>
 */
public interface Validator<T> {

    /**
     * 指定した値が妥当である場合にtrueを返す.
     * @param name 判定する項目名
     * @param value 判定する値
     * @return 指定した値が妥当である場合にtrue
     */
    String validate(String name, T value);
    
}
