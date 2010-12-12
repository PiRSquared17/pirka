package org.pirkaengine.form.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 正規表現の文字列フィールドに付与するアノテーション
 * @author shuji.w6e
 * @since 0.1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Regex1 {
    /** 設定値 */
    String value();

    /** メッセージキー */
    String messageKey() default "";
}
