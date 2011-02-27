package org.pirkaengine.form.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * URLに利用可能な文字列フィールドに付与するアノテーション.
 * <p>
 * 英数字,.~$!*';:@=&+を許可する
 * </p>
 * [\\w-\\./~,$!\\*';:@=&+]
 * @author shuji.w6e
 * @since 0.1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface UriUsable {
    /** メッセージキー */
    String messageKey() default "";
}
