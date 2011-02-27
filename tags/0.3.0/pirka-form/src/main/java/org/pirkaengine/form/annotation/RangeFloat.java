package org.pirkaengine.form.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 浮動小数点数の範囲を指定するアノテーション。
 * @author shuji.w6e
 * @since 0.1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RangeFloat {
    /** 最小値（デフォルト0） */
    float min() default 0f;

    /** 最大値（デフォルト100） */
    float max() default 1f;

    /** メッセージキー */
    String messageKey() default "";
}
