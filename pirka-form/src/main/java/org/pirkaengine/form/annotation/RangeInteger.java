package org.pirkaengine.form.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 整数の範囲を指定するアノテーション。
 * @author shuji.w6e
 * @since 0.1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RangeInteger {
    /** 最小値（デフォルト0） */
    int min() default 0;

    /** 最大値（デフォルト100） */
    int max() default 100;

    /** メッセージキー */
    String messageKey() default "";
}
