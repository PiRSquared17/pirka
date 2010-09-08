package org.pirkaengine.form.validator;

/**
 * <p>Validator</p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public interface Validator<T> {

    boolean isValid(T value);

}